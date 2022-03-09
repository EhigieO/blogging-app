package com.bomen.blogging.services;

import com.bomen.blogging.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    String createUser(UserDto userDto);
}
