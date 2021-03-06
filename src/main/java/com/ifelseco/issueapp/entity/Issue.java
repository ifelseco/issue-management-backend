package com.ifelseco.issueapp.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Issue implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Date createdDate;

    @ManyToOne
    private Project project;

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
    }

}
