package com.bomen.blogging.dtos;

import com.bomen.blogging.models.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class PostDto {
    private List<Tag> tags;
    private String title;
    private String body;

    public void setTags(Tag... tag) {
        tags = new ArrayList<>();
        tags.addAll(Arrays.asList(tag));
    }
}
