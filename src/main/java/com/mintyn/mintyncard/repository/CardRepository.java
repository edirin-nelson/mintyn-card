package com.mintyn.mintyncard.repository;

import com.mintyn.mintyncard.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {

}
