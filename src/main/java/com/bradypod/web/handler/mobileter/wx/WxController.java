package com.bradypod.web.handler.mobileter.wx;

import com.bradypod.util.wx.*;
import com.bradypod.web.model.PlayUser;
import com.bradypod.web.service.repository.jpa.PlayUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 
 * 
 * 类描述：微信授权登录 <br>
 * 作者：田帅 <br>
 * 创建时间：2017-09-16 <br>
 * 版本：V1.0
 */
@Controller
@RequestMapping(value = "/wxController")
public class WxController {

	@Autowired
	private PlayUserRepository playUserRes;
	
	/**
	 * 跳转微信
	 * wxController/wxLoginHtml
	 * @return
     */
	@RequestMapping(value = "/wxLoginHtml")
	public String wxMain(ModelMap map){
		map.addAttribute("userName","");
		return "/apps/business/platform/game/wxGetCode/main";
	}

	/**
	 * 
	 * 方法描述: 跳转到h5微信支付<br>
	 * 作者：田帅 <br>
	 * 创建时间：2017-09-16 <br>
	 * 版本：V1.0
	 */
	@RequestMapping(value = "/toH5WxPay")
	public Object toH5WxPay(String out_trade_no, String total_fee, String body) {
		System.out.println("out_trade_no=" + out_trade_no);
		System.out.println("total_fee=" + total_fee);
		System.out.println("body=" + body);
		String backUri = "";//微信授权后跳转的支付界面
		backUri = backUri + "?out_trade_no=" + out_trade_no + "&total_fee="
				+ total_fee + "&body=" + body;
		// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
		backUri = URLEncoder.encode(backUri);
		// scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid="
				+ ConfigUtil.APPIDH5
				+ "&redirect_uri="
				+ backUri
				+ "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		return "redirect:" + url;
	}


	/**
	 *
	 * 方法描述: H5向微信请求支付信息<br>
	 * 作者：田帅 <br>
	 * 创建时间：2017-09-16 <br>
	 * 版本：V1.0
	 */
	@RequestMapping(value = "/getWXPayXmlH5")
	@ResponseBody
	public Object getWXPayXmlH5(HttpSession session,HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "0") int orderprices) {
		Map<String, Object> json = new HashMap<String, Object>();
		String openid = ((PlayUser) session.getAttribute("mgPlayUser")).getOpenid();//获取用户id
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHHmmssSSS");
		String out_trade_no = formatter.format(new Date());//充值订单号时间戳

		System.out.println("out_trade_no=" + out_trade_no);
		System.out.println("total_fee=" + orderprices);
		// System.out.println("body=" + body);
		System.out.println("openid=" + openid);
		// 金额转化为分为单位
		int finalmoney = orderprices;

		String appid = ConfigUtil.APPID;
		String appsecret = ConfigUtil.APP_SECRECTH5;
		String partner = ConfigUtil.MCH_IDH5;
		String partnerkey = ConfigUtil.API_KEYH5;

		request.getSession();
		// 商户号
		String mch_id = partner;
		// 随机数
		String nonce_str = PayCommonUtil.CreateNoncestr();
		// 商品描述根据情况修改
		String body = "麻将游戏";

		// 订单生成的机器 IP
		String spbill_create_ip = PayCommonUtil.getIpAddress(request);
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = ConfigUtil.NOTIFY_URL;
		System.out.println("wowowo===================="+notify_url);
		System.out.println("=========================================================================================");
		System.out.println("wowowo===================="+notify_url);
		System.out.println("=========================================================================================");
		String trade_type = "JSAPI";
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("total_fee", finalmoney + "");
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openid);

//		if("1".equals(status)){
//			// 附件属性,原样返回
//			packageParams.put("attach", "clear");
//		}else {
//			// 附件属性,原样返回
//			packageParams.put("attach", "trade");
//		}

		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = PayCommonUtil.createSignature(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign>" + sign + "</sign>"
				+ "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>"
				+ out_trade_no + "</out_trade_no>" + "<total_fee>" + finalmoney
				+ "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<openid>" + openid + "</openid>"
				+ "<attach>"+packageParams.get("attach")+"</attach>"
				+ "</xml>";
		System.out.println("xml=" + xml);
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = "";
		try {
			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if (prepay_id.equals("")) {
				request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
				response.sendRedirect("error.jsp");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String appid2 = appid;
		String timestamp = Sha1Util.getTimeStamp();
		String nonceStr2 = nonce_str;
		String prepay_id2 = "prepay_id=" + prepay_id;
		String packages = prepay_id2;
		finalpackage.put("appId", appid2);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonceStr2);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		String finalsign = reqHandler.createSign(finalpackage);

		json.put("appid", appid2);
		json.put("timeStamp", timestamp);
		json.put("nonceStr", nonceStr2);
		json.put("package", packages);
		json.put("paySign", finalsign);
		json.put("status", 200);
		return json;

	}

	/**
	 * 微信授权域名地址
	 * @return
     */
	@RequestMapping({"/MP_verify_V4mcS82aXhi5ivFV.txt"})
	@ResponseBody
	public String wxCodeTxt(){
		return "V4mcS82aXhi5ivFV";
	}
	
	/**
	 * @Title: checkDownPlayer
	 * @Description: TODO(验证玩家是否有下家，true有false没有)
	 * @return 设定文件 boolean 返回类型
	 */
	@ResponseBody
	@RequestMapping("checkDownPlayer")
	public boolean checkDownPlayer(PlayUser playUser) {
		boolean flag = true;
		List<PlayUser> playUserList = playUserRes.findByPinvitationcode(playUser.getInvitationcode());
		if(playUserList.isEmpty()){
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 
	* @Title: rechargeManagement 
	* @Description: TODO(支付完成后调整数据库) 
	* @param playUser 用户信息
	* @param preferentialAmount 优惠金额
	* @param payAmount 支付金额
	* @return    设定文件 
	* String    返回类型
	 */
	@ResponseBody
	@RequestMapping("/rechargeManagement")
	public String rechargeManagement(PlayUser playUser,Double preferentialAmount,Double payAmount, Double originalPrice,int roomCount) {
		PlayUser zjPlayUser = playUserRes.findById(playUser.getId());
		int cards = zjPlayUser.getCards() + roomCount;
		playUserRes.setCardsById(cards,playUser.getId());//修改充值完的房卡数量
		if(null != playUser.getPinvitationcode()){
			PlayUser jozPlayUser = playUserRes.findByInvitationcode(playUser.getPinvitationcode());
			
			if(null != jozPlayUser.getPinvitationcode()){
				PlayUser zPlayUser = playUserRes.findByInvitationcode(jozPlayUser.getPinvitationcode());
			}
		}
		
		
		return null;
	}
}
