package com.ifelseco.issueapp;

import com.ifelseco.issueapp.entity.Role;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.RegisterModel;
import com.ifelseco.issueapp.service.RoleService;
import com.ifelseco.issueapp.service.UserService;
import com.ifelseco.issueapp.service.impl.UserServiceImpl;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssueApplication implements CommandLineRunner {
    private static final Logger LOG= LoggerFactory.getLogger(IssueApplication.class);

    private final RoleService roleService;
    private ModelMapper modelMapper;
    private UserService userService;

    public IssueApplication(RoleService roleService, ModelMapper modelMapper, UserService userService) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    public static void main(String[] args) {
        SpringApplication.run(IssueApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createRole("ROLE_LEAD");
        createRole("ROLE_DEV");
        createRole("ROLE_ADMIN");
        createAdmin();
    }

    private void createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        roleService.save(role);
    }

    private void createAdmin(){
        RegisterModel admin=new RegisterModel();
        admin.setEmail("admin@issue.com");
        admin.setFirstname("Ifelseco");
        admin.setPassword("admin");
        admin.setPhone("123456789");
        admin.setLastname("Tech");
        admin.setUsername("admin@issue.com");
        userService.createAdmin(modelMapper.map(admin, User.class));
        LOG.info("Admin user added successfuly.");
    }
}

