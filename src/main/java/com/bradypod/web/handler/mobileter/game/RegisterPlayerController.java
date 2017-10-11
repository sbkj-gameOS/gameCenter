package com.bradypod.web.handler.mobileter.game;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bradypod.util.wx.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bradypod.core.BMDataContext;
import com.bradypod.util.BeanUtils;
import com.bradypod.util.CacheConfigTools;
import com.bradypod.util.IP;
import com.bradypod.util.IPTools;
import com.bradypod.util.UKTools;
import com.bradypod.util.cache.CacheHelper;
import com.bradypod.util.wx.WxUserInfo;
import com.bradypod.web.handler.Handler;
import com.bradypod.web.model.AccountConfig;
import com.bradypod.web.model.PlayUser;
import com.bradypod.web.model.PlayUserClient;
import com.bradypod.web.model.RoomRechargeRecord;
import com.bradypod.web.model.Token;
import com.bradypod.web.model.mobileter.murecharge.vo.PlayUserVo;
import com.bradypod.web.service.repository.es.PlayUserClientESRepository;
import com.bradypod.web.service.repository.es.TokenESRepository;
import com.bradypod.web.service.repository.jpa.PlayUserRepository;
import com.bradypod.web.service.repository.jpa.RoomRechargeRecordRepository;
import com.bradypod.web.service.repository.jpa.TokenRepository;
import com.bradypod.web.service.repository.spec.DefaultSpecification;
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
	private TokenRepository tokenRes;

	@Autowired
	private PlayUserClientESRepository playUserClientRes;

	@Autowired
	private TokenESRepository tokenESRes;

	@Autowired
	private RoomRechargeRecordRepository roomRechargeRecordRepository;

	@RequestMapping("/wxLogin")
	public String wxLogin(ModelMap map, String code, HttpSession session, String invitationcode, HttpServletRequest request) {
		/** 请求结果 */
		String result = WxUserInfo.getWxUserInfo(code);// 根据code获取微信用户信息
		JSONObject jsonObject = (JSONObject) JSON.parse(result);
		Token userToken = null;
		Gson gson = new Gson();
		try {
			if (null != jsonObject.get("openid")) {
				PlayUser playUser = gson.fromJson(result, PlayUser.class);
				PlayUser newPlayUser = playUserRes.findByOpenid(playUser.getOpenid());
				if (null == newPlayUser) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHHmmssSSS");
					String time = formatter.format(new Date());
					playUser.setInvitationcode(time);
					playUser.setCards(10);
					RoomRechargeRecord roomRechargeRecord = new RoomRechargeRecord();
					roomRechargeRecord.setUserName(playUser.getNickname());
					roomRechargeRecord.setInvitationCode(playUser.getInvitationcode());
					roomRechargeRecord.setRoomCount(10);
					roomRechargeRecord.setPayAmount(BigDecimal.valueOf(30.00));
					roomRechargeRecord.setDirectlyTheLastAmount(BigDecimal.valueOf(0.00));
					roomRechargeRecord.setIndirectTheLastAmount(BigDecimal.valueOf(0.00));
					playUser.setTrtProfit(BigDecimal.valueOf(0.00));
					playUser.setToken(UKTools.getUUID());
					playUser.setPinvitationcode(invitationcode);
					playUserRes.saveAndFlush(playUser);
					roomRechargeRecordRepository.saveAndFlush(roomRechargeRecord);
					map.addAttribute("url", ConfigUtil.GAME_URL + "?userId=" + playUser.getId());
				} else {
					playUser = newPlayUser;
					map.addAttribute("url", ConfigUtil.GAME_URL + "?userId=" + playUser.getId());
					userToken = tokenESRes.findById(playUser.getToken());
					if (userToken != null) {
						tokenESRes.delete(userToken);
						userToken = null;
					}
				}
				String ip = UKTools.getIpAddr(request);
//				IP ipdata = IPTools.getInstance().findGeography(ip);
				userToken = new Token();
				userToken.setIp(ip);
//				userToken.setRegion(ipdata.getProvince() + ipdata.getCity());
				userToken.setId(UKTools.getUUID());
				userToken.setUserid(playUser.getId());
				userToken.setCreatetime(new Date());
				userToken.setOrgi(playUser.getOrgi());
				AccountConfig config = CacheConfigTools.getGameAccountConfig(BMDataContext.SYSTEM_ORGI);
				if (config != null && config.getExpdays() > 0) {
					userToken.setExptime(new Date(System.currentTimeMillis() + 60 * 60 * 24 * config.getExpdays() * 1000));// 默认有效期 ， 7天
				} else {
					userToken.setExptime(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 7 * 1000));// 默认有效期 ， 7天
				}
				userToken.setLastlogintime(new Date());
				userToken.setUpdatetime(new Date(0));

				tokenESRes.save(userToken);
				playUser.setToken(userToken.getId());
				CacheHelper.getApiUserCacheBean().put(userToken.getId(), userToken, userToken.getOrgi());
				CacheHelper.getApiUserCacheBean().put(playUser.getId(), playUser, userToken.getOrgi());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/apps/business/platform/game/wxGetCode/main";
	}

	/**
	 * @Title: findPlayUserInfo
	 * @Description: TODO(获取回显用户信息)
	 * @param token
	 * @return 设定文件 JSONObject 返回类型
	 */
	@ResponseBody
	@RequestMapping("/findPlayUserInfo")
	public JSONObject findPlayUserInfo(String token) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			PlayUser playUser = playUserRes.findByToken(token);
			dataMap.put("playUser", playUser);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "查询失败");
		}
		return (JSONObject) JSONObject.toJSON(dataMap);
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
			Pageable pageable = new PageRequest(page - 1, limit);
			DefaultSpecification<PlayUser> spec = new DefaultSpecification<PlayUser>();
			if (null != playUser.getNickname() && !playUser.getNickname().equals("")) spec.setParams("nickname", "like", "%" + playUser.getNickname() + "%");
			Page<PlayUser> p = playUserRes.findAll(spec, pageable);
			List<PlayUserVo> puolist = new ArrayList<PlayUserVo>();
			for (PlayUser pu : p.getContent()) {
				PlayUserVo puv = new PlayUserVo();
				BeanUtils.copyProperties(pu, puv);
				if (null != puv.getPinvitationcode()) {
					String supAccount = playUserRes.findByInvitationcode(puv.getPinvitationcode()).getNickname();
					puv.setSupAccount(supAccount);
				}
				int subCount = playUserRes.countByPinvitationcode(puv.getInvitationcode());
				puv.setSubCount(String.valueOf(subCount));
				puolist.add(puv);
			}
			dataMap.put("data", puolist);
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
			playUserRes.setPinvitationcodeById(playUser.getPinvitationcode(), playUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "添加邀请码失败");
		}
		return (JSONObject) JSONObject.toJSON(dataMap);
	}

	/**
	 * @Title: deductRoomCard
	 * @Description: TODO(扣除房卡)
	 * @param playUser
	 * @return 设定文件 JSONObject 返回类型
	 */
	@ResponseBody
	@RequestMapping("/deductRoomCard")
	public JSONObject deductRoomCard(@SessionAttribute("mgPlayUser") PlayUser playUser) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			PlayUser newPlayUser = playUserRes.findByOpenid(playUser.getOpenid());
			int cards = newPlayUser.getCards() - 3;
			if (cards < 0) {
				throw new Exception("扣卡失败");
			}
			playUserRes.setCardsById(cards, newPlayUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
		}
		return (JSONObject) JSONObject.toJSON(dataMap);
	}
}
