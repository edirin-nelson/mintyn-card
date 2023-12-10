package com.mintyn.mintyncard.repository;


import com.mintyn.mintynassessment.entity.JwtToken;
import com.mintyn.mintynassessment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Integer> {
    Optional<JwtToken> findByToken(String token);

    List<JwtToken> findTokenByUserAndExpiredIsFalseAndRevokedIsFalse(User user);
}