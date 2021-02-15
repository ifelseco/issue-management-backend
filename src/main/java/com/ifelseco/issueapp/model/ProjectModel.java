package com.ifelseco.issueapp.model;

import com.ifelseco.issueapp.entity.Tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectModel {

    private String name;
    private List<MemberModel> members;
    private Tenant tenant;

}
