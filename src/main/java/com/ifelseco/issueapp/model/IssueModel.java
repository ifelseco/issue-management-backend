package com.ifelseco.issueapp.model;


import lombok.Data;

import java.util.Date;

@Data
public class IssueModel {
    private Long id;
    private String name;
    private String description;
    private Date createdDate;
    private MemberModel createdBy;
}
