package com.bomen.blogging.services;

import com.bomen.blogging.dtos.PostDto;
import com.bomen.blogging.models.Comment;
import com.bomen.blogging.models.Post;
import com.bomen.blogging.models.Tag;

import java.util.List;

public interface PostService {
    Post createPost(PostDto postDto);

    Post findPostByTitle(String title);

    List<Post> findAllPostsByTag(String tag);

    Post addComment(Comment comment, String id);

    Post findPostById(String postId);

    Post addTagToPost(Post post, Tag tag);
}
