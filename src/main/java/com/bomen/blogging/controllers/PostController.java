package com.bomen.blogging.controllers;

import com.bomen.blogging.dtos.PostDto;
import com.bomen.blogging.services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/posts")
public class PostController {
    @Autowired
    PostServiceImpl postService;


//    public Response createPost(@RequestBody PostDto postDto){
//
//    }
}
