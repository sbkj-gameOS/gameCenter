package com.bradypod.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.GameModel;

public abstract interface GameModelRepository  extends JpaRepository<GameModel, String>{
	
  public abstract GameModel findByIdAndOrgi(String id, String orgi);
  
  public abstract List<GameModel> findByOrgiAndGame(String orgi , String game);
}
