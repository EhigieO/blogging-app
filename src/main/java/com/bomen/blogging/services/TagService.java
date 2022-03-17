package com.bomen.blogging.services;


import com.bomen.blogging.dtos.TagDto;
import com.bomen.blogging.exceptions.AlreadyExistsException;
import com.bomen.blogging.models.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(TagDto tagDto) throws AlreadyExistsException;
    void addTag(String postId, List<Tag> tags);
    Tag addTag(String postId, Tag tag);

    Tag findTag(String keyWord);
}
