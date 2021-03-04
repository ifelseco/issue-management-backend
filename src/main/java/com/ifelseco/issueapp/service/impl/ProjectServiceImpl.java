package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.entity.Project;
import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.entity.Tenant;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.MemberModel;
import com.ifelseco.issueapp.model.ProjectModel;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.model.TenantShowModel;
import com.ifelseco.issueapp.repository.ProjectRepository;
import com.ifelseco.issueapp.repository.TeamRepository;
import com.ifelseco.issueapp.repository.UserRepository;
import com.ifelseco.issueapp.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;

    @Override
    public ProjectModel create(List<Long> selectedIds, ProjectModel projectModel, Principal principal) {
        User lead = userRepository.findByUsername(principal.getName());
        List<User> members = new ArrayList<>();
        if(selectedIds != null){
            members = convertAllMembersToUser(selectedIds);
        }
        members.add(lead);
        Project project = convertProjectModelToProject(members,lead,projectModel);
        Project savedProject = projectRepository.save(project);
        ProjectModel returnProjectModel = convertProjectToProjectModel(savedProject);
        return returnProjectModel ;
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public Set<ProjectModel> findAllProjects() {
        List<Project> projectList = projectRepository.findAll();
        return convertProjectSetToProjectModelSet(projectList);
    }

    @Override
    public ProjectModel findProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(NoSuchElementException::new);

        return convertProjectToProjectModel(project);

    }

    @Override
    public ProjectModel editProject(Long projectId, ProjectModel projectModel) {
        Project project = projectRepository.findById(projectId).orElseThrow(NoSuchElementException::new);
        // TODO: 2/28/21  I think this is not the efficient way. Need to find a way of using modelmapper
        project.setDescription(projectModel.getDescription());
        project.setTeam(projectModel.getTeam());
        project.setName(projectModel.getName());
        Set<User> newMembers = convertAllMembersToUser(projectModel.getMembers());
        project.setMembers(newMembers);
        Project updatedProject = projectRepository.save(project);
        
        return convertProjectToProjectModel(updatedProject);
        
    }

    private Set<ProjectModel> convertProjectSetToProjectModelSet(List<Project> projectList) {
        Set<ProjectModel> projectModelsList = projectList.stream().map((project) -> convertProjectToProjectModel(project)).collect(Collectors.toSet());
        return projectModelsList;
    }

    private ProjectModel convertProjectToProjectModel(Project savedProject) {
        ProjectModel newProjectModel = new ProjectModel();
        Set<MemberModel> memberModels = convertAllUserToMemberModel(savedProject.getMembers());
        newProjectModel.setMembers(memberModels);
        newProjectModel.setId(savedProject.getId());
        newProjectModel.setDescription(savedProject.getDescription());
        newProjectModel.setName(savedProject.getName());
        return newProjectModel;
    }

    private Set<MemberModel> convertAllUserToMemberModel(Set<User> members) {
        Set<MemberModel> memberModels = new HashSet<>();
        for (User user: members) {
            memberModels.add(modelMapper.map(user, MemberModel.class));
        }
        return memberModels;
    }

    private List<User> convertAllMembersToUser(List<Long> selectedIds) {
        return selectedIds.stream().map(id -> userRepository.findById(id).orElseThrow(NoSuchElementException::new)).collect(Collectors.toList());
    }

    private Set<User> convertAllMembersToUser(Set<MemberModel> memberModels) {
        return memberModels.stream().map(memberModel -> userRepository.findByUsername(memberModel.getUsername())).collect(Collectors.toSet());
    }

    private Project convertProjectModelToProject(List<User> members,User lead, ProjectModel projectModel) {
        Project project = modelMapper.map(projectModel,Project.class);
        Tenant tenant = lead.getTenant();
        Team team = teamRepository.findByCreatedBy(lead.getId());
        Set<User> membersSet = new HashSet<>(members);
        project.setMembers((membersSet));
        project.setTeam(team);
        project.setTenant(tenant);
        return project;
    }

}
