package order;

import java.sql.Timestamp;

/**
 * @Title: DishClassBranch
 * @Description: 分店菜品类别信息
 * @Company: tuka
 * @author ben
 * @date 2014年6月15日
 */
public class DishClassBranch {
	private int classid;
	private int creatorSid;
	private Timestamp createTime;
	private String className;
	private boolean allowed;
	private int available;
	private int dishCount;
	
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
	 * @return the creatorSid
	 */
	public int getCreatorSid() {
		return creatorSid;
	}
	/**
	 * @param creatorSid the creatorSid to set
	 */
	public void setCreatorSid(int creatorSid) {
		this.creatorSid = creatorSid;
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
	 * @return the allowed
	 */
	public boolean getAllowed() {
		return allowed;
	}
	/**
	 * @param allowed the allowed to set
	 */
	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
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
	/**
	 * @return the dishCount
	 */
	public int getDishCount() {
		return dishCount;
	}
	/**
	 * @param dishCount the dishCount to set
	 */
	public void setDishCount(int dishCount) {
		this.dishCount = dishCount;
	}
}
