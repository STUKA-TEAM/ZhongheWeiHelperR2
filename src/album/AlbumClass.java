package album;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: AlbumClass
 * @Description: albumclass信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月17日
 */
public class AlbumClass {
	private int classid;
	private String appid;
	private String className;
	private Timestamp createTime;
	private List<Integer> albumidList;
	private int albumCount;
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
	 * @return the albumidList
	 */
	public List<Integer> getAlbumidList() {
		return albumidList;
	}
	/**
	 * @param albumidList the albumidList to set
	 */
	public void setAlbumidList(List<Integer> albumidList) {
		this.albumidList = albumidList;
	}
	/**
	 * @return the albumCount
	 */
	public int getAlbumCount() {
		return albumCount;
	}
	/**
	 * @param albumCount the albumCount to set
	 */
	public void setAlbumCount(int albumCount) {
		this.albumCount = albumCount;
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
