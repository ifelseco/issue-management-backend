package com.ifelseco.issueapp;

import com.ifelseco.issueapp.entity.Role;
import com.ifelseco.issueapp.entity.Tenant;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.RegisterModel;
import com.ifelseco.issueapp.service.RoleService;
import com.ifelseco.issueapp.service.TenantService;
import com.ifelseco.issueapp.service.UserService;
import com.ifelseco.issueapp.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class IssueApplication implements CommandLineRunner {
    private static final Logger LOG= LoggerFactory.getLogger(IssueApplication.class);

    private final RoleService roleService;
    private ModelMapper modelMapper;
    private UserService userService;
    private TenantService tenantService;



    public static void main(String[] args) {
        SpringApplication.run(IssueApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createRole("ROLE_LEAD");
        createRole("ROLE_DEV");
        createRole("ROLE_ADMIN");
        createAdmin();
        createTenant();
    }

    private void createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        roleService.save(role);
    }

    private void createAdmin(){
        User admin=new User();
        admin.setEmail("admin@issue.com");
        admin.setFullName("Ifelseco Tech");
        admin.setPassword("admin");
        admin.setPhone("123456789");
        admin.setUsername("admin@issue.com");
        admin.setEnabled(true);
        userService.createAdmin(admin);
        LOG.info("Admin user added successfuly.");
    }

    private void createTenant(){
        Tenant tenant=new Tenant();
        tenant.setTenantName("tenant");
        tenant.setEmail("tenant@issue.com");
        tenant.setPhone("123456789");
        tenant.setAddress("asdf");
        tenant.setTenantCode("123");
        tenant.setEnabled(true);
        tenantService.createTenant(tenant);
        LOG.info("Tenant added successfuly.");
    }



}

