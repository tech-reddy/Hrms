package com.reddy.controller;

import com.reddy.dto.employee.RoleRequestDTO;
import com.reddy.dto.employee.RoleResponseDTO;
import com.reddy.service.employee.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponseDTO createRole(@Valid @RequestBody RoleRequestDTO dto) {
        return roleService.createRole(dto);
    }

    @PutMapping("/{id}")
    public RoleResponseDTO updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequestDTO dto) {
        return roleService.updateRole(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

    @GetMapping
    public ResponseEntity<Page<RoleResponseDTO>> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }

    @GetMapping("/{id}")
    public RoleResponseDTO getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping("/search")
    public Page<RoleResponseDTO> searchRoles(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return roleService.searchRoles(query, PageRequest.of(page, size));
    }

    private List<Sort.Order> parseSort(String sort) {
        String[] parts = sort.split(",");
        String property = parts[0].trim();
        Sort.Direction direction = Sort.Direction.ASC; // default
        if (parts.length > 1) {
            String dir = parts[1].trim().toLowerCase();
            if ("desc".equals(dir)) {
                direction = Sort.Direction.DESC;
            }
        }
        return Collections.singletonList(new Sort.Order(direction, property));
    }
}
