package com.ifelseco.issueapp.controller;


import com.ifelseco.issueapp.entity.User;
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
    public ResponseEntity addDevelopersByEmail(@Valid @RequestBody String email, Principal principal, @PathVariable Long teamId){
        teamService.sendInvitationToDeveloper(email,principal,teamId);
        return new ResponseEntity("Developers are invited success", HttpStatus.OK);
    }



    @GetMapping("/invitation-confirm-email")
    public ResponseEntity<User> confirmInvitationEmail(@RequestParam("uuid") String uuid,@RequestParam("teamId") Long teamId) {
        User user = teamService.confirmInvitationEmail(uuid,teamId);

        return new ResponseEntity<User>(user,HttpStatus.OK);

    }



}
