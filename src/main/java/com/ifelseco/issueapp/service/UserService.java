package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.entity.UserRole;

import java.util.Set;

public interface UserService {

    User createLead(User user);
    User createDeveloper(User user);
    User findByUsername(String username);
    User findByEmail(String email);

}
