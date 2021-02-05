package com.ifelseco.issueapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@ApiModel(value = "Register User Model", description = "Model for creating lead user")
public class RegisterModel {

    private BaseResponseModel responseModel;

    @NotBlank(message = "Firstname is required!")
    private String firstname;

    @NotBlank(message = "Lastname is required!")
    private String lastname;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 2,max = 16)
    private String username;

    @NotBlank(message="Password is required")
    private String password;

    private String phone;

}
