package com.bomen.blogging.services;

import com.bomen.blogging.dtos.LoginDto;
import com.bomen.blogging.dtos.PostDto;
import com.bomen.blogging.dtos.TagDto;
import com.bomen.blogging.dtos.UserDto;
import com.bomen.blogging.exceptions.AlreadyExistsException;
import com.bomen.blogging.exceptions.BlogAppException;
import com.bomen.blogging.models.Post;
import com.bomen.blogging.models.Tag;
import com.bomen.blogging.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TagServiceImpl tagService;

    @Autowired
    PostServiceImpl postService;

    UserDto userDto;
    PostDto postDto;
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
        postService.reset();
        tagService.reset();
    }

    @Test
    void userCanBeCreated(){
        User author = null;
        try {
            author = userService.createUser(userDto);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", author);

        assertNotNull(author.getId());
    }
    @Test
    void cannotCreateMultipleUsers(){
        User author = null;
        try {
            author = userService.createUser(userDto);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", author);
        UserDto userDto1 = new UserDto();
        userDto1.setFirstName("Ehigie");
        userDto1.setLastName("Ikpea");
        userDto1.setUserName("Makaaki");
        userDto1.setPhoneNumber("07039410420");
        userDto1.setEmail("ikpeaeo@yahoo.com");
        userDto1.setPassword("dewwwew");

        try {
             userService.createUser(userDto1);
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
    @Test
    void postHasAuthor(){
        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        Tag tag = null;
        try {
            tag = tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }

        postDto = new PostDto();
        postDto.setTitle("Dancing in the rain");
        postDto.setBody("sad souls all pretending to be happy where they are");
        postDto.setAuthorEmail("sane@world.com");
        postDto.setTags(tag);

        userDto.setEmail("sane@world.com");
        User author = null;
        try {
            author = userService.createUser(userDto);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", author);

        Post post = postService.createPost(postDto);
    }
}