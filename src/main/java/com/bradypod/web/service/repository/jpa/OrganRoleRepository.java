package com.bradypod.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.Organ;
import com.bradypod.web.model.OrganRole;
import com.bradypod.web.model.Role;

public abstract interface OrganRoleRepository  extends JpaRepository<OrganRole, String>
{
	
	public abstract Page<OrganRole> findByOrgiAndRole(String orgi ,Role role,Pageable paramPageable);
	
	public abstract List<OrganRole> findByOrgiAndRole(String orgi ,Role role);
	
	public abstract List<OrganRole> findByOrgiAndOrgan(String orgi ,Organ organ);
}

