package com.bomen.blogging.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class Comment {
    @Id
    private String id;
    private String postId;
    private String email;
    private String comment;
    @CreatedDate
    private Date created;
}
