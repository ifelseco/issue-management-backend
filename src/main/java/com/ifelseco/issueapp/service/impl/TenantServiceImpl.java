package com.ifelseco.issueapp.service.impl;

import com.ifelseco.issueapp.entity.Tenant;
import com.ifelseco.issueapp.repository.TenantRepository;
import com.ifelseco.issueapp.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TenantServiceImpl implements TenantService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Tenant createTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Override
    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    @Override
    public Tenant findByTenantName(String tenantName) {
        return tenantRepository.findByTenantName(tenantName);
    }

    @Override
    public Tenant findByEmail(String email) {
        return tenantRepository.findByEmail(email);
    }

    @Override
    public Tenant findOne(Long id) {
        return tenantRepository.findById(id).orElse(null);
    }

}
