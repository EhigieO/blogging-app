package com.bomen.blogging.repositories;

import com.bomen.blogging.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
