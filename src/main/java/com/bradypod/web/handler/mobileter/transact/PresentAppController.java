package com.bradypod.web.handler.mobileter.transact;

import java.util.Date;
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
import com.bradypod.web.model.PresentApp;
import com.bradypod.web.service.repository.jpa.PresentAppRepository;
import com.bradypod.web.service.repository.spec.DefaultSpecification;

/**
 * @ClassName: PresentAppController
 * @Description: TODO(提现记录表)
 * @author dave
 * @date 2017年9月26日 上午9:38:03
 */
@Controller
@RequestMapping("/presentapp")
public class PresentAppController extends Handler {

/*	@Autowired
	private ProManagementRepository proManagementRepository;*/

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
	 * @Title: findPresentappList
	 * @Description: TODO(提现记录)
	 * @param presentApp
	 * @param page 页码
	 * @param limit 页尾
	 * @param firstParms 开始金额
	 * @param endParms 结束金额
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 设定文件 JSONObject 返回类型
	 */
	@ResponseBody
	@RequestMapping("/findPresentappList")
	public JSONObject findPresentappList(PresentApp presentApp, Integer page, Integer limit, Double firstParms, Double endParms, Date startDate, Date endDate) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			Pageable pageable = new PageRequest(page, limit);
			DefaultSpecification<PresentApp> spec = new DefaultSpecification<PresentApp>();
			if (null != presentApp.getUserName()) spec.setParams("userName", "like", "%" + presentApp.getUserName() + "%");

			if (null != firstParms) spec.setParams("amountMoney", ">=", firstParms);

			if (null != endParms) spec.setParams("amountMoney", "<=", endParms);

			if (null != startDate) spec.setParams("presentAppTime", ">=", startDate);

			if (null != endDate) spec.setParams("presentAppTime", "<=", endDate);

			if (3 != presentApp.getPreState()) {
				spec.setParams("preState", "eq", presentApp.getPreState());
			} else if (3 == presentApp.getPreState()) {

			}
			Page<PresentApp> p = presentAppRepository.findAll(spec, pageable);
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
