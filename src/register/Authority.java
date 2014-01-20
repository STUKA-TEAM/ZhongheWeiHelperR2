package register;

/**
 * @Title: Authority
 * @Description: 权限信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月20日
 */
public class Authority {
	private int authid;
	private String authName;
	private String authPinyin;
	
	public int getAuthid() {
		return authid;
	}
	public void setAuthid(int authid) {
		this.authid = authid;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getAuthPinyin() {
		return authPinyin;
	}
	public void setAuthPinyin(String authPinyin) {
		this.authPinyin = authPinyin;
	}
}
