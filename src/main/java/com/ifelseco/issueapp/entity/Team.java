package com.ifelseco.issueapp.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Team implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToMany
    private Set<User> members;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToMany
    //@JoinTable(name = "team_project", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects;


    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

//    @CreatedDate
//    @Column(columnDefinition = "TIMESTAMP")
    private Date createTime;

    @PrePersist
    protected void onCreate() {
        this.createTime = new Date();
    }

    //it is id of leader
    private Long createdBy;

}
