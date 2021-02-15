package com.ifelseco.issueapp.service;


import com.ifelseco.issueapp.model.ProjectModel;

import java.security.Principal;

public interface ProjectService {
    ProjectModel create(ProjectModel projectModel, Principal principal);

}
