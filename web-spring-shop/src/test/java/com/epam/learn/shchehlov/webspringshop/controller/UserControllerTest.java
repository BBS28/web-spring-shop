package com.epam.learn.shchehlov.webspringshop.controller;

import com.epam.learn.shchehlov.webspringshop.dto.UserDto;
import com.epam.learn.shchehlov.webspringshop.entity.User;
import com.epam.learn.shchehlov.webspringshop.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ResponseEntity<?> responseEntity;

    @Test
    void createNewUser() {
        UserDto userDto = new UserDto();
        userDto.setLogin("Login");
        when(userService.findUserByLogin(anyString())).thenReturn(null).thenReturn(new User());
        when(bindingResult.hasErrors()).thenReturn(false).thenReturn(true);

        assertThat(userController.createNewUser(userDto, bindingResult).getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        verify(userService).createUser(userDto);
        assertThat(userController.createNewUser(userDto, bindingResult).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        userController.createNewUser(userDto, bindingResult);
    }
}
