package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.model.IssueListModel;

public interface IssueService {

    IssueListModel getIssueList(Long projectId);
}
