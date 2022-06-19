package com.epam.learn.shchehlov.webspringshop.service.impl;

import com.epam.learn.shchehlov.webspringshop.entity.User;
import com.epam.learn.shchehlov.webspringshop.entity.attribute.Role;
import com.epam.learn.shchehlov.webspringshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplJunitTest {

    private User user1;
    private User user2;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        user1 = new User();
        user1.setId(1L);
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

    @Test
    void testFindUserByLogin() {
        when(userRepository.findByLogin("Login")).thenReturn(user1);

        User foundUser = userService.findUserByLogin("Login");

        assertNotNull(foundUser);
        assertEquals("name", foundUser.getFirstName());
    }

    @Test
    void testFindUserByLogin_UsernameNotFoundException() {
        when(userRepository.findByLogin(anyString())).thenReturn(null);

        assertNull(userService.findUserByLogin("Login"));
    }

    @Test
    void testFindAllUsersByLogin() {
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> foundUsers = userService.findAllUsers();

        assertThat(foundUsers).isNotNull().contains(user2);
    }

    @Test
    void testCreateUser() {
        User userData = new User();
        userData.setFirstName("name");
        userData.setLastName("Lastname");
        userData.setEmail("email@email.exem");
        userData.setLogin("Login");
        userData.setPassword("Password123");
        userData.setMailing(true);

        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user1);
        User createdUser = userService.createUser(userData);

        assertNotNull(createdUser);
        assertEquals(1L, createdUser.getId());
    }
}
