package register;

import java.util.List;

/**
 * @Title: AppInfo
 * @Description: 应用信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月25日
 */
public class AppInfo {
	private int sid;
	private List<String> authNameList;
	private String appid;
	private String wechatToken;
	private String wechatName;
	private String wechatOriginalId;
	private String wechatNumber;
	private String address;
	private String industry;
	private String url;
	private boolean isCharged;
	
	/**
	 * @return the sid
	 */
	public int getSid() {
		return sid;
	}
	/**
	 * @param sid the sid to set
	 */
	public void setSid(int sid) {
		this.sid = sid;
	}
	/**
	 * @return the authNameList
	 */
	public List<String> getAuthNameList() {
		return authNameList;
	}
	/**
	 * @param authNameList the authNameList to set
	 */
	public void setAuthNameList(List<String> authNameList) {
		this.authNameList = authNameList;
	}
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
	 * @return the wechatToken
	 */
	public String getWechatToken() {
		return wechatToken;
	}
	/**
	 * @param wechatToken the wechatToken to set
	 */
	public void setWechatToken(String wechatToken) {
		this.wechatToken = wechatToken;
	}
	/**
	 * @return the wechatName
	 */
	public String getWechatName() {
		return wechatName;
	}
	/**
	 * @param wechatName the wechatName to set
	 */
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}
	/**
	 * @return the wechatOriginalId
	 */
	public String getWechatOriginalId() {
		return wechatOriginalId;
	}
	/**
	 * @param wechatOriginalId the wechatOriginalId to set
	 */
	public void setWechatOriginalId(String wechatOriginalId) {
		this.wechatOriginalId = wechatOriginalId;
	}
	/**
	 * @return the wechatNumber
	 */
	public String getWechatNumber() {
		return wechatNumber;
	}
	/**
	 * @param wechatNumber the wechatNumber to set
	 */
	public void setWechatNumber(String wechatNumber) {
		this.wechatNumber = wechatNumber;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}
	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the isCharged
	 */
	public boolean getIsCharged() {
		return isCharged;
	}
	/**
	 * @param isCharged the isCharged to set
	 */
	public void setIsCharged(boolean isCharged) {
		this.isCharged = isCharged;
	}
}
