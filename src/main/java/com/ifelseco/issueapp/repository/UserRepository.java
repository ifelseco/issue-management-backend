package com.ifelseco.issueapp.repository;

import com.ifelseco.issueapp.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);

}
