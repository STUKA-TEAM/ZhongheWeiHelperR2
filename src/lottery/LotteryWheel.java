package lottery;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: LotteryWheel
 * @Description: lotterywheel抽奖
 * @Company: Tuka
 * @author ben
 * @date 2014年3月29日
 */
public class LotteryWheel {
	private int wheelid;
	private String wheeluuid;
	private String appid;
	private String wheelName;
	private Timestamp createTime;
	private String wheelDesc;
	private int maxDayCount;
	private int count;
	private List<LotteryWheelItem> itemList;
	
	/**
	 * @return the wheelid
	 */
	public int getWheelid() {
		return wheelid;
	}
	/**
	 * @param wheelid the wheelid to set
	 */
	public void setWheelid(int wheelid) {
		this.wheelid = wheelid;
	}
	/**
	 * @return the wheeluuid
	 */
	public String getWheeluuid() {
		return wheeluuid;
	}
	/**
	 * @param wheeluuid the wheeluuid to set
	 */
	public void setWheeluuid(String wheeluuid) {
		this.wheeluuid = wheeluuid;
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
	 * @return the wheelName
	 */
	public String getWheelName() {
		return wheelName;
	}
	/**
	 * @param wheelName the wheelName to set
	 */
	public void setWheelName(String wheelName) {
		this.wheelName = wheelName;
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
	 * @return the wheelDesc
	 */
	public String getWheelDesc() {
		return wheelDesc;
	}
	/**
	 * @param wheelDesc the wheelDesc to set
	 */
	public void setWheelDesc(String wheelDesc) {
		this.wheelDesc = wheelDesc;
	}
	/**
	 * @return the maxDayCount
	 */
	public int getMaxDayCount() {
		return maxDayCount;
	}
	/**
	 * @param maxDayCount the maxDayCount to set
	 */
	public void setMaxDayCount(int maxDayCount) {
		this.maxDayCount = maxDayCount;
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
	 * @return the itemList
	 */
	public List<LotteryWheelItem> getItemList() {
		return itemList;
	}
	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<LotteryWheelItem> itemList) {
		this.itemList = itemList;
	}
}
