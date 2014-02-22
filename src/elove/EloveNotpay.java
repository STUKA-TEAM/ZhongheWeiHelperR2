package elove;

/**
 * @Title: EloveNotpay
 * @Description: elove未付款信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年2月22日
 */
public class EloveNotpay {
	private String wechatName;
	private String appid;
	private int notPayNumber;
	
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
	 * @return the notPayNumber
	 */
	public int getNotPayNumber() {
		return notPayNumber;
	}
	/**
	 * @param notPayNumber the notPayNumber to set
	 */
	public void setNotPayNumber(int notPayNumber) {
		this.notPayNumber = notPayNumber;
	}
}
