package com.ifelseco.issueapp.controller;


import com.ifelseco.issueapp.model.ProjectModel;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.service.ProjectService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@Api("/api/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create Project", notes = "Creating a new project", response = ProjectModel.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProjectModel.class),
            @ApiResponse(code = 400, message = "Bad request", response = ProjectModel.class)
    })
    public ResponseEntity createProject(
            @ApiParam(required = true, name = "project", value = "New project")
            @Valid @RequestBody ProjectModel projectModel, Principal principal) {
        return new ResponseEntity(projectService.create(projectModel, principal), HttpStatus.CREATED);
    }
}
