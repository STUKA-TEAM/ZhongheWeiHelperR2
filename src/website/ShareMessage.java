package website;

/**
 * @Title: ShareMessage
 * @Description: 分享依赖信息
 * @Company: Tuka
 * @author ben
 * @date 2014年3月23日
 */
public class ShareMessage {
	private String wechatNumber;
	private String shareTitle;
	private String shareContent;
	private String appLink;
	private String imageLink;
	
	/**
	 * @return the wechatNumber
	 */
	public String getWechatNumber() {
		return wechatNumber;
	}
	/**
	 * @param wechatNumber the wechatNumber to set
	 */
	public void setWechatNumber(String wechatNumber) {
		this.wechatNumber = wechatNumber;
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
	 * @return the appLink
	 */
	public String getAppLink() {
		return appLink;
	}
	/**
	 * @param appLink the appLink to set
	 */
	public void setAppLink(String appLink) {
		this.appLink = appLink;
	}
	/**
	 * @return the imageLink
	 */
	public String getImageLink() {
		return imageLink;
	}
	/**
	 * @param imageLink the imageLink to set
	 */
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
}
