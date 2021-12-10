package com.innowise.training.shablinskaya.Repository.Impl;

import com.innowise.training.shablinskaya.Entity.History;
import com.innowise.training.shablinskaya.Repository.HistoryRepository;

import java.util.List;

public class HistoryRepositoryImpl implements HistoryRepository {
    @Override
    public History getById(Integer id) {
        return null;
    }

    @Override
    public List<History> getAllFromTable() {
        return null;
    }

    @Override
    public boolean updateTable(History history) {
        return false;
    }

    @Override
    public boolean deleteFromTable(Integer id) {
        return false;
    }

    @Override
    public void addToTable(History history) {

    }
}
