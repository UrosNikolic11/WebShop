package com.consulteer.webShop.services.impl;

import com.consulteer.webShop.dto.CreateRoleDto;
import com.consulteer.webShop.dto.RoleDto;
import com.consulteer.webShop.exception.BadRequestException;
import com.consulteer.webShop.exception.NotFoundException;
import com.consulteer.webShop.mappers.RoleMapper;
import com.consulteer.webShop.model.Role;
import com.consulteer.webShop.repositories.RoleRepository;
import com.consulteer.webShop.services.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDto create(CreateRoleDto createRoleDto) {
        Role role = roleMapper.map(createRoleDto);
        roleRepository.save(role);
        return roleMapper.map(role);
    }

    @Override
    public Page<RoleDto> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(roleMapper::map);
    }

    @Override
    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(NotFoundException::new);
        return roleMapper.map(role);
    }

    @Override
    public RoleDto update(Long id, CreateRoleDto createRoleDto) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new BadRequestException("Role with given id does not exist!"));

        role.setRole(createRoleDto.role());

        roleRepository.save(role);

        return roleMapper.map(role);
    }

    @Override
    public void remove(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new BadRequestException("Role with given id does not exist!"));
        if (!role.getUsers().isEmpty()) throw new BadRequestException("U can not delete this role because some users have this role");
        roleRepository.delete(role);
    }
}
