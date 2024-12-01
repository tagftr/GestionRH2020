package com.gpch.login.service;

import com.gpch.login.model.Role;
import com.gpch.login.model.User;
import com.gpch.login.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("roleService")
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(
                       RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role findRoleByName(String role) {
        return roleRepository.findByRole(role);
    }
}
