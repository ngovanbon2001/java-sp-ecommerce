package ecomerce.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    private String email;

    @NotBlank(message = "otp is required")
    private String otp;

    @NotBlank(message = "newPassword is required")
    @Size(min = 8, max = 128, message = "newPassword must be between 8 and 128 characters")
    @Pattern(
        regexp = "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{7,}$",
        message = "Password must start with uppercase and contain at least one lowercase letter, one number, and one special character"
    )
    private String newPassword;
}
