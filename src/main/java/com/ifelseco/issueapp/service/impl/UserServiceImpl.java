package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.security.SecurityUtility;
import com.ifelseco.issueapp.entity.Role;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.entity.UserRole;
import com.ifelseco.issueapp.repository.RoleRepository;
import com.ifelseco.issueapp.repository.UserRepository;
import com.ifelseco.issueapp.service.ConfirmUserService;
import com.ifelseco.issueapp.service.EmailService;
import com.ifelseco.issueapp.service.RoleService;
import com.ifelseco.issueapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG= LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private EmailService emailService;
    private ConfirmUserService confirmUserService;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           EmailService emailService,
                           ConfirmUserService confirmUserService, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.confirmUserService = confirmUserService;
        this.roleService = roleService;
    }


    @Override
    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createLead(User user) {

        User savedUser=userRepository.findByUsername(user.getUsername());

        if (savedUser!=null) {
            LOG.info("User with username {} already exist."+user.getUsername());
        }else{
            user.setPassword(SecurityUtility.passwordEncoder().encode(user.getPassword()));
            createUserRole(user, "ROLE_LEAD");
            savedUser=userRepository.save(user);
            sendMail(savedUser);
        }
        return savedUser;
    }

    private void createUserRole(User user,String roleName) {
        Set<UserRole> userRoles = new HashSet<>();
        Role roleLead = roleService.findByName(roleName);

        if(roleLead != null){
            userRoles.add(new UserRole(user,roleLead));
        }

        userRoles.forEach(ur -> roleRepository.save(ur.getRole()));
        user.getUserRoles().addAll(userRoles);
    }

    //todo - create a method to save developer

    @Transactional
    public User createDeveloper(User user) {
            createUserRole(user,"ROLE_DEV");
            user = userRepository.save(user);
        return user;
    }

    @Override
    public User createAdmin(User user) {
        user.setPassword(SecurityUtility.passwordEncoder().encode(user.getPassword()));
        user.setEnabled(true);
        createUserRole(user,"ROLE_ADMIN");
        return userRepository.save(user);
    }

    private void sendMail(User savedUser) {
        if(savedUser!=null) {
            try {
                emailService.sendConfirmationToLead(savedUser);
            }catch(Exception e) {
                LOG.error("Email error {} "+e.getMessage());
            }
        }
    }


}
