package com.ifelseco.issueapp.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IssueListModel {

    private List<IssueModel> issueModelList=new ArrayList<>();
}
