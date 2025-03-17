package com.reddy.service;


import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;

    public void sendEmployeeCredentials(String toEmail, String username, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Employee Account Credentials");
        message.setText("Dear Employee,\n\n"
                + "Your account has been created successfully.\n"
                + "Username: " + username + "\n"
                + "Temporary Password: " + tempPassword + "\n\n"
                + "Please change your password upon your first login.\n\n"
                + "Best regards,\nHRMS Team");
        mailSender.send(message);
    }
}

