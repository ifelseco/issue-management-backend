package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.config.AppConstants;
import com.ifelseco.issueapp.config.EmailConstants;
import com.ifelseco.issueapp.entity.ConfirmUserToken;
import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.EmailModel;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.repository.TeamRepository;
import com.ifelseco.issueapp.repository.UserRepository;
import com.ifelseco.issueapp.service.ConfirmUserService;
import com.ifelseco.issueapp.service.EmailService;
import com.ifelseco.issueapp.service.TeamService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamServiceImpl.class);
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ConfirmUserService confirmUserService;
    private final EmailService emailService;



    public TeamServiceImpl(TeamRepository teamRepository,
                           UserRepository userRepository,
                           ModelMapper modelMapper,
                           ConfirmUserService confirmUserService,
                           EmailService emailService) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.confirmUserService = confirmUserService;
    }

    @Override
    public TeamModel create(TeamModel teamModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Team team = modelMapper.map(teamModel,Team.class);
        Set<User> userSet=new HashSet<>();
        userSet.add(user);
        team.setMembers(userSet);
        team.setCreatedBy(user.getId());
        TeamModel teamModel1 = modelMapper.map(teamRepository.save(team),TeamModel.class);
        return teamModel1;
    }

    @Override
    public void sendInvitationToSelectedDevelopers(List<Long> developersIds, Principal principal, Long teamId) {
        try{
            Team team=teamRepository.findById(teamId).orElseThrow(NoSuchElementException::new);
            if(team!=null) {
                for(long id: developersIds){
                    User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
                    emailService.sendConfirmationToDeveloper(user,team);
                }
            }else {
                LOG.error("Team id is not valid");
            }

        }catch(Exception e){
            LOG.error("Team or user is not valid",e.getMessage());
        }


    }

    @Override
    public TeamModel update(TeamModel teamModel) {
        return null;
    }

    @Override
    public void delete(long Id) {

    }

    public User confirmInvitationEmail(String uuid,Long teamId) {

        ConfirmUserToken confirmUserToken=confirmUserService.findByToken(uuid);

        User user=userRepository.findByEmail(confirmUserToken.getUser().getEmail());

        addDeveloperToTeam(user,teamId);
         return user;

    }

    private void addDeveloperToTeam(User user, Long teamId) {
        Team team = teamRepository.findById(teamId).
                orElseThrow(NoSuchElementException::new);
        team.getMembers().add(user);
        teamRepository.save(team);
    }


}
