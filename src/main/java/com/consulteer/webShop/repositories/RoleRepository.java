package com.consulteer.webShop.repositories;

import com.consulteer.webShop.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long>, CrudRepository<Role, Long> {
}
