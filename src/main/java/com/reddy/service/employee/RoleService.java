package com.reddy.service.employee;

import com.reddy.dto.employee.RoleRequestDTO;
import com.reddy.dto.employee.RoleResponseDTO;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.employee.Role;
import com.reddy.repository.employee.EmployeeRepository;
import com.reddy.repository.employee.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepo;
    private final EmployeeRepository employeeRepo;
    private final ModelMapper modelMapper;

    public RoleResponseDTO createRole(RoleRequestDTO dto) {
        if(roleRepo.existsByName(dto.getName())) {
            throw new DataIntegrityViolationException("Role name already exists");
        }

        Role role = modelMapper.map(dto, Role.class);
        return mapToDTO(roleRepo.save(role));
    }

    public RoleResponseDTO updateRole(Long id, RoleRequestDTO dto) {
        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if(!role.getName().equals(dto.getName()) &&
                roleRepo.existsByName(dto.getName())) {
            throw new DataIntegrityViolationException("New role name already exists");
        }

        modelMapper.map(dto, role);
        return mapToDTO(roleRepo.save(role));
    }

    public void deleteRole(Long id) {
        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if(employeeRepo.existsByRoleId(id)) {
            throw new DataIntegrityViolationException(
                    "Cannot delete role assigned to employees"
            );
        }

        roleRepo.delete(role);
    }

    public Page<RoleResponseDTO> getAllRoles(Pageable pageable) {
        return roleRepo.findAll(pageable)
                .map(this::mapToDTO);
    }

    public RoleResponseDTO getRoleById(Long id) {
        return roleRepo.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

    public Page<RoleResponseDTO> searchRoles(String query, Pageable pageable) {
        return roleRepo.searchRoles(query, pageable)
                .map(this::mapToDTO);
    }

    private RoleResponseDTO mapToDTO(Role role) {
        RoleResponseDTO dto = modelMapper.map(role, RoleResponseDTO.class);
        dto.setEmployeeCount(role.getEmployees().size());
        return dto;
    }
}
