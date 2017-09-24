package com.bradypod.web.handler.apps;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bradypod.util.Menu;
import com.bradypod.web.handler.Handler;
import com.bradypod.web.service.repository.jpa.UserRepository;

@Controller
public class AppsController extends Handler{
	
	@Autowired
	private UserRepository userRes;
	
	@RequestMapping({"/apps/content"})
	@Menu(type="apps", subtype="content")
	public ModelAndView content(ModelMap map , HttpServletRequest request){
		return request(super.createAppsTempletResponse("/apps/desktop/index"));
	}
}
