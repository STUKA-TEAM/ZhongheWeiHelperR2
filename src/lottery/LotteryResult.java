package lottery;

/**
 * @Title: LotteryResult
 * @Description: lotteryresult单条抽奖结果
 * @Company: Tuka
 * @author ben
 * @date 2014年5月4日
 */
public class LotteryResult {
	private int resultid;
	private String contact;
	private int status;
	
	/**
	 * @return the resultid
	 */
	public int getResultid() {
		return resultid;
	}
	/**
	 * @param resultid the resultid to set
	 */
	public void setResultid(int resultid) {
		this.resultid = resultid;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
}
