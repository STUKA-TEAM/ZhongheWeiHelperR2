package order;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: DishClass
 * @Description: 菜品类别信息
 * @Company: Tuka
 * @author ben
 * @date 2014年4月29日
 */
public class DishClass {
	private int classid;
	private String appid;
	private String className;
	private Timestamp createTime;
	private List<Integer> dishidList;
	private int dishCount;
	private boolean selected;
	
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
	 * @return the dishidList
	 */
	public List<Integer> getDishidList() {
		return dishidList;
	}
	/**
	 * @param dishidList the dishidList to set
	 */
	public void setDishidList(List<Integer> dishidList) {
		this.dishidList = dishidList;
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
}
