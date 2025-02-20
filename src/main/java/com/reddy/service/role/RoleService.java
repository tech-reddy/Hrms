package com.reddy.service.role;

import com.reddy.model.employee.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    Role createRole(Role role);
    Role updateRole(Role role);
    void deleteRole(Long id);
}
