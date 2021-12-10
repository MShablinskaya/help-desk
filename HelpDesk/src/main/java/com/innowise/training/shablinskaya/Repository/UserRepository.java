package com.innowise.training.shablinskaya.Repository;

import com.innowise.training.shablinskaya.Entity.User;

public interface UserRepository extends TableManagerRepository<User, Integer> {
    User getByName(String name);//зачем тебе user по имени?
}
