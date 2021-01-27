package com.ifelseco.issueapp;

import com.ifelseco.issueapp.entity.Role;
import com.ifelseco.issueapp.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssueappApplication implements CommandLineRunner {

    private final RoleService roleService;

    public IssueappApplication(RoleService roleService) {
        this.roleService = roleService;
    }


    public static void main(String[] args) {
        SpringApplication.run(IssueappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createRole("ROLE_LEAD");
        createRole("ROLE_DEV");
        createRole("ROLE_ADMIN");
    }

    private void createRole(String roleName) {
        Role roleLead = new Role();
        roleLead.setName(roleName);
        roleService.save(roleLead);
    }
}

