package com.spendwise.service.category;

public interface CategoryService {
    boolean existsByUserAndCategory(String appUserId, Long categoryId);

}
