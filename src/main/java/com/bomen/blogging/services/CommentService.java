package com.bomen.blogging.services;

import com.bomen.blogging.dtos.CommentDto;
import com.bomen.blogging.models.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(CommentDto commentDto);

    List<Comment> findAllCommentsForPost(String postId);

    List<Comment> findCommentsByEmail(String commenterEmail);
}
