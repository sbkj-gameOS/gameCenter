package com.bradypod.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.TableProperties;

public abstract interface TablePropertiesRepository extends JpaRepository<TableProperties, String>{
	
	public abstract TableProperties findById(String id);

	public abstract List<TableProperties> findByDbtableid(String dbtableid) ;
}
