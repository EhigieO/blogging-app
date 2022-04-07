package com.bomen.blogging.services;

import com.bomen.blogging.dtos.UserDto;
import com.bomen.blogging.exceptions.BlogAppException;
import com.bomen.blogging.models.Post;
import com.bomen.blogging.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(UserDto userDto) throws BlogAppException;

    User findByEmail(String email);

    User updateUserListOfPost(String email, Post post);
}
