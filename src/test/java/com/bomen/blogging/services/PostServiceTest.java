package com.bomen.blogging.services;

import com.bomen.blogging.dtos.CommentDto;
import com.bomen.blogging.dtos.PostDto;
import com.bomen.blogging.dtos.TagDto;
import com.bomen.blogging.dtos.UserDto;
import com.bomen.blogging.exceptions.AlreadyExistsException;
import com.bomen.blogging.exceptions.BlogAppException;
import com.bomen.blogging.models.Comment;
import com.bomen.blogging.models.Post;
import com.bomen.blogging.models.Tag;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PostServiceTest {

    @Autowired
    PostServiceImpl postService;

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    UserServiceImpl userService;

    PostDto postDto;
    UserDto userDto;
    @Autowired
    TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        postDto = new PostDto();
        postDto.setTitle("Dancing in the rain");
        postDto.setBody("sad souls all pretending to be happy where they are");
        postDto.setAuthorEmail("sane@world.com");

        userDto = new UserDto();
        userDto.setFirstName("Ehigie");
        userDto.setLastName("Ikpea");
        userDto.setUserName("Makanaki");
        userDto.setPhoneNumber("07039410420");
        userDto.setEmail("ikpeaeo@yahoo.com");
        userDto.setPassword("dewwwewH");

    }

    @AfterEach
    void tearDown() {
        postService.reset();
        commentService.reset();
        tagService.reset();
        userService.reset();
    }

    @Test
    void postCanBeCreated(){
        try {
            userService.createUser(userDto);
        } catch (BlogAppException e) {
            e.printStackTrace();
        }
        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        Tag tag = null;
        try {
            tag = tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        postDto.setTags(tag);
        postDto.setAuthorEmail("ikpeaeo@yahoo.com");
        Post post = postService.createPost(postDto);
        log.info("this is post -> {}",post);
        assertEquals(post.getBody(), postDto.getBody());
    }

    @Test
    void postHasId(){
        try {
            userService.createUser(userDto);
        } catch (BlogAppException e) {
            e.printStackTrace();
        }

        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        Tag tag = null;
        try {
            tag = tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        postDto.setTags(tag);
        postDto.setAuthorEmail("ikpeaeo@yahoo.com");
        Post post = postService.createPost(postDto);
        String id = post.getId();
        post = postService.findPostById(id);
        assertEquals(post.getBody(), postDto.getBody());

    }
    @Test
    void canFindPostByTitle(){
        try {
            userService.createUser(userDto);
        } catch (BlogAppException e) {
            e.printStackTrace();
        }
        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        Tag tag = null;
        try {
            tag = tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        postDto.setTags(tag);
        postDto.setAuthorEmail("ikpeaeo@yahoo.com");
        Post post = postService.createPost(postDto);
        assertEquals(postService.findPostByTitle(postDto.getTitle()),post);
    }

    @Test
    void createTag(){
        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        Tag tag = null;
        try {
            tag = tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        log.info("tag is created -> {}", tag);
        assert tag != null;
        assertEquals(tag.getKeyWord(),tagDto.getKeyWord());
    }

    @Test
    void canAddTagToPost(){
        try {
            userService.createUser(userDto);
        } catch (BlogAppException e) {
            e.printStackTrace();
        }

        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        Tag tag = null;
        try {
            tag = tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }

        TagDto tagDto2 = new TagDto();
        tagDto2.setKeyWord("Top");
        Tag tag2= null;
        try {
            tag2 = tagService.createTag(tagDto2);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }

        postDto.setAuthorEmail("ikpeaeo@yahoo.com");
        postDto.setTags(tag,tag2);
        Post post = postService.createPost(postDto);
        log.info("post with tag -> {}", post);
        assertTrue(post.getTags().contains(tag));
        assertTrue(post.getTags().contains(tag2));
    }

    @Test
    void cannotCreateMultipleTagsOfSameKeyWord(){
        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        try {
            tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }

        TagDto tagDto2 = new TagDto();
        tagDto2.setKeyWord("clothing");
        assertThrows(AlreadyExistsException.class,()-> tagService.createTag(tagDto2));
    }
    @Test
    void findTagByKeyWord(){
        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        Tag tag = null;
        try {
            tag = tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        assert tag != null;
        assertEquals(tag.getKeyWord(),tagDto.getKeyWord());
        tag = tagService.findTag("clothing");
        assertEquals(tag.getKeyWord(),"clothing");
    }

    @Test
    void findListOfPostByTags(){
        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        Tag tag = null;
        try {
            tag = tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        postDto.setTags(tag);

        userDto = new UserDto();
        userDto.setFirstName("Ehigie");
        userDto.setLastName("Ikpea");
        userDto.setUserName("Makanaki");
        userDto.setPhoneNumber("07039410420");
        userDto.setEmail("sane@world.com");
        userDto.setPassword("dewwwewH");

        try {
            userService.createUser(userDto);
        } catch (BlogAppException e) {
            log.info("Error -> {}",e.getMessage());
        }

        Post post = postService.createPost(postDto);
        PostDto postDto1 = new PostDto();
        postDto1.setTitle("killer Chicken");
        postDto1.setBody("Avian flu kills chickens");
        postDto1.setTags(tag);
        postDto1.setAuthorEmail("sane@world.com");

        Post post1 = postService.createPost(postDto1);

        List<Post> posts = postService.findAllPostsByTag("clothing");
        log.info("posts in tag -> {}", posts);
        //assertTrue(posts.contains(post));
        assertTrue(posts.contains(post1));
    }

    @Test
    void createComment(){
        CommentDto commentDto = new CommentDto();
        commentDto.setCommenterEmail("pearl@gmail.com");
        commentDto.setComment("so true, people who dont care about themselves");

        commentService.createComment(commentDto);
        List<Comment> comments = commentService.findCommentsByEmail(commentDto.getCommenterEmail());
        log.info("comment saved -> {}", comments);
        assertFalse(comments.isEmpty());
    }
    @Test
    void userCanCreateMultipleComments(){
        CommentDto commentDto = new CommentDto();
        commentDto.setCommenterEmail("pearl@gmail.com");
        commentDto.setComment("so true, people who dont care about themselves");
        commentService.createComment(commentDto);

        CommentDto commentDto2 = new CommentDto();
        commentDto2.setCommenterEmail("pearl@gmail.com");
        commentDto2.setComment("so true, people who dont care about themselves");
        commentService.createComment(commentDto2);
        List<Comment> comments = commentService.findCommentsByEmail(commentDto.getCommenterEmail());
        log.info("comment saved -> {}", comments);
        assertTrue(comments.size() > 1);
    }

    @Test
    void postHasComments(){
        try {
            userService.createUser(userDto);
        } catch (BlogAppException e) {
            e.printStackTrace();
        }

        TagDto tagDto = new TagDto();
        tagDto.setKeyWord("clothing");
        Tag tag = null;
        try {
            tag = tagService.createTag(tagDto);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        postDto.setTags(tag);
        postDto.setAuthorEmail("ikpeaeo@yahoo.com");

        postService.createPost(postDto);
        Post post = postService.findPostByTitle(postDto.getTitle());
        assertEquals(post.getComments(),new ArrayList<>());

        CommentDto commentDto = new CommentDto();
        commentDto.setCommenterEmail("pearl@gmail.com");
        commentDto.setComment("so true, people who dont care about themselves");
        Comment comment = commentService.createComment(commentDto);

        post = postService.addComment(comment, post.getId());
        log.info("comment added to post -> {}",post);
        assertFalse(post.getComments().isEmpty());
    }
}