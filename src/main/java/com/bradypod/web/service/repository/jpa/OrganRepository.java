package com.bradypod.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.Organ;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public abstract interface OrganRepository
  extends JpaRepository<Organ, String>
{
  public abstract Organ findByIdAndOrgi(String paramString, String orgi);
  
  public abstract Page<Organ> findByOrgi(String orgi , Pageable paramPageable);
  
  public abstract Organ findByNameAndOrgi(String paramString, String orgi);
  
  public abstract List<Organ> findByOrgi(String orgi);
  
  public abstract List<Organ> findByOrgiAndSkill(String orgi , boolean skill);

  @Query(value = "update bm_organ set name = :name,updatetime = now() where id = :id",nativeQuery = true)
  @Modifying
  @Transactional
  public abstract int setOrgiById(@Param("name") String name , @Param("id") String id) ;
}
