package com.ifelseco.issueapp.model;

import com.ifelseco.issueapp.entity.Project;
import com.ifelseco.issueapp.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@ApiModel(value = "Team Model", description = "Model for creating new team")
public class TeamModel {
    private Long id;
    private List<MemberModel> members;
    private List<ProjectModel> projects;
    private String name;
    private Date createTime;
    private MemberModel createdBy;
}
