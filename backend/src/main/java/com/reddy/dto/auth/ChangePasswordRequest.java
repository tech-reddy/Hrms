package com.reddy.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest{
    @NotBlank String oldPassword;
    @NotBlank @Size(min = 6) String newPassword;
}
