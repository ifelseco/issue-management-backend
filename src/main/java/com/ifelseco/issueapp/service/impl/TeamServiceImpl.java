package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.mapping.impl.FromTeamModelToTeam;
import com.ifelseco.issueapp.mapping.impl.FromTeamToTeamModel;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.repository.TeamRepository;
import com.ifelseco.issueapp.repository.UserRepository;
import com.ifelseco.issueapp.service.TeamService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final FromTeamModelToTeam fromTeamModelToTeam;
    private final FromTeamToTeamModel fromTeamToTeamModel;

    public TeamServiceImpl(TeamRepository teamRepository, UserRepository userRepository, FromTeamModelToTeam fromTeamModelToTeam, FromTeamToTeamModel fromTeamToTeamModel) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.fromTeamModelToTeam = fromTeamModelToTeam;
        this.fromTeamToTeamModel = fromTeamToTeamModel;
    }

    @Override
    public TeamModel create(TeamModel teamModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Team team = fromTeamModelToTeam.convert(teamModel);
        team.setCreatedBy(user.getId());
        TeamModel teamModel1 = fromTeamToTeamModel.convert(teamRepository.save(team));
        return teamModel1;
    }

    @Override
    public void addDeveloper(List<Long> developersIds) {

    }

    @Override
    public TeamModel update(TeamModel teamModel) {
        return null;
    }

    @Override
    public void delete(long Id) {

    }
}
