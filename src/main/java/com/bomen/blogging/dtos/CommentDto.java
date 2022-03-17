package com.bomen.blogging.dtos;

import lombok.Data;

@Data
public class CommentDto {
    private String comment;
    private String commenterEmail;
}
