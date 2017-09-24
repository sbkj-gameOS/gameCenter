package com.bradypod.web.service.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.SystemConfig;

public abstract interface SystemConfigRepository  extends JpaRepository<SystemConfig, String>
{
	public abstract SystemConfig findByOrgi(String orgi);
}

