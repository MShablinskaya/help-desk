package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Category;
import com.innowise.training.shablinskaya.helpdesk.repository.CategoryRepository;

import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public Category getById(Integer id) {
        return null;
    }

    @Override
    public List<Category> getAllFromTable() {
        return null;
    }

    @Override
    public boolean updateTable(Category category) {
        return false;
    }

    @Override
    public boolean deleteFromTable(Integer id) {
        return false;
    }

    @Override
    public void addToTable(Category category) {

    }
}
