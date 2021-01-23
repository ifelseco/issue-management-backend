package com.ifelseco.issueapp.mapping.impl;


import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.mapping.Converter;
import com.ifelseco.issueapp.model.RegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Moon on 1/23/2021
 */
@Service
public class FromRegisterModelToUser implements Converter<RegisterModel, User> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User convert(RegisterModel registerModel) {

        User user = modelMapper.map(registerModel,User.class);
        return user;

    }
}
