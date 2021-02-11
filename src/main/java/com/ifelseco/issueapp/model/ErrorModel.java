package com.ifelseco.issueapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Path;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorModel {
    private String field;
    private Object rejectedValue;
    private String message;
}
