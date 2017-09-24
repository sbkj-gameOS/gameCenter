package com.bradypod.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.Secret;

public abstract interface SecretRepository  extends JpaRepository<Secret, String>{
	public abstract List<Secret> findByOrgi(String orgi);
}

