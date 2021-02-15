package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.model.ProjectModel;
import com.ifelseco.issueapp.repository.ProjectRepository;
import com.ifelseco.issueapp.service.ProjectService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    //TODO: Ask for create scenario

    @Override
    public ProjectModel create(ProjectModel projectModel, Principal principal) {
        return null;
    }
}
