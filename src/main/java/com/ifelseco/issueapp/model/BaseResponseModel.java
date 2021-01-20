package com.ifelseco.issueapp.model;

import lombok.Data;

@Data
public class BaseResponseModel {
    private int responseCode;
    private String responseMessage;
}
