package com.innowise.training.shablinskaya.helpdesk.repository;

import com.innowise.training.shablinskaya.helpdesk.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> getById(Long id);

    Category getByName(String name);

}
