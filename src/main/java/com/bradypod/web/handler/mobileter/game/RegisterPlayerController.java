package com.bradypod.web.handler.mobileter.game;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@ResponseBody
	@RequestMapping("/wxLogin")
	public String wxLogin(String code, HttpSession session) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		/** 请求结果 */
		String result = "{\"subscribe\": 1,\"openid\":\"o6_bmjrPTlm6_2sgVt7hMZOPfL2M\",\"nickname\": \"Band\",\"sex\": 1,\"language\": \"zh_CN\",\"city\":\"广州\",\"province\": \"广东\",\"country\":\"中国\",\"headimgurl\":\"\",\"subscribe_time\": 1382694957,\"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\",\"remark\": \"\",\"groupid\":0,  \"tagid_list\":[128,2]}";
		// String result = WxUserInfo.getWxUserInfo(code);//根据code获取微信用户信息
		JSONObject jsonObject = (JSONObject) JSON.parse(result);
		Gson gson = new Gson();
		try {
			if (null != jsonObject.get("openid")) {
				PlayUser playUser = gson.fromJson(result, PlayUser.class);
				PlayUser newPlayUser = playUserRes.findByOpenid(playUser.getOpenid());
				if (null == newPlayUser) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHHmmssSSS");
					String time = formatter.format(new Date());
					playUser.setInvitationcode(time);
					playUser.setRoomcordnum(10);
					RoomRechargeRecord roomRechargeRecord = new RoomRechargeRecord();
					roomRechargeRecord.setUserName(playUser.getNickname());
					roomRechargeRecord.setInvitationCode(playUser.getInvitationcode());
					roomRechargeRecord.setRoomCount(10);
					roomRechargeRecord.setOriginalPrice(BigDecimal.valueOf(30.00));
					roomRechargeRecord.setPreferentialAmount(BigDecimal.valueOf(0.00));
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
		return gson.toJson(dataMap);
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
}
