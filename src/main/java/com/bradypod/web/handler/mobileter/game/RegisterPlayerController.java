package com.bradypod.web.handler.mobileter.game;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.bradypod.util.wx.WxUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bradypod.web.handler.Handler;
import com.bradypod.web.model.PlayUser;
import com.bradypod.web.model.RoomRechargeRecord;
import com.bradypod.web.service.repository.jpa.PlayUserRepository;
import com.bradypod.web.service.repository.jpa.RoomRechargeRecordRepository;
import com.google.gson.Gson;

/**
 * @ClassName: RegisterPlayerController
 * @Description: TODO(注册玩家控制层)
 * @author dave
 * @date 2017年9月25日 下午4:03:15
 */
@Controller
@RequestMapping("/registerPlayer")
public class RegisterPlayerController extends Handler {

	@Autowired
	private PlayUserRepository playUserRes;

	@Autowired
	private RoomRechargeRecordRepository roomRechargeRecordRepository;

	@RequestMapping("/wxLogin")
	public String wxLogin(ModelMap map, String code, HttpSession session) {
		System.out.println("-------------------code-------------------:" + code);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		/** 请求结果 */
		String result = WxUserInfo.getWxUserInfo(code);// 根据code获取微信用户信息
		JSONObject jsonObject = (JSONObject) JSON.parse(result);
		Gson gson = new Gson();
		try {
			if (null != jsonObject.get("openid")) {
				PlayUser playUser = gson.fromJson(result, PlayUser.class);
				map.addAttribute("userName",playUser.getNickname());//回显用户名
				map.addAttribute("userId",playUser.getNickname());//回显用户名
				PlayUser newPlayUser = playUserRes.findByOpenid(playUser.getOpenid());
				if (null == newPlayUser) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHHmmssSSS");
					String time = formatter.format(new Date());
					playUser.setInvitationcode(time);
					playUser.setCards(10);;
					RoomRechargeRecord roomRechargeRecord = new RoomRechargeRecord();
					roomRechargeRecord.setUserName(playUser.getNickname());
					roomRechargeRecord.setInvitationCode(playUser.getInvitationcode());
					roomRechargeRecord.setRoomCount(10);
					roomRechargeRecord.setPayAmount(BigDecimal.valueOf(30.00));
					roomRechargeRecord.setDirectlyTheLastAmount(BigDecimal.valueOf(0.00));
					roomRechargeRecord.setIndirectTheLastAmount(BigDecimal.valueOf(0.00));
					playUser.setTrtProfit(BigDecimal.valueOf(0.00));
					playUserRes.saveAndFlush(playUser);
					roomRechargeRecordRepository.saveAndFlush(roomRechargeRecord);
					session.setAttribute("mgPlayUser", playUser);
					dataMap.put("mgPlayUser", playUser);
				} else {
					session.setAttribute("mgPlayUser", newPlayUser);
					dataMap.put("mgPlayUser", newPlayUser);
				}
				dataMap.put("success", true);
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", "授权失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "授权失败");
		}
		return "/apps/business/platform/game/wxGetCode/main";
	}

	/**
	 * @Title: findRegisterPlayerList
	 * @Description: TODO(查询玩家信息)
	 * @param playUser
	 * @return 设定文件 JSONObject 返回类型
	 */
	@ResponseBody
	@RequestMapping("/findRegisterPlayerList")
	public JSONObject findRegisterPlayerList(PlayUser playUser, Integer page, Integer limit) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			Pageable Pageable = new PageRequest(page, limit, Sort.Direction.DESC, "cards");
			Page<PlayUser> p = playUserRes.findAll(Pageable);
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
	 * 方法描述: 微信绑定平台的用户手机号<br>
	 * 作者：田帅 <br>
	 * 创建时间：2017-09-16 <br>
	 * 版本：V1.0
	 */
	@RequestMapping("/wxBound")
	@ResponseBody
	public Object wxBound(String mobile, String openid, String nickname, String headimgurl) {
		Map<String, Object> json = new HashMap<String, Object>();
		System.out.println("mobile=" + mobile);
		System.out.println("openid=" + openid);
		System.out.println("nickname=" + nickname);
		System.out.println("headimgurl=" + headimgurl);
		if (mobile == null || mobile.isEmpty() || openid == null || openid.isEmpty()) {
			json.put("status", "203");
			json.put("message", "参数不完整");
			return json;
		}
		// 赋值书手机号
		/* 根据手机号查询账号是否存在 */
		// 该手机号还没有注册
		// 该手机号已注册
		// 先查询该手机号是否已绑定微信openid
		return json;
	}

	/**
	 * @Title: buildPinvitationcode
	 * @Description: TODO(添加邀请码)
	 * @param playUser
	 * @return 设定文件 JSONObject 返回类型
	 */
	@ResponseBody
	@RequestMapping("/buildPinvitationcode")
	public JSONObject buildPinvitationcode(PlayUser playUser) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			playUserRes.setPinvitationcodeById(playUser.getPinvitationcode(),playUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "添加邀请码失败");
		}
		return (JSONObject) JSONObject.toJSON(dataMap);
	}
}
