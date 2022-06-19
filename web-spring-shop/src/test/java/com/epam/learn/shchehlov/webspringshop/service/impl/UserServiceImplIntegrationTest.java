package com.epam.learn.shchehlov.webspringshop.service.impl;

import com.epam.learn.shchehlov.webspringshop.entity.User;
import com.epam.learn.shchehlov.webspringshop.entity.attribute.Role;
import com.epam.learn.shchehlov.webspringshop.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceImplIntegrationTest {

    User user1;
    User user2;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setFirstName("name");
        user1.setLastName("Lastname");
        user1.setEmail("email@email.exem");
        user1.setLogin("Login");
        user1.setPassword("Password123");
        user1.setMailing(true);
        user1.setRole(Role.ROLE_USER);

        user2 = new User();
        user2.setId(2L);
        user2.setFirstName("name2");
        user2.setLastName("Lastname2");
        user2.setEmail("second@email.exem");
        user2.setLogin("Login2");
        user2.setPassword("Password456");
        user2.setMailing(false);
        user2.setRole(Role.ROLE_USER);
    }

    @AfterEach
    public void afterEach() {
        userService.deleteAllUsers();
    }

    @Test
    void shouldCreateUserAndSaveItToDb() {
        userService.createUser(user1);

        User dbUser = userService.findUserByLogin("Login");

        assertThat(dbUser.getFirstName()).isEqualTo(user1.getFirstName());
        assertNotEquals(0L, dbUser.getId());

    }

    @Test
    void shouldDeleteUserFromDB() {
        userService.createUser(user1);

        User userFromDB = userService.findUserByLogin("Login");
        userService.deleteUser(userFromDB);

        assertNull(userService.findUserByLogin("Login"));
    }

    @Test
    void shouldGetUserDetails() {
        userService.createUser(user1);

        UserDetails userDetails = userService.loadUserByUsername("Login");

        assertEquals("Login", userDetails.getUsername());
    }

    @Test
    void shouldFindAllUsers() {
        userService.createUser(user1);
        userService.createUser(user2);
        List<User> userList = new ArrayList<>();

        userList = userService.findAllUsers();

        assertEquals(2, userList.size());
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenLoadWrongUserByUsername() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("WrongLogin");
        });
    }
}
