package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.TeamModel;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface TeamService {
    TeamModel create(TeamModel teamModel, Principal principal);
    void sendInvitationToDeveloper(String email, Principal principal, Long teamId);

    User confirmInvitationEmail(String uuid,Long teamId);

    void deleteTeam(Long teamId);

    TeamModel findTeamById(Long teamId);

    Set<TeamModel> findAllTeams();

    TeamModel editTeam(Long teamId, TeamModel teamModel);

}
