package com.epam.learn.shchehlov.webspringshop.service.impl;

import com.epam.learn.shchehlov.webspringshop.dto.UserDto;
import com.epam.learn.shchehlov.webspringshop.entity.User;
import com.epam.learn.shchehlov.webspringshop.entity.attribute.Role;
import com.epam.learn.shchehlov.webspringshop.mappers.UserMapper;
import com.epam.learn.shchehlov.webspringshop.repository.UserRepository;
import com.epam.learn.shchehlov.webspringshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(UserDto userDto) {
        User user = findUserByLogin(userDto.getLogin());
        userRepository.delete(user);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }
}
