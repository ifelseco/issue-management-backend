package com.ifelseco.issueapp.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Base Response", description = "Base response model")
public class BaseResponseModel {
    private int responseCode;
    private String responseMessage;
}
