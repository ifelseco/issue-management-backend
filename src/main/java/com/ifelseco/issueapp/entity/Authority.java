package com.ifelseco.issueapp.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
public class Authority implements GrantedAuthority, Serializable {

    private static final long serialVersionUID=1L;

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }
}
