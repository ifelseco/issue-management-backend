package com.ifelseco.issueapp.service;

import com.ifelseco.issueapp.entity.ConfirmUserToken;

public interface ConfirmUserService {
    ConfirmUserToken findByToken(String token);
    ConfirmUserToken save(ConfirmUserToken confirmUserToken);
    void delete(ConfirmUserToken confirmUserToken);

}
