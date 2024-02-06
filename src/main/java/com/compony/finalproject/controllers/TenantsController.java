package com.compony.finalproject.controllers;

import com.compony.finalproject.dto.TenantDto;
import com.compony.finalproject.service.impl.TenantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenants")
public class TenantsController {

    private final TenantServiceImpl tenantService;

    @Autowired
    public TenantsController(TenantServiceImpl tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDto> getById(@PathVariable Integer id) {
        try {
            TenantDto tenantDto = tenantService.getById(id);
            return ResponseEntity.ok(tenantDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TenantDto>> getAll() {
        try {
            List<TenantDto> tenantDto = tenantService.getAll();
            return ResponseEntity.ok(tenantDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<TenantDto> create(@RequestBody TenantDto tenantDto) {
        try {
            tenantService.create(tenantDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(tenantDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDto> update(@PathVariable Integer id, @RequestBody TenantDto tenantDto) {
        try {
            tenantService.update(id, tenantDto);
            return ResponseEntity.status(HttpStatus.OK).body(tenantDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TenantDto> deleteById(@PathVariable Integer id) {
        try {
            tenantService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
