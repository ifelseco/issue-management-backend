package com.ifelseco.issueapp.controller;

import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.ErrorModel;
import com.ifelseco.issueapp.model.RegisterModel;
import com.ifelseco.issueapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/register")
public class RegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public RegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegisterModel registerModel, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity(convertValidationErrors(errors), HttpStatus.BAD_REQUEST);
        } else {
            if (userService.findByEmail(registerModel.getEmail()) != null) {
                return new ResponseEntity("Email has already registered", HttpStatus.BAD_REQUEST);
            } else if (userService.findByUsername(registerModel.getUsername()) != null) {
                return new ResponseEntity("Username has already registered", HttpStatus.BAD_REQUEST);
            } else {
                try {
                    User savingUser = modelMapper.map(registerModel, User.class);
                    savingUser = userService.createUser(savingUser);
                    return new ResponseEntity("User registered successfully, userId: " + savingUser.getId(), HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity("Db Error", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

    }

    private List<ErrorModel> convertValidationErrors(Errors errors) {
        return errors.getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
