package com.ifelseco.issueapp.controller;


import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.TeamModel;
import com.ifelseco.issueapp.service.TeamService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/team")
@Api("/api/team")
@AllArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/create")
    @ApiOperation(value = "Create Team", notes = "Creating a new team", response = TeamModel.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TeamModel.class),
            @ApiResponse(code = 400, message = "Bad request", response = TeamModel.class)
    })
    public ResponseEntity createTeam(
            @ApiParam(required = true, name = "team", value = "New team")
            @Valid @RequestBody TeamModel teamModel, Principal principal) {
        return new ResponseEntity(teamService.create(teamModel, principal), HttpStatus.CREATED);
    }

    @PostMapping("/add/{teamId}")
    @ApiOperation(value = "Add Developer By Email", notes = "Send email to developer for confirm")
    public ResponseEntity addDevelopersByEmail(
            @ApiParam(required = true, name = "Email", value = "Developer Email")
            @Valid @RequestBody String email, Principal principal,
            @ApiParam(required = true, name = "TeamId", value = "Team Id")
            @PathVariable Long teamId) {

        teamService.sendInvitationToDeveloper(email, principal, teamId);
        return new ResponseEntity("Developers are invited success", HttpStatus.OK);
    }


    @GetMapping("/invitation-confirm-email")
    @ApiOperation(value = "Confirm Email", notes = "Handle Developer Confirmation From Email" )
    public ResponseEntity confirmInvitationEmail(
            @ApiParam(required = true, name = "UUID", value = "UUID")
            @RequestParam("uuid") String uuid,
            @ApiParam(required = true, name = "TeamId", value = "TeamId")
            @RequestParam("teamId") Long teamId) {

        // TODO: 2/8/21 confirmInvatationEmail method may return null. So the return user from this method should be checked!

        User user = teamService.confirmInvitationEmail(uuid, teamId);
        if(user!=null){
            return new ResponseEntity(user.getEmail()+" Developer added to team successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity("Db Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("delete/{teamId}")
    public void deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
    }

    @GetMapping("{teamId}")
    public ResponseEntity<TeamModel> getTeamById(@PathVariable("teamId") Long teamId) {
        return new ResponseEntity(teamService.findTeamById(teamId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Set<TeamModel>> getAllTeams() {
        return new ResponseEntity(teamService.findAllTeams(), HttpStatus.OK);
    }

    @PutMapping("edit/{teamId}")
    public ResponseEntity<TeamModel> editTeam(@PathVariable("teamId") Long teamId, @RequestBody TeamModel teamModel){
        return new ResponseEntity(teamService.editTeam(teamId, teamModel), HttpStatus.OK);
    }

}