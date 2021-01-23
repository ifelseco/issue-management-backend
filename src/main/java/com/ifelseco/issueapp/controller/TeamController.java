package com.ifelseco.issueapp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifelseco.issueapp.entity.User;
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

    @PostMapping("/add")
   //     [2,6] will be sent from postman
    public ResponseEntity addSelectedDevelopers(@Valid @RequestBody List<Long> developersIds, Principal principal){
        teamService.addDevelopers(developersIds,principal);
        return new ResponseEntity("Developers are invited success", HttpStatus.OK);
    }
}
