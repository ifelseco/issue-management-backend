package com.ifelseco.issueapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TenantShowModel {

    private String tenantName;
    private String tenantCode;


}
