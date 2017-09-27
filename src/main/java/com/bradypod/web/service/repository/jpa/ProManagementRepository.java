package com.bradypod.web.service.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.ProManagement;

/**
 * @ClassName: ProManagementRepository
 * @Description: TODO(分润管理)
 * @author dave
 * @date 2017年9月25日 下午8:03:31
 */
public abstract interface ProManagementRepository extends JpaRepository<ProManagement, String> {

}
