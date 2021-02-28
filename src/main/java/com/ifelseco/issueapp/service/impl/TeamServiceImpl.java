package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.entity.*;
import com.ifelseco.issueapp.model.MemberModel;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.model.TenantShowModel;
import com.ifelseco.issueapp.repository.TeamRepository;
import com.ifelseco.issueapp.repository.UserRepository;
import com.ifelseco.issueapp.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;


@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ConfirmUserService confirmUserService;
    private final EmailService emailService;
    private final RoleService roleService;

    @Override
    public TeamModel create(TeamModel teamModel, Principal principal) {

        User user = userRepository.findByUsername(principal.getName());
        Tenant tenant=user.getTenant();
        Team team = modelMapper.map(teamModel,Team.class);

        setTeamsFieldsBeforeSave(user, tenant, team);

        TeamModel newTeamModel = getTeamModel(user, team);

        return newTeamModel;
    }

    private TeamModel getTeamModel(User user, Team team) {
        Team savedTeam = teamRepository.save(team);

        TeamModel newTeamModel = new TeamModel();
        MemberModel lead = modelMapper.map(user, MemberModel.class);
        List<MemberModel> memberModelList = new ArrayList<>();
        memberModelList.add(lead);

        newTeamModel.setCreateTime(savedTeam.getCreateTime());
        newTeamModel.setCreatedBy(lead);

        //newTeamModel.setTenantShowModel(new TenantShowModel(savedTeam.getTenant().getTenantName(),savedTeam.getTenant().getTenantCode()));

        newTeamModel.setTenantShowModel(modelMapper.map(savedTeam.getTenant(),TenantShowModel.class));
        newTeamModel.setId(savedTeam.getId());
        newTeamModel.setMembers(memberModelList);
        newTeamModel.setName(savedTeam.getName());

        //TODO : Project eklenince ProjectModel e cevirip set edilecek
        // newTeamModel.setProjects(savedTeam.getProjects());

        return newTeamModel;
    }

    private void setTeamsFieldsBeforeSave(User user, Tenant tenant, Team team) {
        Set<User> userSet= new HashSet<>();
        userSet.add(user);
        team.setMembers(userSet);
        team.setCreatedBy(user.getId());
        team.setCreateTime(new Date());
        team.setTenant(tenant);
    }

    @Override
    public void sendInvitationToDeveloper(String email, Principal principal, Long teamId) {
        try{
            Team team=teamRepository.findById(teamId).orElseThrow(NoSuchElementException::new);
            if(team!=null) {
                    User user = userRepository.findByEmail(email);
                    if(user == null) {
                        User newUser = new User();
                        newUser.setEmail(email);
                        newUser.setCandidate(true);
                        user = userRepository.save(newUser);
                    }
                    emailService.sendConfirmationToDeveloper(user,team);
            }else {
                log.error("Team id is not valid");
            }
        }catch(Exception e){
            log.error("Team or user is not valid",e.getMessage());
        }

    }



    public User confirmInvitationEmail(String uuid,Long teamId) {

        try {
            ConfirmUserToken confirmUserToken = confirmUserService.findByToken(uuid);
            User user = userRepository.findByEmail(confirmUserToken.getUser().getEmail());
            //if this user is a new user
            if (user.isCandidate()) {
                return user;
            } else {
                if (!checkDev(user)) {
                    Role devRole = roleService.findByName("ROLE_DEV");
                    user.getUserRoles().add(new UserRole(user, devRole));
                }
                addDeveloperToTeam(user, teamId);
                return user;
            }
        } catch (Exception e) {
            log.error("Token,team or user is not found", e.getMessage());
        }
        return null;
    }



    private boolean checkDev(User user) {
        boolean isDev = false;
        Set<UserRole> userRoles = user.getUserRoles();
        for (UserRole ur : userRoles){
            if(ur.getRole().getName().equals("ROLE_DEV")){
                isDev = true;
            }
        }
        return isDev;
    }

    private void addDeveloperToTeam(User user, Long teamId) {
        Team team = teamRepository.findById(teamId).
                orElseThrow(NoSuchElementException::new);
        team.getMembers().add(user);
        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long teamId) {

        teamRepository.deleteById(teamId);
    }

    @Override
    public TeamModel findTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(NoSuchElementException::new);

        User user = userRepository.findById(team.getCreatedBy())
                .orElseThrow(NoSuchElementException::new);

        return getTeamModel(user, team);

    }

    @Override
    public Set<TeamModel> findAllTeams() {
       List<Team> teamSet = teamRepository.findAll();
        return convertTeamSetToTeamModelSet(teamSet);
    }

    @Override
    public TeamModel editTeam(Long teamId, TeamModel teamModel) {
        Team updatedTeam = teamRepository.findById(teamId)
                .orElseThrow(NoSuchElementException::new);

        modelMapper.getConfiguration().setSkipNullEnabled(true); //configure ModelMapper to ignore all properties that are null
        modelMapper.map(teamModel, updatedTeam);

        User user = userRepository.findById(updatedTeam.getCreatedBy())
                .orElseThrow(NoSuchElementException::new);

        return getTeamModel(user, updatedTeam);

    }


    private Set<TeamModel> convertTeamSetToTeamModelSet(List<Team> teamSet){
        Set<TeamModel> teamModelSet = new HashSet<>();
        TeamModel teamModel;

        for(Team team : teamSet){
            User user = userRepository.findById(team.getCreatedBy())
                    .orElseThrow(NoSuchElementException::new);

            teamModel= getTeamModel(user, team);
            teamModelSet.add( teamModel );
        }
        return teamModelSet;
    }

}
