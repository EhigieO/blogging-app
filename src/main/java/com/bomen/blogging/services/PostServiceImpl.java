package com.bomen.blogging.services;

import com.bomen.blogging.dtos.PostDto;
import com.bomen.blogging.models.Comment;
import com.bomen.blogging.models.Post;
import com.bomen.blogging.models.Tag;
import com.bomen.blogging.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    UserServiceImpl userService;

    ModelMapper mapper = new ModelMapper();
    @Autowired
    TagServiceImpl tagService;

    @Override
    public Post createPost(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        post = postRepository.save(post);
        tagService.addTag(post.getId(), postDto.getTags());
        post.setAuthorEmail(postDto.getAuthorEmail());
        userService.updateUserListOfPost(postDto.getAuthorEmail(),post);
        return postRepository.save(post);
    }

    @Override
    public Post findPostByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    public List<Post> findAllPostsByTag(String keyWord) {
        Tag tag = tagService.findTag(keyWord);
        List<String> postIds = tag.getPostIds();
        List<Post> posts = new ArrayList<>();
        for (String postId : postIds) {
            posts.add(postRepository.findById(postId).orElseThrow());
        }
        return  posts;
    }

    @Override
    public Post addComment(Comment comment, String postId) {
        comment.setPostId(postId);
        Post post = findPostById(postId);
        post.addComment(comment);
        return postRepository.save(post);
    }

    @Override
    public Post findPostById(String postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    @Override
    public Post addTagToPost(Post post, Tag tag) {
        tag = tagService.addTag(post.getId(), tag);
        post.addTags(tag);
        return postRepository.save(post);
    }

    public void reset() {
        postRepository.deleteAll();
    }
}
