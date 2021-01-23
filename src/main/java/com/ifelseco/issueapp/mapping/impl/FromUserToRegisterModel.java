package com.ifelseco.issueapp.mapping.impl;

import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.mapping.Converter;
import com.ifelseco.issueapp.model.RegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Moon on 1/23/2021*/
@Service
public class FromUserToRegisterModel implements Converter<User, RegisterModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegisterModel convert(User user) {

        RegisterModel registerModel = modelMapper.map(user,RegisterModel.class);
        return registerModel;

    }
}
