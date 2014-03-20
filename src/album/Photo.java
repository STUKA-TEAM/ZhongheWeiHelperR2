package album;

/**
 * @Title: Photo
 * @Description: photo信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月17日
 */
public class Photo {
	private String imagePath;
	private String imageDesc;
	
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	/**
	 * @return the imageDesc
	 */
	public String getImageDesc() {
		return imageDesc;
	}
	/**
	 * @param imageDesc the imageDesc to set
	 */
	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}
}
