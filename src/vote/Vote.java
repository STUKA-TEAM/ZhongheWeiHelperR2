package vote;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: Vote
 * @Description: vote投票信息
 * @Company: Tuka
 * @author ben
 * @date 2014年3月25日
 */
public class Vote {
	 private int voteid;
	 private String voteName;
	 private Timestamp createTime;
	 private String voteDesc;
	 private String coverPic;
	 private int maxSelected;
	 private List<VoteItem> itemList;
	 
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
	 * @return the voteName
	 */
	public String getVoteName() {
		return voteName;
	}
	/**
	 * @param voteName the voteName to set
	 */
	public void setVoteName(String voteName) {
		this.voteName = voteName;
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
	 * @return the voteDesc
	 */
	public String getVoteDesc() {
		return voteDesc;
	}
	/**
	 * @param voteDesc the voteDesc to set
	 */
	public void setVoteDesc(String voteDesc) {
		this.voteDesc = voteDesc;
	}
	/**
	 * @return the coverPic
	 */
	public String getCoverPic() {
		return coverPic;
	}
	/**
	 * @param coverPic the coverPic to set
	 */
	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}
	/**
	 * @return the maxSelected
	 */
	public int getMaxSelected() {
		return maxSelected;
	}
	/**
	 * @param maxSelected the maxSelected to set
	 */
	public void setMaxSelected(int maxSelected) {
		this.maxSelected = maxSelected;
	}
	/**
	 * @return the itemList
	 */
	public List<VoteItem> getItemList() {
		return itemList;
	}
	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<VoteItem> itemList) {
		this.itemList = itemList;
	}
}
