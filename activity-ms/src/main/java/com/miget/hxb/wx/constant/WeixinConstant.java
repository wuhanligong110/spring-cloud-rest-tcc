package com.miget.hxb.wx.constant;

import java.io.File;

public class WeixinConstant {

    /************************************商户相关资料【海峡两岸微商城】*****************************************/
	/**
	 * 公众号id
	 */
	public static final String APPID = "wx6f1c07cceeb9e7c9";
	/**
	 * 公众号的appsecret
	 */
	public static final String SECRET = "40412277db636ed2f9a491d4aa2b3eaa";
	/**
	 * 商户号
	 */
	public static final String MCH_ID = "1484593492";
	/**
	 * 支付密钥
	 */
	public static final String 	KEY = "1f6b92a4af464748b0afc2b8a11f8bf3";
	/**
     * 密钥文件的存放路径
     */
    public static final String KEY_PATH = File.separator+"data"+File.separator+"cert"+File.separator+"hxlawsc"+File.separator+"apiclient_cert.p12";
	/**
	 * 支付结果通用通知
	 */
	public static final String NOTIFYURL = "http://hxlawsc.jiuguishangcheng.com/rest/api/wxpay/paynotify";
	/**
	 * 公众号接口配置信息中的 Token
	 */
	public static final String TOKEN = "v5ent";
	/**
	 * 带参数临时二维码有效时间 30天
	 */
	public static final int EXPIRESECONDS = 30*24*60*60;
	/**
	 * 首页链接地址(微信授权)
	 */
	public static final String INDEX_PAGE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6f1c07cceeb9e7c9&redirect_uri=http%3a%2f%2fhxlawsc.jiuguishangcheng.com&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	/**
	 * 微信模板消息id
	 */
	public static final String TEMPLET_TYPE = "r4Tt1jwIZwtsDCbyUd48p-Kn-m25pPIfImnZ3VvVW7Q";
	/**
	 * 开发者微信号
	 */
	public static final String FROM_USER_NAME="gh_21f5981be41c";
}
