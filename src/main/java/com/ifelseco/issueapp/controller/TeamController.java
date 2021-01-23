package com.ifelseco.issueapp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifelseco.issueapp.entity.ConfirmUserToken;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.BaseResponseModel;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    @PostMapping("/create")
    public ResponseEntity<TeamModel> createTeam(@Valid @RequestBody TeamModel teamModel, Principal principal){
        return new ResponseEntity<>(teamService.create(teamModel,principal), HttpStatus.CREATED);
    }

    @PostMapping("/add/{teamId}")
   //     [2,6] will be sent from postman
    public ResponseEntity addSelectedDevelopers(@Valid @RequestBody List<Long> developersIds, Principal principal, @PathVariable Long teamId){
        teamService.addDevelopers(developersIds,principal,teamId);
        return new ResponseEntity("Developers are invited success", HttpStatus.OK);
    }

    @GetMapping("/confirm-invitation-email")
    public ResponseEntity confirmInvitationEmail(@RequestParam("uuid") String uuid) {

        //TODO: user should be added to teams developers list

        return new ResponseEntity<>("Joining the team has been successfully completed", HttpStatus.OK);
    }

}
