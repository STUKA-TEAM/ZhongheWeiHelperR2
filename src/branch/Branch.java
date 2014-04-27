package branch;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: Branch
 * @Description: branch分店信息
 * @Company: Tuka
 * @author ben
 * @date 2014年4月24日
 */
public class Branch {
	private int branchSid;
	private int roleid;
	private String username;
	private String password;
	private Timestamp createDate;
	private String storeName;
	private String phone;
	private String address;
	private BigDecimal lng;
	private BigDecimal lat;
	private List<String> imageList;
	private int storeSid;
	private List<Integer> classidList;
	private boolean selected;
	
	/**
	 * @return the branchSid
	 */
	public int getBranchSid() {
		return branchSid;
	}
	/**
	 * @param branchSid the branchSid to set
	 */
	public void setBranchSid(int branchSid) {
		this.branchSid = branchSid;
	}
	/**
	 * @return the roleid
	 */
	public int getRoleid() {
		return roleid;
	}
	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}
	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	/**
	 * @return the storeSid
	 */
	public int getStoreSid() {
		return storeSid;
	}
	/**
	 * @param storeSid the storeSid to set
	 */
	public void setStoreSid(int storeSid) {
		this.storeSid = storeSid;
	}
	/**
	 * @return the classidList
	 */
	public List<Integer> getClassidList() {
		return classidList;
	}
	/**
	 * @param classidList the classidList to set
	 */
	public void setClassidList(List<Integer> classidList) {
		this.classidList = classidList;
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
