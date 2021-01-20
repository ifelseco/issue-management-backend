package com.ifelseco.issueapp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Role implements Serializable {

    private static final long serialVersionUID=1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;

    private String name;


    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude private Set<UserRole> userRoles=new HashSet<>();



}
