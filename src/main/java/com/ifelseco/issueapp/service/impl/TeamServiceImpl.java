package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.entity.*;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.repository.TeamRepository;
import com.ifelseco.issueapp.repository.UserRepository;
import com.ifelseco.issueapp.service.ConfirmUserService;
import com.ifelseco.issueapp.service.EmailService;
import com.ifelseco.issueapp.service.RoleService;
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
    private final UserServiceImpl userServiceImpl;
    private final RoleService roleService;



    public TeamServiceImpl(TeamRepository teamRepository,
                           UserRepository userRepository,
                           ModelMapper modelMapper,
                           ConfirmUserService confirmUserService,
                           EmailService emailService, UserServiceImpl userServiceImpl, RoleService roleService) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.confirmUserService = confirmUserService;
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;
    }

    @Override
    public TeamModel create(TeamModel teamModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Team team = modelMapper.map(teamModel,Team.class);
        Set<User> userSet=new HashSet<>();
        userSet.add(user);
        team.setMembers(userSet);
        team.setCreatedBy(user.getId());
        team.setCreateTime(new Date());
        TeamModel teamModel1 = modelMapper.map(teamRepository.save(team),TeamModel.class);
        return teamModel1;
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

        //todo: Check if token is valid - exception handling - think of using try catch if you are interacted with db
        ConfirmUserToken confirmUserToken=confirmUserService.findByToken(uuid);

        User user=userRepository.findByEmail(confirmUserToken.getUser().getEmail());
        //if this user is a new user
        if(user.isCandidate()) {
            return user;
        }else{
            if(!checkDev(user)) {
                Role devRole = roleService.findByName("ROLE_DEV");
                user.getUserRoles().add(new UserRole(user, devRole));
            }

            addDeveloperToTeam(user,teamId);
            return user;
        }
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

}
