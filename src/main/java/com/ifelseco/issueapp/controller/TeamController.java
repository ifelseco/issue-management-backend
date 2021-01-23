package com.ifelseco.issueapp.controller;

import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

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
}
