package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.entity.Role;
import com.ifelseco.issueapp.repository.RoleRepository;
import com.ifelseco.issueapp.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        Role newRole = findByName(role.getName());

        if(newRole != null){
            LOG.info("This role has already registered");
            return newRole;
        }

        return roleRepository.save(role);

    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
