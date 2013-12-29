package elove;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: EloveWizard
 * @Description: 向导完整向导信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class EloveWizard {
	private int eloveid;
	private String appid;
	private Timestamp createTime;
	private Timestamp expiredTime;
	private String title;
	private String password;
	private String coverPic;
	private String coverText;
	private String shareTitle;
	private String shareContent;
	private String majorGroupPhoto;
	private int themeid;
	private String music;
	private String xinLang;
	private String xinNiang;
    private List<String> storyImagePath;
    private String storyTextImagePath;
	private List<String> dressImagePath;
	private List<String> dressVideoPath;
	private String weddingDate;
	private String weddingAddress;
	private BigDecimal lng;
	private BigDecimal lat;
	private String phone;
	private List<String> recordImagePath;
	private List<String> recordVideoPath;
	private String footerText;
	private String sideCorpInfo;
	
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
	 * @return the majorGroupPhoto
	 */
	public String getMajorGroupPhoto() {
		return majorGroupPhoto;
	}
	/**
	 * @param majorGroupPhoto the majorGroupPhoto to set
	 */
	public void setMajorGroupPhoto(String majorGroupPhoto) {
		this.majorGroupPhoto = majorGroupPhoto;
	}
	/**
	 * @return the themeid
	 */
	public int getThemeid() {
		return themeid;
	}
	/**
	 * @param themeid the themeid to set
	 */
	public void setThemeid(int themeid) {
		this.themeid = themeid;
	}
	/**
	 * @return the music
	 */
	public String getMusic() {
		return music;
	}
	/**
	 * @param music the music to set
	 */
	public void setMusic(String music) {
		this.music = music;
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
	/**
	 * @return the storyImagePath
	 */
	public List<String> getStoryImagePath() {
		return storyImagePath;
	}
	/**
	 * @param storyImagePath the storyImagePath to set
	 */
	public void setStoryImagePath(List<String> storyImagePath) {
		this.storyImagePath = storyImagePath;
	}
	/**
	 * @return the storyTextImagePath
	 */
	public String getStoryTextImagePath() {
		return storyTextImagePath;
	}
	/**
	 * @param storyTextImagePath the storyTextImagePath to set
	 */
	public void setStoryTextImagePath(String storyTextImagePath) {
		this.storyTextImagePath = storyTextImagePath;
	}
	/**
	 * @return the dressImagePath
	 */
	public List<String> getDressImagePath() {
		return dressImagePath;
	}
	/**
	 * @param dressImagePath the dressImagePath to set
	 */
	public void setDressImagePath(List<String> dressImagePath) {
		this.dressImagePath = dressImagePath;
	}
	/**
	 * @return the dressVideoPath
	 */
	public List<String> getDressVideoPath() {
		return dressVideoPath;
	}
	/**
	 * @param dressVideoPath the dressVideoPath to set
	 */
	public void setDressVideoPath(List<String> dressVideoPath) {
		this.dressVideoPath = dressVideoPath;
	}
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
	/**
	 * @return the recordImagePath
	 */
	public List<String> getRecordImagePath() {
		return recordImagePath;
	}
	/**
	 * @param recordImagePath the recordImagePath to set
	 */
	public void setRecordImagePath(List<String> recordImagePath) {
		this.recordImagePath = recordImagePath;
	}
	/**
	 * @return the recordVideoPath
	 */
	public List<String> getRecordVideoPath() {
		return recordVideoPath;
	}
	/**
	 * @param recordVideoPath the recordVideoPath to set
	 */
	public void setRecordVideoPath(List<String> recordVideoPath) {
		this.recordVideoPath = recordVideoPath;
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
	 * @return the sideCorpInfo
	 */
	public String getSideCorpInfo() {
		return sideCorpInfo;
	}
	/**
	 * @param sideCorpInfo the sideCorpInfo to set
	 */
	public void setSideCorpInfo(String sideCorpInfo) {
		this.sideCorpInfo = sideCorpInfo;
	}
	
}
