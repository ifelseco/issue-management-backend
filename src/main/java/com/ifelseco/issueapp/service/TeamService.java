package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.model.TeamModel;

import java.security.Principal;
import java.util.List;

public interface TeamService {
    TeamModel create(TeamModel teamModel, Principal principal);
    void addDeveloper(List<Long> developersIds);
    TeamModel update(TeamModel teamModel);
    void delete(long Id);
}
