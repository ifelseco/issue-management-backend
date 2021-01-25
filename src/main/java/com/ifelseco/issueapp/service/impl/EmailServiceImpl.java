package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.config.AppConstants;
import com.ifelseco.issueapp.config.EmailConstants;
import com.ifelseco.issueapp.entity.ConfirmUserToken;
import com.ifelseco.issueapp.entity.Team;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.EmailModel;
import com.ifelseco.issueapp.service.ConfirmUserService;
import com.ifelseco.issueapp.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);
    private JavaMailSender emailSender;
    private SpringTemplateEngine templateEngine;
    private ConfirmUserService confirmUserService;

    public EmailServiceImpl(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendConfirmationToLead(User user) {
        sendEmail(user,null);
    }

    @Override
    public void sendConfirmationToDeveloper(User user, Team team) {
        sendEmail(user,team);
    }

    private void sendEmail(User user,Team team) {
        try {
            ConfirmUserToken confirmUserToken=confirmUserService.createToken(user);
            EmailModel emailModel = initModel(user, confirmUserToken,team);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());


            Context context = new Context();
            context.setVariables(emailModel.getModel());
            String html = templateEngine.process(emailModel.getTemplateName(), context);
            helper.setTo(emailModel.getTo());
            helper.setText(html, true);
            helper.setSubject(emailModel.getSubject());
            helper.setFrom(emailModel.getFrom());

            emailSender.send(message);
        } catch (Exception e){
            LOG.error("Email send error: {} "+e.getClass()+e.getMessage());
            throw new RuntimeException(e);

        }

    }

    private EmailModel initModel(User user, ConfirmUserToken confirmUserToken,Team team) {
        Map<String, Object> model = new HashMap<>();
        model.put("firstName",user.getFirstname());
        model.put("signature","Issue Management");
        model.put("confirmUrl", EmailConstants.CONFIRM_USER_EMAIL_URL+confirmUserToken.getToken()+team!=null?"&teamId="+team.getId() : "" );

        return new EmailModel(EmailConstants.FROM_EMAIL,user.getEmail(),
                "Issue Management: E-posta doğrulama", EmailConstants.CONFIRM_USER_TEMPLATE,model);
    }



}
