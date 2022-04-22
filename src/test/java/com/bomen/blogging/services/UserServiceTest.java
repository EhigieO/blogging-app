package com.bomen.blogging.services;

import com.bomen.blogging.dtos.LoginRequest;
import com.bomen.blogging.dtos.PostDto;
import com.bomen.blogging.dtos.TagDto;
import com.bomen.blogging.dtos.SignUpRequest;
import com.bomen.blogging.exceptions.AlreadyExistsException;
import com.bomen.blogging.exceptions.BlogAppException;
import com.bomen.blogging.models.*;
import com.bomen.blogging.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TagServiceImpl tagService;

    @Autowired
    PostServiceImpl postService;

    @Autowired
    private RoleRepository repo;

    SignUpRequest signUpRequest;
    PostDto postDto;
    LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("Ehigie");
        signUpRequest.setLastName("Ikpea");
        signUpRequest.setUserName("Makanaki");
        signUpRequest.setPhoneNumber("07039410420");
        signUpRequest.setEmail("ikpeaeo@yahoo.com");
        signUpRequest.setPassword("dewwwewH");
        signUpRequest.setRoles(Set.of("author","user"));

        loginRequest = new LoginRequest();
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
            author = userService.createUser(signUpRequest);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", author);

        assertNotNull(author.getId());
    }

    @Test
    void UserHasRole() {
        User author = null;
        try {
            author = userService.createUser(signUpRequest);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", author);

        assertNotNull(author.getRoles());
    }
    @Test
    void cannotCreateMultipleUsers(){
        User author = null;
        try {
            author = userService.createUser(signUpRequest);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", author);
        SignUpRequest signUpRequest1 = new SignUpRequest();
        signUpRequest1.setFirstName("Ehigie");
        signUpRequest1.setLastName("Ikpea");
        signUpRequest1.setUserName("Makaaki");
        signUpRequest1.setPhoneNumber("07039410420");
        signUpRequest1.setEmail("ikpeaeo@yahoo.com");
        signUpRequest1.setPassword("dewwwew");

        try {
             userService.createUser(signUpRequest1);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        assertThrows(BlogAppException.class, ()-> userService.createUser(signUpRequest1));
    }
    @Test
    void testThatUserPasswordCannotBeLessThanSixCharacters(){
        signUpRequest.setPassword("asde");

        assertThrows(BlogAppException.class, ()-> userService.createUser(signUpRequest));
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

        signUpRequest.setEmail("sane@world.com");
        User author = null;
        try {
            author = userService.createUser(signUpRequest);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", author);

        Post post = postService.createPost(postDto);
    }

    @Test
    public void testCreateRoles() {
        Role user = new Role(UserRole.USER);
        Role admin = new Role(UserRole.ADMIN);
        Role editor = new Role(UserRole.EDITOR);
        Role author = new Role(UserRole.AUTHOR);
        repo.deleteAll();
        repo.saveAll(List.of(user, admin, editor, author));

        List<Role> listRoles = repo.findAll();

        assertThat(listRoles.size()).isEqualTo(4);
    }

    @Test
    void testThatLoginGeneratesToken(){

        User author = null;
        try {
            author = userService.createUser(signUpRequest);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        log.info("{}", author);

        loginRequest.setUserName("Makanaki");
        loginRequest.setPassword("dewwwewH");

//        AccessToken token = userService.login(loginDto);
//        log.info("Token ---->{}",token);
//
//        assertNotNull(token.getAccessToken());
    }

    @Test
    void userCanBeEnabled(){
        User author = null;
        try {
            author = userService.createUser(signUpRequest);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }
        assertFalse(author.getEnabled());
        userService.enableUser(author.getEmail());
//        assertEquals(author, userService.findByEmail(author.getEmail()));
        assertTrue(userService.findByEmail(author.getEmail()).getEnabled());
    }
}