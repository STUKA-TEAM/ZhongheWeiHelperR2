package elove;

import java.math.BigDecimal;

/**
 * @Title: Step4Info
 * @Description: 向导第四步信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class Step4Info {
	private String weddingDate;
	private String weddingAddress;
	private BigDecimal lng;
	private BigDecimal lat;
	private String phone;
	
	/**
	 * @return the weddingDate
	 */
	public String getWeddingDate() {
		return weddingDate;
	}
	/**
	 * @param weddingDate the weddingDate to set
	 */
	public void setWeddingDate(String weddingDate) {
		this.weddingDate = weddingDate;
	}
	/**
	 * @return the weddingAddress
	 */
	public String getWeddingAddress() {
		return weddingAddress;
	}
	/**
	 * @param weddingAddress the weddingAddress to set
	 */
	public void setWeddingAddress(String weddingAddress) {
		this.weddingAddress = weddingAddress;
	}
	/**
	 * @return the lng
	 */
	public BigDecimal getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
	/**
	 * @return the lat
	 */
	public BigDecimal getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
