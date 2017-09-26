package com.bradypod.web.handler.mobileter.transact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bradypod.web.service.repository.jpa.PresentAppRepository;
import com.bradypod.web.service.repository.jpa.ProManagementRepository;

/**
 * @ClassName: ProManagementController
 * @Description: TODO(分润管理)
 * @author dave
 * @date 2017年9月26日 上午9:38:18
 */
@Controller
@RequestMapping("promanagement")
public class ProManagementController {

	@Autowired
	private ProManagementRepository proManagementRepository;
	
	@Autowired
	private PresentAppRepository presentAppRepository;

}
