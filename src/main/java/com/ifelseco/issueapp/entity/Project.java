package com.ifelseco.issueapp.entity;

import com.ifelseco.issueapp.model.TeamModel;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToMany
    //TODO: join columns
    private Set<User> members;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    private Team team;

}
