package com.reddy.controller;

import com.reddy.dto.auth.PasswordResetResponse;
import com.reddy.service.ForgetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/forget-password")
public class ForgetPasswordController {
    private ForgetPasswordService forgetPasswordService;

    // Endpoint to send OTP based on email or username
    @PostMapping("/send-otp")
    public ResponseEntity<PasswordResetResponse> sendOtp(@RequestParam String identifier) {
        try {
            forgetPasswordService.sendOtp(identifier);
            PasswordResetResponse response = new PasswordResetResponse("OTP sent successfully");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            PasswordResetResponse response = new PasswordResetResponse(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    // Endpoint to verify the OTP for the provided email
    @PostMapping("/verify-otp")
    public ResponseEntity<PasswordResetResponse> verifyOtp(@RequestParam String email,
                                            @RequestParam String otp) {
        boolean valid = forgetPasswordService.verifyOtp(email, otp);
        if (valid) {
            PasswordResetResponse response = new PasswordResetResponse("OTP verified");
            return ResponseEntity.ok(response);
        } else {
            PasswordResetResponse response = new PasswordResetResponse("Invalid OTP");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    // Endpoint to reset the password after OTP verification
    @PostMapping("/reset-password")
    public ResponseEntity<PasswordResetResponse> resetPassword(@RequestParam String email,
                                                @RequestParam String newPassword) {
        try {
            forgetPasswordService.resetPassword(email, newPassword);
            PasswordResetResponse response = new PasswordResetResponse("Password reset successfully");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            PasswordResetResponse response = new PasswordResetResponse(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }
}

