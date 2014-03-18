package menu;

/**
 * @Title: Step1Info
 * @Description: 向导第一步信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月18日
 */
public class Step1Info {
	private String appid;
	private String appSecret;
	private String accessToken;
	
	/**
	 * @return the appid
	 */
	public String getAppid() {
		return appid;
	}
	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}
	/**
	 * @param appSecret the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
