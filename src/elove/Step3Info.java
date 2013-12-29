package elove;

import java.util.List;

/**
 * @Title: Step3Info
 * @Description: 向导第三步信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class Step3Info {
	private List<String> dressImagePath;
	private List<String> dressVideoPath;
	
	/**
	 * @return the dressImagePath
	 */
	public List<String> getDressImagePath() {
		return dressImagePath;
	}
	/**
	 * @param dressImagePath the dressImagePath to set
	 */
	public void setDressImagePath(List<String> dressImagePath) {
		this.dressImagePath = dressImagePath;
	}
	/**
	 * @return the dressVideoPath
	 */
	public List<String> getDressVideoPath() {
		return dressVideoPath;
	}
	/**
	 * @param dressVideoPath the dressVideoPath to set
	 */
	public void setDressVideoPath(List<String> dressVideoPath) {
		this.dressVideoPath = dressVideoPath;
	}
}
