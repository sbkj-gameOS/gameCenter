package com.bradypod.web.handler.mobileter.transact;

import com.alibaba.fastjson.JSONObject;
import com.bradypod.web.model.PresentApp;
import com.bradypod.web.model.ProManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bradypod.web.handler.Handler;
import com.bradypod.web.service.repository.jpa.PresentAppRepository;
import com.bradypod.web.service.repository.jpa.ProManagementRepository;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

	/**
	 * 提现审批
	 * 
	 * @return
	 */
	@RequestMapping({ "/index" })
	public ModelAndView index(ModelMap map, HttpServletRequest request) {
		return request(super.createAppsTempletResponse("/apps/business/platform/room/approvel/index"));
	}

	/**
	 * @Title: findRegisterPlayerList
	 * @Description: TODO(提现记录)
	 * @param playUser
	 * @return 设定文件 JSONObject 返回类型
	 */
	@ResponseBody
	@RequestMapping("/findPresentappList")
	public JSONObject findPresentappList(PresentApp presentApp, Integer page, Integer limit) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			Pageable pageable = new PageRequest(page, limit);
			Page<PresentApp> p = presentAppRepository.findAll(pageable);
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
}
