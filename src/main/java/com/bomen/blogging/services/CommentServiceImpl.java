package com.bomen.blogging.services;

import com.bomen.blogging.dtos.CommentDto;
import com.bomen.blogging.models.Comment;
import com.bomen.blogging.repositories.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public Comment createComment(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllCommentsForPost(String postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public List<Comment> findCommentsByEmail(String commenterEmail) {
        return commentRepository.findAllByEmail(commenterEmail);
    }

    public void reset() {
       commentRepository.deleteAll();
    }
}
