package com.ifelseco.issueapp.model;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class TenantModel {

    private Long id;

    @NotBlank(message = "Tenantname is required!")
    private String tenantName;

    @NotBlank(message = "Phone is required!")
    private String phone;

    @NotBlank(message = "Adress is required!")
    private String address;


    @Email(message = "Invalid email format")
    private String email;

    private String tenantCode;

    private boolean enabled=true;

    private List<TeamModel> teams=new ArrayList<>();



}
