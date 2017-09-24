package com.bradypod.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.AccountConfig;

public abstract interface AccountConfigRepository extends JpaRepository<AccountConfig, String>
{
  public abstract List<AccountConfig> findByOrgi(String orgi);
}
