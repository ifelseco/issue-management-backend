package com.ifelseco.issueapp.model;

import com.ifelseco.issueapp.entity.Project;
import com.ifelseco.issueapp.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;


@Data
public class TeamModel {
    private Long id;
    private Set<User> members;
    private Set<Project> projects;
    private String name;
    private Date createTime;
    private Long createdBy;
}
