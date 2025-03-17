package com.reddy.service;

import com.reddy.model.User;
import com.reddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Service
public class ForgetPasswordService {

    private JavaMailSender mailSender;


    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;

    // In-memory OTP store: mapping email -> OTP
    private final Map<String, String> otpCache = new ConcurrentHashMap<>();

    // Send OTP to user's registered email based on identifier (email or username)
    public void sendOtp(String identifier) {
        System.out.println(identifier);
        User user = userRepository.findByUserOrEmail(identifier)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(user.getEmployee().getEmail());
        String otp = generateOtp();
        System.out.println(otp.toString());
        otpCache.put(user.getEmployee().getEmail(), otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmployee().getEmail());
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);
    }

    // Verify the OTP for a given email
    public boolean verifyOtp(String email, String otp) {
        System.out.println(otpCache);
        System.out.println(email);
        System.out.println(otp);
        String cachedOtp = otpCache.get(email);
        System.out.println(cachedOtp);
        if (cachedOtp != null && cachedOtp.equals(otp)) {
            System.out.println(otpCache.get(email));
            otpCache.remove(email); // Remove OTP once verified
            return true;
        }
        System.out.println("No equal");
        return false;
    }

    // Reset the user's password if OTP verification succeeded
    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByUserOrEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // Helper method to generate a 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otpInt = 100000 + random.nextInt(900000);
        return String.valueOf(otpInt);
    }
}