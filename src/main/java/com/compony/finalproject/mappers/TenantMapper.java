package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.TenantDto;
import com.compony.finalproject.model.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {

    public TenantDto toDto(Tenant tenant) {
        TenantDto tenantDto = new TenantDto();
        tenantDto.setId(tenant.getId());
        tenantDto.setUserName(tenant.getUserName());
        tenantDto.setPassword(tenant.getPassword());
        tenantDto.setFullName(tenant.getFullName());
        tenantDto.setPhoneNumber(tenant.getPhoneNumber());
        tenantDto.setEmail(tenant.getEmail());
        tenantDto.setRating(tenant.getRating());
        return tenantDto;
    }

    public Tenant toEntity(TenantDto tenantDto) {
        Tenant tenant = new Tenant();
        tenant.setId(tenantDto.getId());
        tenant.setUserName(tenantDto.getUserName());
        tenant.setPassword(tenantDto.getPassword());
        tenant.setFullName(tenantDto.getFullName());
        tenant.setPhoneNumber(tenantDto.getPhoneNumber());
        tenant.setEmail(tenantDto.getEmail());
        tenant.setRating(tenantDto.getRating());
        return tenant;
    }
}
