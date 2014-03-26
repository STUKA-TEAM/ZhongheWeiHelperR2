package vote;

import java.util.List;

/**
 * @Title: VoteContent
 * @Description: 单个用户投票信息
 * @Company: Tuka
 * @author ben
 * @date 2014年3月26日
 */
public class VoteContent {
	private int voteid;
	private String openid;
	private String contact;
	private List<Integer> answer;
	
	/**
	 * @return the voteid
	 */
	public int getVoteid() {
		return voteid;
	}
	/**
	 * @param voteid the voteid to set
	 */
	public void setVoteid(int voteid) {
		this.voteid = voteid;
	}
	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
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
	 * @return the answer
	 */
	public List<Integer> getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(List<Integer> answer) {
		this.answer = answer;
	}
}
