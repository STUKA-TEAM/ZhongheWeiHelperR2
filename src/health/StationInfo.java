package health;

/**
 * @Title: StationInfo
 * @Description: 体检站微信与主机绑定信息
 * @Company: tuka
 * @author ben
 * @date 2014年7月18日
 */
public class StationInfo {
	private int mid;
	private String appid;
	private String host;
	
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
}
