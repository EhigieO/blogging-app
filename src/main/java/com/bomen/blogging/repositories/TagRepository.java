package com.bomen.blogging.repositories;

import com.bomen.blogging.models.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {
    Tag findByKeyWord(String keyWord);
}
