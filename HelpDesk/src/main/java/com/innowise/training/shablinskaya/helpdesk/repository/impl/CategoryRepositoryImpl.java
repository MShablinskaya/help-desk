package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Category;
import com.innowise.training.shablinskaya.helpdesk.repository.CategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Category> getById(Long id) {
        return Optional.of(entityManager.find(Category.class, id));
    }

    @Override
    public List<Category> getAllFromTable() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
    }

    @Override
    public Category updateTable(Category category) {
        return entityManager.merge(category);
    }

    @Override
    public void addToTable(Category category) {
        entityManager.persist(category);

    }
}
