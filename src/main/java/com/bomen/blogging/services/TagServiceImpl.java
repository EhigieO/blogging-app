package com.bomen.blogging.services;

import com.bomen.blogging.dtos.TagDto;
import com.bomen.blogging.exceptions.AlreadyExistsException;
import com.bomen.blogging.models.Tag;
import com.bomen.blogging.repositories.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl  implements TagService {
    ModelMapper mapper = new ModelMapper();

    @Autowired
    private TagRepository tagRepository;


    @Override
    public Tag createTag(TagDto tagDto) throws AlreadyExistsException {
        Tag tag = mapper.map(tagDto, Tag.class);
        boolean tagExists = tagRepository.findByKeyWord(tag.getKeyWord()) != null;
        if (tagExists) {
            throw new AlreadyExistsException("Tag Already exits");
        }
        tag = tagRepository.save(tag);
        return tag;
    }

    @Override
    public Tag addTag(String postId, Tag tag) {
        tag.addPostId(postId);
        return tagRepository.save(tag);
    }

    @Override
    public Tag findTag(String keyWord) {
        return tagRepository.findByKeyWord(keyWord);
    }

    @Override
    public void addTag(String postId, List<Tag> tags) {
        for (Tag tag:tags) {
            tag.addPostId(postId);
            tagRepository.save(tag);
        }
    }


    public void reset() {
        tagRepository.deleteAll();
    }
}
