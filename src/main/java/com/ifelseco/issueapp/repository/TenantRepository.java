package com.ifelseco.issueapp.repository;

import com.ifelseco.issueapp.entity.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TenantRepository extends CrudRepository<Tenant, Long> {

	List<Tenant> findAll();
	Tenant findByTenantName(String tenantName);
	Tenant findByEmail(String email);

}
