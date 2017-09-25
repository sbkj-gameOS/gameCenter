package com.bradypod.web.handler.mobileter.transact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bradypod.web.handler.Handler;
import com.bradypod.web.service.repository.jpa.ProManagementRepository;

@Controller
@RequestMapping("/presentapp")
public class PresentAppController extends Handler{

	@Autowired
	private ProManagementRepository proManagementRepository;
	
}
