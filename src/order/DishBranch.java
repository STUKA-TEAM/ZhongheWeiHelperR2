package order;

/**
 * @Title: DishBranch
 * @Description: 分店菜品信息
 * @Company: Tuka
 * @author ben
 * @date 2014年4月29日
 */
public class DishBranch {
	private int dishid;
	private int branchSid;
	private String price;
	private int available;
	
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
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
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
}
