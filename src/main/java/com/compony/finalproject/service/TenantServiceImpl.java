package com.compony.finalproject.service;

import com.compony.finalproject.dto.TenantDto;
import com.compony.finalproject.mappers.TenantMapper;
import com.compony.finalproject.model.Landlord;
import com.compony.finalproject.model.Tenant;
import com.compony.finalproject.repository.TenantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TenantServiceImpl implements CrudService<TenantDto>{

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }


    @Override
    public TenantDto getById(Integer id) {
        return tenantMapper.toDto(tenantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Landlord not found with id: " + id)));
    }


    @Override
    public List<TenantDto> getAll() {
        return tenantRepository.findAll().stream()
                .map(tenantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TenantDto create(TenantDto item) {
        Tenant tenant = tenantMapper.toEntity(item);
        return tenantMapper.toDto(tenantRepository.save(tenant));
    }

    @Override
    public TenantDto update(Integer id, TenantDto item) {
        if (!tenantRepository.existsById(id)) {
            throw new EntityNotFoundException("Landlord not found with id: " + id);
        }
        Tenant updatedTenant = tenantMapper.toEntity(item);
        updatedTenant.setId(id);
        return tenantMapper.toDto(tenantRepository.save(updatedTenant));
    }

    @Override
    public void delete(Integer id) {
        if (!tenantRepository.existsById(id)) {
            throw new EntityNotFoundException("Landlord not found with id: " + id);
        }
        tenantRepository.deleteById(id);
    }
}
