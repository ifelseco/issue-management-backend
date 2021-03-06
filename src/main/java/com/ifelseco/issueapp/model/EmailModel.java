package com.ifelseco.issueapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailModel {
    private String from;
    private String to;
    private String subject;
    private String templateName;
    private Map<String, Object> model;

}
