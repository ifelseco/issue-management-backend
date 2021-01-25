package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.EmailModel;

public interface EmailService {

    void sendConfirmationToLead(User user);
    void sendConfirmationToDeveloper(User user, Team team);
}
