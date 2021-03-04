package com.ifelseco.issueapp.controller;


import com.ifelseco.issueapp.model.ProjectModel;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.service.ProjectService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/project")
@Api("/api/project")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/create/{selectedMemberIds}")
    public ResponseEntity createProject(
            @PathVariable List<Long> selectedMemberIds,
            @Valid @RequestBody ProjectModel projectModel, Principal principal) {
        return new ResponseEntity(projectService.create(selectedMemberIds, projectModel, principal), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{projectId}")
    public void deleteTeam(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }

    @GetMapping("{projectId}")
    public ResponseEntity<TeamModel> getProjectById(@PathVariable("projectId") Long projectId) {
        return new ResponseEntity(projectService.findProjectById(projectId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Set<ProjectModel>> getAllProjects() {
        return new ResponseEntity(projectService.findAllProjects(), HttpStatus.OK);
    }

    @PutMapping("edit/{projectId}")
    public ResponseEntity<TeamModel> editProject(@PathVariable("projectId") Long projectId, @RequestBody ProjectModel projectModel){
        return new ResponseEntity(projectService.editProject(projectId, projectModel), HttpStatus.OK);
    }
}
