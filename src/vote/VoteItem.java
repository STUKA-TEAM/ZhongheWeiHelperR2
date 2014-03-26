package vote;

/**
 * @Title: VoteItem
 * @Description: vote投票选项信息
 * @Company: Tuka
 * @author ben
 * @date 2014年3月25日
 */
public class VoteItem {
	private int itemid;
	private String itemName;
	private String itemPic;
	private int count;
	private double percent;
	
	/**
	 * @return the itemid
	 */
	public int getItemid() {
		return itemid;
	}
	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the itemPic
	 */
	public String getItemPic() {
		return itemPic;
	}
	/**
	 * @param itemPic the itemPic to set
	 */
	public void setItemPic(String itemPic) {
		this.itemPic = itemPic;
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
	/**
	 * @return the percent
	 */
	public double getPercent() {
		return percent;
	}
	/**
	 * @param percent the percent to set
	 */
	public void setPercent(double percent) {
		this.percent = percent;
	}
	
}
