package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Category;
import com.innowise.training.shablinskaya.helpdesk.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Category> getById(Long id) {
        return Optional.of(entityManager.find(Category.class, id));
    }

    @Override
    public Category getByName(String name) {
        return (Category) entityManager.createQuery("SELECT c FROM Category c WHERE c.categoryName = :name")
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Category> getAllFromTable() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
    }

}
