package com.spendwise.repo;

import com.spendwise.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category, Long> {
    @Query("SELECT COUNT(*) > 0 " +
           "FROM Category c " +
           "JOIN c.appUser u " +
           "WHERE u.id = :appUserId " +
           "AND c.id = :categoryId")
    boolean existsByAppUserIdAndCategoryId(String appUserId, Long categoryId);
}
