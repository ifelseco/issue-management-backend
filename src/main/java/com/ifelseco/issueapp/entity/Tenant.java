package com.ifelseco.issueapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Tenant implements Serializable {


    private static final long serialVersionUID = -6562195283785095675L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, updatable = false)
    private Long id;


    private String tenantName;
    private String phone;
    private String email;
    private String address;
    private String tenantCode;
    private boolean enabled=true;

}
