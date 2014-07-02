package voucher;

import java.sql.Timestamp;

/**
 * @Title: Voucher
 * @Description: voucher优惠券信息
 * @Company: tuka
 * @author ben
 * @date 2014年6月17日
 */
public class Voucher {
	private int voucherid;
    private String appid;
	private String title;
	private Timestamp createTime;
	private String description;
	private String coverPic;
	private String destroyKey;
	private int isPublic;
	private int totalNum;
	private int restNum;
	
	/**
	 * @return the voucherid
	 */
	public int getVoucherid() {
		return voucherid;
	}
	/**
	 * @param voucherid the voucherid to set
	 */
	public void setVoucherid(int voucherid) {
		this.voucherid = voucherid;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the destroyKey
	 */
	public String getDestroyKey() {
		return destroyKey;
	}
	/**
	 * @param destroyKey the destroyKey to set
	 */
	public void setDestroyKey(String destroyKey) {
		this.destroyKey = destroyKey;
	}
	/**
	 * @return the isPublic
	 */
	public int getIsPublic() {
		return isPublic;
	}
	/**
	 * @param isPublic the isPublic to set
	 */
	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}
	/**
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}
	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	/**
	 * @return the restNum
	 */
	public int getRestNum() {
		return restNum;
	}
	/**
	 * @param restNum the restNum to set
	 */
	public void setRestNum(int restNum) {
		this.restNum = restNum;
	}
}
