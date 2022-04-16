package com.bomen.blogging.registration.token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken,String> {
    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
