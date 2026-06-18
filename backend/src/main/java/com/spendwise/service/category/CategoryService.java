package com.spendwise.service.category;

import com.spendwise.dto.CategoryDto;
import com.spendwise.model.Category;

import java.util.List;

public interface CategoryService {

    boolean existsByUserAndCategory(String appUserId, Long categoryId);

    List<Category> getAllWithoutUserId();

    Category getByName(String category);

    List<CategoryDto> getAllCategoriesForUser(String userId);

}
