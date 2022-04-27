package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    Category findByName(String name);

    List<Category> getAllCategories();
}
