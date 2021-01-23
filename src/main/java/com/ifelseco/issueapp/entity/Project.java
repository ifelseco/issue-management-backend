package com.ifelseco.issueapp.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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

    @ManyToMany
    //TODO: join columns
    private Set<User> members;

}
