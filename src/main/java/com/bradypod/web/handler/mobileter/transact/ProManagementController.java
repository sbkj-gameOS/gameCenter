package com.bradypod.web.handler.mobileter.transact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bradypod.web.service.repository.jpa.ProManagementRepository;

@Controller
@RequestMapping("promanagement")
public class ProManagementController {

	@Autowired
	private ProManagementRepository proManagementRepository;

}
