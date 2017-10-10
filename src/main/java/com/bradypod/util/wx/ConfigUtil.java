package com.bradypod.util.wx;
/**
 *
 *
 * 类描述：微信相关参数配置 <br>
 * 作者：田帅 <br>
 * 创建时间：2017-09-16 <br>
 * 版本：V1.0
 */
public class ConfigUtil {

	/** 微信公众号的ID */
	public final static String APPIDH5 = "wx97f93285af090c3e";
	/** 微信公众号的应用密码 */
	public final static String APP_SECRECTH5 = "1f9470d9289615b41e0a6b487bbe5b85";
	/** 微信公众号商户号 */
	public final static String MCH_IDH5 = "1455666502";
	/** API密钥用户自己设置 */
	public final static String API_KEYH5 = "C50E092772003E8BAFAD8FB7D453888C";
	/** 签名加密方式*/
	public final static String SIGN_TYPE = "MD5";

	/** 微信支付统一接口的回调 */
	public final static String NOTIFY_URL = "http://game.bizpartner.cn/wxController/wxLoginHtml";
	/** oauth2授权接口(GET) */
	public final static String OAUTH2_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	/** 微信授权code值路径 */
	public final static String CODE_URL = "http%3a%2f%2fgame.bizpartner.cn%2fregisterPlayer%2fwxLogin";
	/**企业向个人打款接口 */
	public final static String WX_TRANSFERS_AMOUNT_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	/** 订单查询接口(POST) */
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	/** 关闭订单接口(POST) */
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";

	/** 跳转游戏界面地址 */
	public final static String GAME_URL = "http://192.168.199.229:7456/";
}
