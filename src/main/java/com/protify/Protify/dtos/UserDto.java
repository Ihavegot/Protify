package com.protify.Protify.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.InputType;


@Setter
@Getter
@NoArgsConstructor
public class UserDto {

    @InputType("email")
    private String email;
    private String login;
    @InputType("password")
    private String password;

    @Schema(hidden = true)
    @AssertTrue
    public boolean isEmailValid() {
        return email == null || !email.isBlank();
    }
    @Schema(hidden = true)
    @AssertTrue
    public boolean isLoginValid() {
        return login == null || !login.isBlank();
    }
    @Schema(hidden = true)
    @AssertTrue
    public boolean isPasswordValid() {
        return password == null || !password.isBlank();
    }
}



