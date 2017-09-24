package com.bradypod.web.service.repository.es;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.bradypod.web.model.Token;

public abstract interface TokenESRepository extends ElasticsearchCrudRepository<Token, String>
{
	public abstract Token findById(String id);
	
	public abstract List<Token> findByUserid(String userid);
}
