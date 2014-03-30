package lottery;

import java.math.BigDecimal;

/**
 * @Title: LotteryWheelItem
 * @Description: lotterywheel抽奖奖项
 * @Company: Tuka
 * @author ben
 * @date 2014年3月29日
 */
public class LotteryWheelItem {
	private int itemid;
	private String itemDesc;
	private int itemCount;
	private BigDecimal itemPercent;
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
	 * @return the itemDesc
	 */
	public String getItemDesc() {
		return itemDesc;
	}
	/**
	 * @param itemDesc the itemDesc to set
	 */
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	/**
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}
	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	/**
	 * @return the itemPercent
	 */
	public BigDecimal getItemPercent() {
		return itemPercent;
	}
	/**
	 * @param itemPercent the itemPercent to set
	 */
	public void setItemPercent(BigDecimal itemPercent) {
		this.itemPercent = itemPercent;
	}
	
}
