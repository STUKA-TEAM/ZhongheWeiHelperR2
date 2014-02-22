package register;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Title: AuthInfo
 * @Description: 权限关键信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年2月22日
 */
public class AuthInfo {
	private int sid;
	private String authPinyin;
	private BigDecimal price;
	private Timestamp expiredTime;
	
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
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
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
}
