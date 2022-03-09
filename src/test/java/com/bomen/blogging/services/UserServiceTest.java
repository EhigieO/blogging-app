package com.bomen.blogging.services;

import com.bomen.blogging.dtos.LoginDto;
import com.bomen.blogging.dtos.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    UserServiceImpl userService;
    UserDto userDto;
    LoginDto loginDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setFirstName("Ehigie");
        userDto.setLastName("Ikpea");
        userDto.setUsername("Makanaki");
        userDto.setPhoneNumber("07039410420");
        userDto.setEmail("ikpeaeo@yahoo.com");
        userDto.setPassword("dewwwew");

        loginDto = new LoginDto();
    }

    @Test
    void userCanBeCreated(){
        userService.createUser(userDto);
    }

}