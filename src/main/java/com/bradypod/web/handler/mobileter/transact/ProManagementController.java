package com.bradypod.web.handler.mobileter.transact;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.bradypod.web.handler.Handler;
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
@RequestMapping("promanagement")
public class ProManagementController extends Handler {

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
	public JSONObject findProManagementList(ProManagement proManagement, Integer page, Integer limit) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			Pageable pageable = new PageRequest(page, limit);
			// Page<ProManagement> p = proManagementRepository.findByUserNameLikeAndInvitationCode(proManagement.getUserName(), proManagement.getInvitationCode(), Pageable);
			Page<ProManagement> p = proManagementRepository.findAll(pageable);
			dataMap.put("data", p.getContent());
			dataMap.put("count", p.getTotalElements());
			dataMap.put("code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "查询失败");
		}
		return (JSONObject) JSONObject.toJSON(dataMap);
	}

	/**
	 * 分润管理首页
	 * 
	 * @return
	 */
	@RequestMapping({ "/index" })
	public ModelAndView rechargeRecord(ModelMap map, HttpServletRequest request) {
		return request(super.createAppsTempletResponse("/apps/business/platform/room/management/index"));
	}

}
