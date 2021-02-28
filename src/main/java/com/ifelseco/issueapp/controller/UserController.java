package com.ifelseco.issueapp.controller;


import com.ifelseco.issueapp.entity.ConfirmUserToken;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.BaseResponseModel;
import com.ifelseco.issueapp.service.ConfirmUserService;
import com.ifelseco.issueapp.service.UserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
@AllArgsConstructor
public class UserController {

    private ConfirmUserService confirmUserService;
    private UserService userService;


    @GetMapping("/confirm-email")
    public ResponseEntity<BaseResponseModel> confirmUserEmail(@RequestParam("uuid") String uuid) {

        BaseResponseModel responseModel=new BaseResponseModel();

        try {
            ConfirmUserToken confirmUserToken=confirmUserService.findByToken(uuid);
            return checkByConfirmUserToken(responseModel, confirmUserToken);
        }catch (Exception e) {
            responseModel.setResponseCode(500);
            responseModel.setResponseMessage("System Error...");
            return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private ResponseEntity<BaseResponseModel> checkByConfirmUserToken(BaseResponseModel responseModel, ConfirmUserToken confirmUserToken) {
        if(confirmUserToken==null) {
            responseModel.setResponseCode(400);
            responseModel.setResponseMessage("Thanks for confirming your email, but your confirmation code is invalid");
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }else if(confirmUserToken.isExpired()){
            responseModel.setResponseCode(400);
            responseModel.setResponseMessage("Thanks for confirming your email, but your confirmation code has expired");
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }else {
            try {
                User user=userService.findByEmail(confirmUserToken.getUser().getEmail());
                return checkUserByConfirmationToken(responseModel, user);
            }catch(Exception e) {
                responseModel.setResponseCode(500);
                responseModel.setResponseMessage("System Error...");
                return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private ResponseEntity<BaseResponseModel> checkUserByConfirmationToken(BaseResponseModel responseModel, User user) {
        if(user==null) {
            responseModel.setResponseCode(400);
            responseModel.setResponseMessage("Thanks for confirming your email, but your confirmation code is invalid");
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }else {
            user.setEnabled(true);
            userService.createLead(user);
            responseModel.setResponseCode(200);
            responseModel.setResponseMessage("Your confirmation is successful. You can now login.");
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }


}
