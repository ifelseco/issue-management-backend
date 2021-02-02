package com.ifelseco.issueapp.config;

public class EmailConstants {
    public static final String FROM_EMAIL="test@mail.com";
    public static final String CONFIRM_USER_TEMPLATE="confirm-email";
    public static final String CONFIRM_DEVELOPER_INVITATION_TEMPLATE="confirm-invitation-email";
    public static final String CONFIRM_USER_EMAIL_URL=AppConstants.WEB_URL+"/user/confirm-email?uuid=";
    public static final String CONFIRM_DEVELOPER_EMAIL_URL=AppConstants.WEB_URL+"/team/invitation-confirm-email?uuid=";

}
