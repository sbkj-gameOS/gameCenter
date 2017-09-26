package com.bradypod.web.handler.mobileter.transact;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bradypod.web.model.ProManagement;
import com.bradypod.web.service.repository.jpa.PresentAppRepository;
import com.bradypod.web.service.repository.jpa.ProManagementRepository;

/**
 * @ClassName: ProManagementController
 * @Description: TODO(分润管理)
 * @author dave
 * @date 2017年9月26日 上午9:38:18
 */
@Controller
@RequestMapping("/promanagement")
public class ProManagementController {

	@Autowired
	private ProManagementRepository proManagementRepository;

	@Autowired
	private PresentAppRepository presentAppRepository;

	/**
	 * @Title: findProManagementList
	 * @Description: TODO(分润记录)
	 * @param proManagement
	 * @return 设定文件 JSONObject 返回类型
	 */
	@ResponseBody
	@RequestMapping("/findProManagementList")
	public JSONObject findProManagementList(ProManagement proManagement) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			//dataMap.put("data", proManagementRepository.findByUserNameAndInvitationCode(proManagement.getUserName(),proManagement.getInvitationCode(),0));
			dataMap.put("data", proManagementRepository.findAll());
			dataMap.put("count", 0);
			dataMap.put("code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "查询失败");
		}
		return (JSONObject) JSONObject.toJSON(dataMap);
	}
}
