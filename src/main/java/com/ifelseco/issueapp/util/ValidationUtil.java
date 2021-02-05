package com.ifelseco.issueapp.util;


import com.ifelseco.issueapp.model.ErrorModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationUtil {

    public List<ErrorModel> convertValidationErrors(Errors errors) {
        return errors.getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());
    }

}
