package menu;

import java.util.List;

/**
 * @Title: Menu
 * @Description: 菜单完整信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月18日
 */
public class Menu {
	private String appid;
	private String appsecret;
	private String accesstoken;
	private String realAppid;
	private List<MenuNode> nodeList;
	
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
		return appsecret;
	}
	/**
	 * @param appSecret the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appsecret = appSecret;
	}
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accesstoken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accesstoken = accessToken;
	}
	/**
	 * @return the realAppid
	 */
	public String getRealAppid() {
		return realAppid;
	}
	/**
	 * @param realAppid the realAppid to set
	 */
	public void setRealAppid(String realAppid) {
		this.realAppid = realAppid;
	}
	/**
	 * @return the nodeList
	 */
	public List<MenuNode> getNodeList() {
		return nodeList;
	}
	/**
	 * @param nodeList the nodeList to set
	 */
	public void setNodeList(List<MenuNode> nodeList) {
		this.nodeList = nodeList;
	}
}
