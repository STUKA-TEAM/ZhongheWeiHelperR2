package order;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: Dish
 * @Description: dish菜品信息
 * @Company: Tuka
 * @author ben
 * @date 2014年4月29日
 */
public class Dish {
	private int dishid;
	private String appid;
	private int creatorSid;
	private String dishName;
	private Timestamp createTime;
	private String dishPic;
	private String dishDesc;
	private int price;
	private String dishUnit;
	private int recomNum;
	private List<Integer> classidList;
	private boolean selected;
	private String creatorName;
	
	/**
	 * @return the dishid
	 */
	public int getDishid() {
		return dishid;
	}
	/**
	 * @param dishid the dishid to set
	 */
	public void setDishid(int dishid) {
		this.dishid = dishid;
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
	 * @return the creatorSid
	 */
	public int getCreatorSid() {
		return creatorSid;
	}
	/**
	 * @param creatorSid the creatorSid to set
	 */
	public void setCreatorSid(int creatorSid) {
		this.creatorSid = creatorSid;
	}
	/**
	 * @return the dishName
	 */
	public String getDishName() {
		return dishName;
	}
	/**
	 * @param dishName the dishName to set
	 */
	public void setDishName(String dishName) {
		this.dishName = dishName;
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
	 * @return the dishPic
	 */
	public String getDishPic() {
		return dishPic;
	}
	/**
	 * @param dishPic the dishPic to set
	 */
	public void setDishPic(String dishPic) {
		this.dishPic = dishPic;
	}
	/**
	 * @return the dishDesc
	 */
	public String getDishDesc() {
		return dishDesc;
	}
	/**
	 * @param dishDesc the dishDesc to set
	 */
	public void setDishDesc(String dishDesc) {
		this.dishDesc = dishDesc;
	}
	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	/**
	 * @return the dishUnit
	 */
	public String getDishUnit() {
		return dishUnit;
	}
	/**
	 * @param dishUnit the dishUnit to set
	 */
	public void setDishUnit(String dishUnit) {
		this.dishUnit = dishUnit;
	}
	/**
	 * @return the recomNum
	 */
	public int getRecomNum() {
		return recomNum;
	}
	/**
	 * @param recomNum the recomNum to set
	 */
	public void setRecomNum(int recomNum) {
		this.recomNum = recomNum;
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
	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}
	/**
	 * @param creatorName the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
}
