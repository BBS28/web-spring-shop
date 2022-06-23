package com.epam.learn.shchehlov.webspringshop.entity;

import com.epam.learn.shchehlov.webspringshop.entity.attribute.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    @NotEmpty(message = "First name can't be empty")
    @Length(min = 2, message = "First name can't be less than 2 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name can't be empty")
    @Length(min = 2, message = "Last name can't be less than 2 characters")
    private String lastName;

    @Column(name = "email")
    @Email(message = "Email address must be valid")
    @NotEmpty(message = "Email can't be less than 2 characters")
    private String email;

    @Column(name = "login")
    @NotEmpty(message = "Login can't be less than 8 characters")
    @Length(min = 2, message = "Login can't be less than 2 characters")
    private String login;

    @Column(name = "password")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            message = "Entered password should be min 8 symbols, 1 capital letter, 1 number")
    private String password;

    @Column(name = "mailing")
    private boolean mailing;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
}
