package website;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Title: Step1Info
 * @Description: 向导第一步信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月27日
 */
public class Step1Info {
	private String getCode;
	private String title;
	private String phone;
	private String address;
	private BigDecimal lng;
	private BigDecimal lat;
	private String coverPic;
	private String coverText;
	private String shareTitle;
	private String shareContent;
	private String footerText;
	private int themeId;
	private List<String> imageList;
	
	/**
	 * @return the getCode
	 */
	public String getGetCode() {
		return getCode;
	}
	/**
	 * @param getCode the getCode to set
	 */
	public void setGetCode(String getCode) {
		this.getCode = getCode;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the coverPic
	 */
	public String getCoverPic() {
		return coverPic;
	}
	/**
	 * @param coverPic the coverPic to set
	 */
	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}
	/**
	 * @return the coverText
	 */
	public String getCoverText() {
		return coverText;
	}
	/**
	 * @param coverText the coverText to set
	 */
	public void setCoverText(String coverText) {
		this.coverText = coverText;
	}
	/**
	 * @return the shareTitle
	 */
	public String getShareTitle() {
		return shareTitle;
	}
	/**
	 * @param shareTitle the shareTitle to set
	 */
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	/**
	 * @return the shareContent
	 */
	public String getShareContent() {
		return shareContent;
	}
	/**
	 * @param shareContent the shareContent to set
	 */
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	/**
	 * @return the footerText
	 */
	public String getFooterText() {
		return footerText;
	}
	/**
	 * @param footerText the footerText to set
	 */
	public void setFooterText(String footerText) {
		this.footerText = footerText;
	}
	/**
	 * @return the themeId
	 */
	public int getThemeId() {
		return themeId;
	}
	/**
	 * @param themeId the themeId to set
	 */
	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}
	/**
	 * @return the imageList
	 */
	public List<String> getImageList() {
		return imageList;
	}
	/**
	 * @param imageList the imageList to set
	 */
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
}
