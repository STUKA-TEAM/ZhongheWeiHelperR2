package album;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: Album
 * @Description: album信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月17日
 */
public class Album {
	private int albumid;
	private String appid;
	private String albumName;
	private String coverPic;
	private Timestamp createTime;
	private List<Photo> photoList;
	private List<Integer> classidList;
	private int photoCount;
	private boolean selected;
	
	/**
	 * @return the albumid
	 */
	public int getAlbumid() {
		return albumid;
	}
	/**
	 * @param albumid the albumid to set
	 */
	public void setAlbumid(int albumid) {
		this.albumid = albumid;
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
	 * @return the albumName
	 */
	public String getAlbumName() {
		return albumName;
	}
	/**
	 * @param albumName the albumName to set
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
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
	 * @return the photoList
	 */
	public List<Photo> getPhotoList() {
		return photoList;
	}
	/**
	 * @param photoList the photoList to set
	 */
	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}
	/**
	 * @return the classidList
	 */
	public List<Integer> getClassidList() {
		return classidList;
	}
	/**
	 * @param classidList the classidList to set
	 */
	public void setClassidList(List<Integer> classidList) {
		this.classidList = classidList;
	}
	/**
	 * @return the photoCount
	 */
	public int getPhotoCount() {
		return photoCount;
	}
	/**
	 * @param photoCount the photoCount to set
	 */
	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
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
