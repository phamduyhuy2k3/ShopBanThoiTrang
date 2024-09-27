package com.ddk.asmsof306.security.auth;



import com.ddk.asmsof306.model.Authority;
import com.ddk.asmsof306.validation.annotations.PasswordValueMatch;
import com.ddk.asmsof306.validation.annotations.UniqueEmail;
import com.ddk.asmsof306.validation.annotations.UsernameUnique;
import com.ddk.asmsof306.validation.annotations.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Không trùng password"
        )
})
public class RegisterRequest {
    @NotBlank(message = "Họ tên đang để trống")
    private String fullname;
    @NotBlank(message = "Username đang để trống")
    @UsernameUnique
    private String username;
    @NotBlank(message = "Email đang để trống")
    @Email(message = "Email không đúng định dạng")
    @UniqueEmail
    private String email;
    @ValidPassword
    @NotBlank(message = "Password đang để trống")
    private String password;
    @ValidPassword
    @NotBlank(message = "Xác nhận password đang để trống")
    private String confirmPassword;
    private List<Authority> authorities;
    interface ValidationStepOne{

    }
    interface ValidationStepTwo{

    }
}