package lottery;

import java.util.List;

/**
 * @Title: LotteryPrize
 * @Description: lotteryprize某奖项中奖详情
 * @Company: Tuka
 * @author ben
 * @date 2014年5月4日
 */
public class LotteryPrize {
	private int prizeid;
	private int prizeIndex;
	private String prizeDesc;
	private int prizeNum;
	private int luckyNum;
	private List<LotteryResult> luckyList;

	/**
	 * @return the prizeid
	 */
	public int getPrizeid() {
		return prizeid;
	}
	/**
	 * @param prizeid the prizeid to set
	 */
	public void setPrizeid(int prizeid) {
		this.prizeid = prizeid;
	}
	/**
	 * @return the prizeIndex
	 */
	public int getPrizeIndex() {
		return prizeIndex;
	}
	/**
	 * @param prizeIndex the prizeIndex to set
	 */
	public void setPrizeIndex(int prizeIndex) {
		this.prizeIndex = prizeIndex;
	}
	/**
	 * @return the prizeDesc
	 */
	public String getPrizeDesc() {
		return prizeDesc;
	}
	/**
	 * @param prizeDesc the prizeDesc to set
	 */
	public void setPrizeDesc(String prizeDesc) {
		this.prizeDesc = prizeDesc;
	}
	/**
	 * @return the prizeNum
	 */
	public int getPrizeNum() {
		return prizeNum;
	}
	/**
	 * @param prizeNum the prizeNum to set
	 */
	public void setPrizeNum(int prizeNum) {
		this.prizeNum = prizeNum;
	}
	/**
	 * @return the luckyNum
	 */
	public int getLuckyNum() {
		return luckyNum;
	}
	/**
	 * @param luckyNum the luckyNum to set
	 */
	public void setLuckyNum(int luckyNum) {
		this.luckyNum = luckyNum;
	}
	/**
	 * @return the luckyList
	 */
	public List<LotteryResult> getLuckyList() {
		return luckyList;
	}
	/**
	 * @param luckyList the luckyList to set
	 */
	public void setLuckyList(List<LotteryResult> luckyList) {
		this.luckyList = luckyList;
	}
}
