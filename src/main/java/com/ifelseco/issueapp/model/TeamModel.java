package com.ifelseco.issueapp.model;

import com.ifelseco.issueapp.entity.Project;
import com.ifelseco.issueapp.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.Date;
import java.util.Set;


@Data
@ApiModel(value = "Team Model", description = "Model for creating new team")
public class TeamModel {
    private Long id;
    private Set<User> members;
    private Set<Project> projects;
    private String name;
    private Date createTime;
    private Long createdBy;
}
