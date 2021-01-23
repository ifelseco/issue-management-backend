package com.ifelseco.issueapp.mapping.impl;


import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.mapping.Converter;
import com.ifelseco.issueapp.model.TeamModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FromTeamModelToTeam implements Converter<TeamModel, Team> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Team convert(TeamModel teamModel) {

        Team team = modelMapper.map(teamModel,Team.class);
        return team;

    }
}
