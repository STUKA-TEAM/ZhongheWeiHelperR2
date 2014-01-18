package register;

import java.util.List;

/**
 * @Title: Welcome
 * @Description: 欢迎页配置信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月18日
 */
public class Welcome {
	private String type;
	private String appid;
	private List<WelcomeContent> contents;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public List<WelcomeContent> getContents() {
		return contents;
	}
	public void setContents(List<WelcomeContent> contents) {
		this.contents = contents;
	}
}
