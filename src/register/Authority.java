package register;

/**
 * @Title: Authority
 * @Description: 权限信息
 * @Company: tuka
 * @author ben
 * @date 2014年6月11日
 */
public class Authority {
	private int authid;
	private String authName;
	private String authPinyin;
	private boolean selected;
	
	/**
	 * @return the authid
	 */
	public int getAuthid() {
		return authid;
	}
	/**
	 * @param authid the authid to set
	 */
	public void setAuthid(int authid) {
		this.authid = authid;
	}
	/**
	 * @return the authName
	 */
	public String getAuthName() {
		return authName;
	}
	/**
	 * @param authName the authName to set
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	/**
	 * @return the authPinyin
	 */
	public String getAuthPinyin() {
		return authPinyin;
	}
	/**
	 * @param authPinyin the authPinyin to set
	 */
	public void setAuthPinyin(String authPinyin) {
		this.authPinyin = authPinyin;
	}
	/**
	 * @return the selected
	 */
	public boolean getSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
