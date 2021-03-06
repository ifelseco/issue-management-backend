package com.ifelseco.issueapp.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
public class Project implements Serializable {

    private static final long serialVersionUID=1L;

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
