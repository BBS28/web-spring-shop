package com.epam.learn.shchehlov.webspringshop.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotEmpty(message = "First name can't be empty")
    @Size(min = 1, max = 255, message = "First name can't be less than 2 characters")
    private String firstName;

    @NotEmpty(message = "Last name can't be empty")
    @Size(min = 1, max = 255)
    private String lastName;

    @Email(message = "Email address must be valid")
    @Size(min = 1, max = 255, message = "Login can't be less than 2 characters")
    @NotEmpty(message = "Email can't be less than 2 characters")
    private String email;

    @NotEmpty(message = "Login can't be less than 8 characters")
    @Size(min = 1, max = 255, message = "Login can't be less than 2 characters")
    private String login;

    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            message = "Entered password should be min 8 symbols, 1 capital letter, 1 number")
    private String password;

    private boolean mailing;











}
