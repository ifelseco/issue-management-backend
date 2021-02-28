package com.ifelseco.issueapp.service;


import com.ifelseco.issueapp.entity.Tenant;
import com.ifelseco.issueapp.entity.User;

import java.util.List;

public interface TenantService {
	Tenant createTenant(Tenant tenant);
	List<Tenant> findAll();
	Tenant findByTenantName(String tenantName);
	Tenant findByEmail(String email);
	Tenant findOne(Long id);
	Tenant findById(Long id);

}
