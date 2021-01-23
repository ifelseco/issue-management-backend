package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.TeamModel;

import java.security.Principal;
import java.util.List;

public interface TeamService {
    TeamModel create(TeamModel teamModel, Principal principal);
    void addDevelopers(List<Long> developersIds, Principal principal);
    TeamModel update(TeamModel teamModel);
    void delete(long Id);

}
