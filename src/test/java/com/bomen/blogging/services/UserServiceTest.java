package com.bomen.blogging.services;

import com.bomen.blogging.dtos.LoginDto;
import com.bomen.blogging.dtos.UserDto;
import com.bomen.blogging.exceptions.BlogAppException;
import com.bomen.blogging.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.apache.logging.log4j.ThreadContext.isEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
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
        userDto.setUserName("Makanaki");
        userDto.setPhoneNumber("07039410420");
        userDto.setEmail("ikpeaeo@yahoo.com");
        userDto.setPassword("dewwwewH");

       // loginDto = new LoginDto();
    }
    @AfterEach
    void tearDown(){
        userService.reset();
    }

    @Test
    void userCanBeCreated(){
        String message = null;
        try {
            message = userService.createUser(userDto);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", message);
        User user =  userService.findByEmail(userDto.getEmail()).orElseThrow();
        assertNotNull(user.getId());
    }
    @Test
    void cannotCreateMultipleUsers(){
        String message = null;
        try {
            message = userService.createUser(userDto);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", message);
        UserDto userDto1 = new UserDto();
        userDto1.setFirstName("Ehigie");
        userDto1.setLastName("Ikpea");
        userDto1.setUserName("Makaaki");
        userDto1.setPhoneNumber("07039410420");
        userDto1.setEmail("ikpeaeo@yahoo.com");
        userDto1.setPassword("dewwwew");

        try {
            message = userService.createUser(userDto1);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        assertThrows(BlogAppException.class, ()-> userService.createUser(userDto1));
    }
    @Test
    void testThatUserPasswordCannotBeLessThanEightCharacters(){
        userDto.setPassword("asde");

        assertThrows(BlogAppException.class, ()-> userService.createUser(userDto));
    }

}