package com.bradypod.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.Token;

public abstract interface TokenRepository extends JpaRepository<Token, String>
{
	public abstract Token findById(String id);
	
	public abstract List<Token> findByUserid(String userid);
}
