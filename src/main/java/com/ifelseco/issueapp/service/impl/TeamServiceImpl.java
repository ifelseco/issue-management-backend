package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.config.ConstApp;
import com.ifelseco.issueapp.entity.ConfirmUserToken;
import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.mapping.impl.FromTeamModelToTeam;
import com.ifelseco.issueapp.mapping.impl.FromTeamToTeamModel;
import com.ifelseco.issueapp.model.BaseResponseModel;
import com.ifelseco.issueapp.model.EmailModel;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.repository.TeamRepository;
import com.ifelseco.issueapp.repository.UserRepository;
import com.ifelseco.issueapp.service.ConfirmUserService;
import com.ifelseco.issueapp.service.EmailService;
import com.ifelseco.issueapp.service.TeamService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final FromTeamModelToTeam fromTeamModelToTeam;
    private final FromTeamToTeamModel fromTeamToTeamModel;
    private final ConfirmUserService confirmUserService;
    private final EmailService emailService;



    public TeamServiceImpl(TeamRepository teamRepository,
                           UserRepository userRepository,
                           FromTeamModelToTeam fromTeamModelToTeam,
                           FromTeamToTeamModel fromTeamToTeamModel,
                           ConfirmUserService confirmUserService,
                           EmailService emailService) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.fromTeamModelToTeam = fromTeamModelToTeam;
        this.fromTeamToTeamModel = fromTeamToTeamModel;
        this.emailService = emailService;
        this.confirmUserService = confirmUserService;
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
    public void addDevelopers(List<Long> developersIds, Principal principal, Long teamId) {
        for(long id: developersIds){
            User user = userRepository.findById(id)
                    .orElseThrow(NoSuchElementException::new);
            sendInvitationEmail(user,emailService);
        }
        //TODO: user should be added to teams developers list

    }

    @Override
    public TeamModel update(TeamModel teamModel) {
        return null;
    }

    @Override
    public void delete(long Id) {

    }

    private void sendInvitationEmail(User developer, EmailService emailService) {

        ConfirmUserToken confirmUserToken = new ConfirmUserToken();
        confirmUserToken.setToken(UUID.randomUUID().toString());
        confirmUserToken.setExpiryDate(60*24);
        confirmUserToken.setUser(developer);
        confirmUserService.save(confirmUserToken);

        Map<String, Object> model = new HashMap<>();
        model.put("firstName",developer.getFirstname());
        model.put("confirmUrl", ConstApp.WEB_URL+"/invitation-confirm-email?uuid="+confirmUserToken.getToken());
        model.put("signature","Issue Management");

        EmailModel emailModel=new EmailModel(ConstApp.FROM_EMAIL,developer.getEmail(),"Issue Management: Invitation Confirm",model);

        emailService.sendInvitationEmail(emailModel);

    }

    public Boolean confirmInvitationEmail(String uuid) {

        BaseResponseModel responseModel=new BaseResponseModel();

        ConfirmUserToken confirmUserToken=confirmUserService.findByToken(uuid);

        User user=userRepository.findByEmail(confirmUserToken.getUser().getEmail());

         return true;

    }











}
