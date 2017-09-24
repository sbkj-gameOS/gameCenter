package com.bradypod.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.GameConfig;

public abstract interface GameConfigRepository extends JpaRepository<GameConfig, String>
{
  public abstract List<GameConfig> findByOrgi(String orgi);
}
