package com.innowise.training.shablinskaya.Repository;

import java.util.List;

public interface TableManagerRepository <T, ID> {
    T getById(ID id);

    List<T> getAllFromTable();

    boolean updateTable(T entity);

    boolean deleteFromTable(ID id);

    void addToTable(T entity);
}
