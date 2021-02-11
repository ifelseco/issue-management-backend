package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.ConfirmUserToken;
import com.ifelseco.issueapp.entity.User;

public interface ConfirmUserService {
    ConfirmUserToken findByToken(String token);
    ConfirmUserToken createToken(User user);
   // ConfirmUserToken refreshToken(User user);
    void delete(ConfirmUserToken confirmUserToken);

}
