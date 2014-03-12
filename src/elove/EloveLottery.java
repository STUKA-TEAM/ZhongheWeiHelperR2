package elove;

import java.util.List;

/**
 * @Title: EloveLottery
 * @Description: elove抽奖结果信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月11日
 */
public class EloveLottery {
	private List<EloveMessage> messageList;
	private int lucky;
	private boolean success;
	
	/**
	 * @return the messageList
	 */
	public List<EloveMessage> getMessageList() {
		return messageList;
	}
	/**
	 * @param messageList the messageList to set
	 */
	public void setMessageList(List<EloveMessage> messageList) {
		this.messageList = messageList;
	}
	/**
	 * @return the lucky
	 */
	public int getLucky() {
		return lucky;
	}
	/**
	 * @param lucky the lucky to set
	 */
	public void setLucky(int lucky) {
		this.lucky = lucky;
	}
	/**
	 * @return the success
	 */
	public boolean getSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
