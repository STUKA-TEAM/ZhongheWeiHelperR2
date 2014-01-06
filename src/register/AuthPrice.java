package register;

import java.math.BigDecimal;

/**
 * @Title: AuthPrice
 * @Description: 权限售价信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class AuthPrice {
	private String authName;
	private BigDecimal price;
	
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
}
