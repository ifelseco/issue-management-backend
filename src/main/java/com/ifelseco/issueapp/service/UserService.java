package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.exceptionhandling.NotUniqueException;

public interface UserService {

    User createLead(User user);
    User createDeveloper(User user);
    User createAdmin(User user);
    User findByUsername(String username);
    User findByEmail(String email);

}
