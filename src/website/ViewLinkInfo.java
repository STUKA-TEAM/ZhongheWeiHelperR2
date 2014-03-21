package website;

import org.springframework.stereotype.Controller;

/**
 * @Title: ViewLinkInfo
 * @Description: 资源链接接口
 * @Company: Tuka
 * @author ben
 * @date 2014年3月21日
 */
@Controller
public class ViewLinkInfo {
	private Integer websiteid;
	private String appPath;
	
	/**
	 * @return the websiteid
	 */
	public Integer getWebsiteid() {
		return websiteid;
	}
	/**
	 * @param websiteid the websiteid to set
	 */
	public void setWebsiteid(Integer websiteid) {
		this.websiteid = websiteid;
	}
	/**
	 * @return the appPath
	 */
	public String getAppPath() {
		return appPath;
	}
	/**
	 * @param appPath the appPath to set
	 */
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
}
