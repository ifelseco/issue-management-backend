package com.ifelseco.issueapp.controller;

import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.exceptionhandling.NotUniqueException;
import com.ifelseco.issueapp.model.RegisterModel;
import com.ifelseco.issueapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/register")
//@Api("/api/register")
public class RegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public RegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping
//    @ApiOperation(value = "Register Lead", notes = "Register team lead.")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Success"),
//            @ApiResponse(code = 400, message = "Bad request"),
//            @ApiResponse(code = 500, message = "Internal Server Error")
//    })

    public ResponseEntity register(@Valid @RequestBody RegisterModel registerModel) throws NotUniqueException {

        if (userService.findByEmail(registerModel.getEmail()) != null) {
            throw new NotUniqueException("Email");
        } else if (userService.findByUsername(registerModel.getUsername()) != null) {
            throw new NotUniqueException("Username");
        }

        try {
            User savingUser = modelMapper.map(registerModel, User.class);
            savingUser = userService.createLead(savingUser);
            return new ResponseEntity("User registered successfully, userId: " + savingUser.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Db Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    private List<ErrorModel> convertValidationErrors(Errors errors) {
//        return errors.getFieldErrors().stream()
//                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
//                .collect(Collectors.toList());
//    }

//    @ExceptionHandler(value= ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity handleException(ConstraintViolationException exception) {
//
//        List<ErrorModel> errorMessages = exception.getConstraintViolations().stream()
//                .map(err -> new ErrorModel(err.getPropertyPath().toString(), err.getInvalidValue(), err.getMessage()))
//                .distinct()
//                .collect(Collectors.toList());
//        return new ResponseEntity(errorMessages , HttpStatus.BAD_REQUEST);
//    }


//        @ResponseStatus(HttpStatus.BAD_REQUEST)
//        @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity handleInvalidInput(MethodArgumentNotValidException e) {
//        List<ErrorModel> errors =  e.getBindingResult().getFieldErrors().stream()
//                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
//                .collect(Collectors.toList());
//
//        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
//    }

}
