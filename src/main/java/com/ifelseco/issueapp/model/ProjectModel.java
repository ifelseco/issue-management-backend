package com.ifelseco.issueapp.model;

import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.entity.Tenant;

import com.ifelseco.issueapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectModel {


    private Long id;
    private String name;
    private String description;
    private Set<MemberModel> members;
    private Team team;
}
