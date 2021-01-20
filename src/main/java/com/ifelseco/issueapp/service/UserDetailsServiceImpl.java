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

@Service("userSecurityService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    private static final Logger LOG= LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String u) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(u);

        if(user==null) {
            LOG.warn("Username {} not found"+u);
            throw new UsernameNotFoundException("Username "+u);
        }

        return user;
    }
}
