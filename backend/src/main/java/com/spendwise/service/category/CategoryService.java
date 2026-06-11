package com.spendwise.service.category;

import com.spendwise.model.Category;

import java.util.List;

public interface CategoryService {

    boolean existsByUserAndCategory(String appUserId, Long categoryId);

    List<Category> getAllWithoutUserId();

    Category getByName(String category);

}
