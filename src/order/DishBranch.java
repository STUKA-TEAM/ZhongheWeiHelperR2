package order;

import java.sql.Timestamp;

/**
 * @Title: DishBranch
 * @Description: 分店菜品信息
 * @Company: Tuka
 * @author ben
 * @date 2014年5月5日
 */
public class DishBranch {
	private int dishid;
	private Timestamp createTime;
	private String dishName;
	private String dishPic;
	private String dishUnit;
	private int recomNum;
	private String dishDesc;
	private int price;
	private int available;
	private int count;
	
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
	 * @return the available
	 */
	public int getAvailable() {
		return available;
	}
	/**
	 * @param available the available to set
	 */
	public void setAvailable(int available) {
		this.available = available;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	public String getDishDesc() {
		return dishDesc;
	}
	public void setDishDesc(String dishDesc) {
		this.dishDesc = dishDesc;
	}
}
