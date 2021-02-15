package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.entity.ConfirmUserToken;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.repository.ConfirmUserRepository;
import com.ifelseco.issueapp.service.ConfirmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ConfirmUserServiceImpl implements ConfirmUserService {


    @Autowired
    private ConfirmUserRepository confirmUserRepository;

    @Override
    public ConfirmUserToken findByToken(String token) {
        return confirmUserRepository.findByToken(token);
    }

    @Override
    public ConfirmUserToken createToken(User user) {

        ConfirmUserToken confirmUserToken=new ConfirmUserToken();
        confirmUserToken.setToken(UUID.randomUUID().toString());
        confirmUserToken.setExpiryDate(60*24);
        confirmUserToken.setUser(user);

        return confirmUserRepository.save(confirmUserToken);
    }

    @Override
    public void delete(ConfirmUserToken confirmUserToken) {
        confirmUserRepository.delete(confirmUserToken);
    }
}
