package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nichoshop.main.model.Role;
import com.nichoshop.main.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    RoleRepository repository;

    public Role getRoleByUserId(Long userId) {
        return repository.findByUserId(userId);
    };

    public Role getRoleById(Long id) {
        return repository.findById(id).get();
    }

    public List<Role> getAllRoles() {
        List<Role> Roles = new ArrayList<Role>();
        repository.findAll().forEach(Role -> Roles.add(Role));
        return Roles;
    }

    public void saveOrUpdate(Role Role) {
        repository.save(Role);
    }

    public void deleteRoleById(Long id) {
        repository.deleteById(id);
    }
}
