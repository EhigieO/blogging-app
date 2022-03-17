package com.bomen.blogging.services;

import com.bomen.blogging.dtos.UserDto;
import com.bomen.blogging.exceptions.BlogAppException;
import com.bomen.blogging.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    String createUser(UserDto userDto) throws BlogAppException;

    Optional<User> findByEmail(String email);
}
