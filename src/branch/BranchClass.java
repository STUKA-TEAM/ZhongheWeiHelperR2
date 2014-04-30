package branch;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: BranchClass
 * @Description: branch分店类别信息
 * @Company: Tuka
 * @author ben
 * @date 2014年4月24日
 */
public class BranchClass {
	private int classid;
	private int storeSid;
	private String className;
	private Timestamp createTime;
	private List<Integer> branchSidList;
	private boolean selected;
	private int branchCount;
	
	/**
	 * @return the classid
	 */
	public int getClassid() {
		return classid;
	}
	/**
	 * @param classid the classid to set
	 */
	public void setClassid(int classid) {
		this.classid = classid;
	}
	/**
	 * @return the storeSid
	 */
	public int getStoreSid() {
		return storeSid;
	}
	/**
	 * @param storeSid the storeSid to set
	 */
	public void setStoreSid(int storeSid) {
		this.storeSid = storeSid;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
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
	 * @return the branchSidList
	 */
	public List<Integer> getBranchSidList() {
		return branchSidList;
	}
	/**
	 * @param branchSidList the branchSidList to set
	 */
	public void setBranchSidList(List<Integer> branchSidList) {
		this.branchSidList = branchSidList;
	}
	/**
	 * @return the selected
	 */
	public boolean getSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * @return the branchCount
	 */
	public int getBranchCount() {
		return branchCount;
	}
	/**
	 * @param branchCount the branchCount to set
	 */
	public void setBranchCount(int branchCount) {
		this.branchCount = branchCount;
	}
}
