package com.spendwise.repo;

import com.spendwise.model.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepo extends CrudRepository<Card, Long> {
    List<Card> findAllByAppUserId(String userId);
}
