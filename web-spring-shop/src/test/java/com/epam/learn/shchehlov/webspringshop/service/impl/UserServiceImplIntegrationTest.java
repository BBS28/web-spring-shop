package com.epam.learn.shchehlov.webspringshop.service.impl;

import com.epam.learn.shchehlov.webspringshop.dto.UserDto;
import com.epam.learn.shchehlov.webspringshop.entity.User;
import com.epam.learn.shchehlov.webspringshop.entity.attribute.Role;
import com.epam.learn.shchehlov.webspringshop.mappers.UserMapper;
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

    UserDto user1;
    UserDto user2;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        user1 = new UserDto();
        user1.setFirstName("name");
        user1.setLastName("Lastname");
        user1.setEmail("email@email.exem");
        user1.setLogin("Login");
        user1.setPassword("Password123");
        user1.setMailing(true);

        user2 = new UserDto();
//        user2.setId(2L);
        user2.setFirstName("name2");
        user2.setLastName("Lastname2");
        user2.setEmail("second@email.exem");
        user2.setLogin("Login2");
        user2.setPassword("Password456");
        user2.setMailing(false);
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
        System.out.println("!!!!!!!!!!!!!Test!!!!!!!!!!!");
        userService.createUser(user1);

        User userFromDB = userService.findUserByLogin("Login");

        UserDto userDto = userMapper.toDto(userFromDB);
        System.out.println(userDto);

        userService.deleteUser(userDto);

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
