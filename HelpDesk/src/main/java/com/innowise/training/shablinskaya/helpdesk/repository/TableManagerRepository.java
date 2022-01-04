package com.innowise.training.shablinskaya.helpdesk.repository;

import java.util.List;
import java.util.Optional;

public interface TableManagerRepository <T, ID> {
    Optional<T> getById(Long id);

    List<T> getAllFromTable();

    T updateTable(T entity);

    void addToTable(T entity);
}
