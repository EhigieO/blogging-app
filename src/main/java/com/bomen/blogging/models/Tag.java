package com.bomen.blogging.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Tag {
    @Id
    private String id;
    @Indexed(unique=true)
    private String keyWord;
    private List<String> postIds = new ArrayList<>();

    public void addPostId(String id) {
        postIds.add(id);
    }
}
