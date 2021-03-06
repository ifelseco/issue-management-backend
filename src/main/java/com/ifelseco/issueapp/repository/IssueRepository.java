package com.ifelseco.issueapp.repository;

import com.ifelseco.issueapp.entity.Issue;
import com.ifelseco.issueapp.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<Issue,Long> {

    List<Issue> findByProject(Project project);

}
