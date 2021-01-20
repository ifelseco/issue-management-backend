package com.ifelseco.issueapp.repository;

import com.ifelseco.issueapp.entity.ConfirmUserToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmUserRepository extends CrudRepository<ConfirmUserToken,Long> {
    ConfirmUserToken findByToken(String token);

}
