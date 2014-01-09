package elove;

import java.sql.Timestamp;

/**
 * @Title: EloveInfo
 * @Description: elove可查看信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class EloveInfo {
	private int eloveid;
	private Timestamp createTime;
	private Timestamp expiredTime;
	private boolean isVaild;
	private String password;
	private String xinLang;
	private String xinNiang;
	
	/**
	 * @return the eloveid
	 */
	public int getEloveid() {
		return eloveid;
	}
	/**
	 * @param eloveid the eloveid to set
	 */
	public void setEloveid(int eloveid) {
		this.eloveid = eloveid;
	}
	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the expiredTime
	 */
	public Timestamp getExpiredTime() {
		return expiredTime;
	}
	/**
	 * @param expiredTime the expiredTime to set
	 */
	public void setExpiredTime(Timestamp expiredTime) {
		this.expiredTime = expiredTime;
	}
	/**
	 * @return the isVaild
	 */
	public boolean getIsVaild() {
		return isVaild;
	}
	/**
	 * @param isVaild the isVaild to set
	 */
	public void setIsVaild(boolean isVaild) {
		this.isVaild = isVaild;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the xinLang
	 */
	public String getXinLang() {
		return xinLang;
	}
	/**
	 * @param xinLang the xinLang to set
	 */
	public void setXinLang(String xinLang) {
		this.xinLang = xinLang;
	}
	/**
	 * @return the xinNiang
	 */
	public String getXinNiang() {
		return xinNiang;
	}
	/**
	 * @param xinNiang the xinNiang to set
	 */
	public void setXinNiang(String xinNiang) {
		this.xinNiang = xinNiang;
	}
}
