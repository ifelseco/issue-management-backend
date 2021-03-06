package com.ifelseco.issueapp.controller;


import com.ifelseco.issueapp.model.IssueListModel;
import com.ifelseco.issueapp.service.IssueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/issue")
@AllArgsConstructor
public class IssueController {

    private IssueService issueService;


    @GetMapping("/{projectId}")
    public ResponseEntity<?> getIssueList(@PathVariable("projectId") String projectId) {
        IssueListModel issueListModel=issueService.getIssueList(Long.valueOf(projectId));
        return new ResponseEntity<>(issueListModel, HttpStatus.OK);
    }

}
