package com.bradypod.web.service.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bradypod.web.model.RunHistory;


public interface RunHistoryRepository extends JpaRepository<RunHistory, String>,JpaSpecificationExecutor<RunHistory>  {

}
