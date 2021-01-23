package com.ifelseco.issueapp.repository;

import com.ifelseco.issueapp.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team,Long> {

}
