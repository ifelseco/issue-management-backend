package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.config.AppConstants;
import com.ifelseco.issueapp.config.SecurityUtility;
import com.ifelseco.issueapp.entity.ConfirmUserToken;
import com.ifelseco.issueapp.entity.Role;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.entity.UserRole;
import com.ifelseco.issueapp.model.EmailModel;
import com.ifelseco.issueapp.repository.RoleRepository;
import com.ifelseco.issueapp.repository.UserRepository;
import com.ifelseco.issueapp.service.ConfirmUserService;
import com.ifelseco.issueapp.service.EmailService;
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

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           EmailService emailService,
                           ConfirmUserService confirmUserService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.confirmUserService = confirmUserService;
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
    public User createUser(User user) {

        User savedUser=userRepository.findByUsername(user.getUsername());

        if (savedUser!=null) {
            LOG.info("User with username {} already exist."+user.getUsername());
        }else{
            user.setPassword(SecurityUtility.passwordEncoder().encode(user.getPassword()));
            Set<UserRole> userRoles = createRole(user,"ROLE_LEAD");
            user.getUserRoles().addAll(userRoles);
            savedUser=userRepository.save(user);
            sendMail(savedUser);

        }

        return savedUser;
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

    private Set<UserRole> createRole(User user,String roleName) {
        Role role = new Role();
        role.setRoleId(1);
        role.setName(roleName);
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));

        for (UserRole ur : userRoles) {
            roleRepository.save(ur.getRole());
        }
        return userRoles;
    }





}
