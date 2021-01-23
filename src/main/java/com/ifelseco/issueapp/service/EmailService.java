package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.model.EmailModel;

public interface EmailService {

    void sendEmail(EmailModel emailModel);
    void sendInvitationEmail(EmailModel emailModel);

}
