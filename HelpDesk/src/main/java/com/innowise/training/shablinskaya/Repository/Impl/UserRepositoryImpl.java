package com.innowise.training.shablinskaya.Repository.Impl;

import com.innowise.training.shablinskaya.Entity.User;
import com.innowise.training.shablinskaya.Repository.UserRepository;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User getByName(String name) {
        return null;
    }//?

    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public List<User> getAllFromTable() {
        return null;
    }

    @Override
    public boolean updateTable(User user) {
        return false;
    }//?

    @Override
    public boolean deleteFromTable(Integer id) {
        return false;
    }//?

    @Override
    public void addToTable(User user) {//?

    }
}
