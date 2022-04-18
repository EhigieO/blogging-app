package com.bomen.blogging.repositories;

import com.bomen.blogging.models.Role;
import com.bomen.blogging.models.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByRole(UserRole role);

}
