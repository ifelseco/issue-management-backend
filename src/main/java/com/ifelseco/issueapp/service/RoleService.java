package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.Role;

public interface RoleService {
    Role save(Role role);
    Role findByName(String name);
}
