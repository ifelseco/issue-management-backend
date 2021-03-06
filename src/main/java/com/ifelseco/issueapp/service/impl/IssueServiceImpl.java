package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.entity.Project;
import com.ifelseco.issueapp.model.IssueListModel;
import com.ifelseco.issueapp.model.IssueModel;
import com.ifelseco.issueapp.repository.IssueRepository;
import com.ifelseco.issueapp.repository.ProjectRepository;
import com.ifelseco.issueapp.service.IssueService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class IssueServiceImpl implements IssueService {

    private IssueRepository issueRepository;
    private ProjectRepository projectRepository;
    private ModelMapper modelMapper;

    @Override
    public IssueListModel getIssueList(Long projectId) {
        Project project=projectRepository.findById(projectId).orElseThrow(NoSuchElementException :: new );
        IssueListModel issueListModel=new IssueListModel();
        convertIssuesToIssueListModel(project, issueListModel);
        return issueListModel;
    }

    private void convertIssuesToIssueListModel(Project project, IssueListModel issueListModel) {
        issueRepository.findByProject(project).forEach(issue -> {
            IssueModel issueModel=modelMapper.map(issue,IssueModel.class);
            issueListModel.getIssueModelList().add(issueModel);
        });
    }
}
