package com.bradypod.web.handler.mobileter.transact;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bradypod.web.handler.Handler;
import com.bradypod.web.model.PresentApp;
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

	/**
	 * @Title: findRegisterPlayerList
	 * @Description: TODO(提现记录)
	 * @param playUser
	 * @return 设定文件 JSONObject 返回类型
	 */
	@ResponseBody
	@RequestMapping("/findPresentappList")
	public JSONObject findPresentappList(PresentApp presentApp) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			dataMap.put("data", presentAppRepository.findAll());
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
