package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.Category;

public interface CategoryService {

    Category findById(Long id);

    Category findByName(String name);
}
