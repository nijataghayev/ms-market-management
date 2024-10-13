package com.example.marketmanagement.service;

import com.example.marketmanagement.dao.entity.RoleEntity;
import com.example.marketmanagement.dao.repository.RoleRepository;
import com.example.marketmanagement.mapper.RoleMapper;
import com.example.marketmanagement.model.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: nijataghayev
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public List<RoleDto> getAllRoles() {
        log.info("ActionLog.getAllRoles.start");
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        List<RoleDto> roleDtoList = roleEntityList
                .stream()
                .map(roleMapper::mapToDto)
                .toList();
        log.info("ActionLog.getAllRoles.end");
        return roleDtoList;
    }

    public RoleDto getRoleByUserId(Long userId) {
        log.info("ActionLog.getRoleByUserId.start userId {}", userId);
        List<RoleEntity> roleEntity = roleRepository.findByUserId(userId);
        RoleDto roleDto = roleMapper.mapToDto((RoleEntity) roleEntity);
        log.info("ActionLog.getRoleByUserId.start userId {}", userId);

        return roleDto;
    }

    public void changeUserRoleToAdmin(Long userId) {
        log.info("ActionLog.changeUserRoleToAdmin.start userId {}", userId);
        List<RoleEntity> roleEntityList = roleRepository.findByUserId(userId);

        for (RoleEntity role : roleEntityList) {
            if ("ROLE_USER".equals(role.getName())) {
                role.setName("ROLE_ADMIN");
                roleRepository.save(role);
                log.info("ActionLog.changeUserRoleToAdmin.end userId {} with new role {}", userId, role.getName());
                return;
            }
        }
        log.warn("ActionLog.changeUserRoleToAdmin.end userId {} - no changes made", userId);
    }
}
