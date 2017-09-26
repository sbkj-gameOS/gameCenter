package com.bradypod.web.handler.mobileter.transact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bradypod.web.handler.Handler;
import com.bradypod.web.service.repository.jpa.PresentAppRepository;
import com.bradypod.web.service.repository.jpa.ProManagementRepository;

/**
 * @ClassName: PresentAppController
 * @Description: TODO(提现记录表)
 * @author dave
 * @date 2017年9月26日 上午9:38:03
 */
@Controller
@RequestMapping("/presentapp")
public class PresentAppController extends Handler {

	@Autowired
	private ProManagementRepository proManagementRepository;
	
	@Autowired
	private PresentAppRepository presentAppRepository;

}
