package com.ifelseco.issueapp.service;


import com.ifelseco.issueapp.model.ProjectModel;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface ProjectService {
    ProjectModel create(List<Long> selectedIds,ProjectModel projectModel, Principal principal);
    void deleteProject(Long projectId);
    Set<ProjectModel> findAllProjects();
    ProjectModel findProjectById(Long projectId);
    ProjectModel editProject(Long projectId, ProjectModel projectModel);
}
