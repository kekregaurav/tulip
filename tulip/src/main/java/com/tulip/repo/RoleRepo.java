package com.tulip.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulip.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
   
	public Role findRoleByName(String name);
	
}
