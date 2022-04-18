package com.bomen.blogging.services;

import com.bomen.blogging.dtos.LoginRequest;
import com.bomen.blogging.dtos.SignUpRequest;
import com.bomen.blogging.exceptions.BlogAppException;
import com.bomen.blogging.models.Post;
import com.bomen.blogging.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(SignUpRequest signUpRequest) throws BlogAppException;

    User findByEmail(String email);

    User updateUserListOfPost(String email, Post post);

    boolean existsByUserName(String userName);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    void update(User user);

    User login(LoginRequest loginRequest);
}
