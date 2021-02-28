package com.ifelseco.issueapp.controller;

import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.exceptionhandling.NotUniqueException;
import com.ifelseco.issueapp.model.RegisterModel;
import com.ifelseco.issueapp.repository.TenantRepository;
import com.ifelseco.issueapp.service.TenantService;
import com.ifelseco.issueapp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/register")
@Api("/api/register")
@AllArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final TenantService tenantService;

    @PostMapping("{tenantID}")
    @ApiOperation(value = "Register Lead", notes = "Register team lead.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity register(@PathVariable Long tenantID,@Valid @RequestBody RegisterModel registerModel) throws NotUniqueException {

        if (userService.findByEmail(registerModel.getEmail()) != null) {
            throw new NotUniqueException("Email");
        } else if (userService.findByUsername(registerModel.getUsername()) != null) {
            throw new NotUniqueException("Username");
        }

        try {
            User savingUser = modelMapper.map(registerModel, User.class);
            savingUser.setTenant(tenantService.findById(tenantID));
            savingUser = userService.createLead(savingUser);
            return new ResponseEntity("User registered successfully, userId: " + savingUser.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Db Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
