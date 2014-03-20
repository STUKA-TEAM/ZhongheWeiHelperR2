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
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getRealAppid() {
		return realAppid;
	}
	public void setRealAppid(String realAppid) {
		this.realAppid = realAppid;
	}
	public List<MenuNode> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<MenuNode> nodeList) {
		this.nodeList = nodeList;
	}
	

}
