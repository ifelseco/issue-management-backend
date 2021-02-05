package com.ifelseco.issueapp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role")
@Data
@NoArgsConstructor
public class UserRole implements Serializable {

    private static final long serialVersionUID=1L;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude private Role role;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userRoleId;


}
