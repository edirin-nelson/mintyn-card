package com.mintyn.mintyncard.security.repository;

import com.mintyn.mintyncard.security.model.JwtToken;
import com.mintyn.mintyncard.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Integer> {
    Optional<JwtToken> findByToken(String token);

    List<JwtToken> findTokenByCustomerAndExpiredIsFalseAndRevokedIsFalse(Customer customer);
}
