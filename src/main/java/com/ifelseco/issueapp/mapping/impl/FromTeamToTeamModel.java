package com.ifelseco.issueapp.mapping.impl;


import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.mapping.Converter;
import com.ifelseco.issueapp.model.TeamModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FromTeamToTeamModel implements Converter<Team, TeamModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TeamModel convert(Team team) {

        TeamModel teamModel = modelMapper.map(team, TeamModel.class);
        return teamModel;

    }
}