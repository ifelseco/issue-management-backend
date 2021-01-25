package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    private UserRepository userRepository;

    private static final Logger LOG= LoggerFactory.getLogger(UserSecurityService.class);


    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email);

        if(user==null) {
            LOG.warn("Username {} not found"+email);
            throw new UsernameNotFoundException("Email "+email);
        }

        return user;
    }
}
