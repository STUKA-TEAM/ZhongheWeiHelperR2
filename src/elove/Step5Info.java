package elove;

import java.util.List;

/**
 * @Title: Step5Info
 * @Description: 向导第五步信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class Step5Info {
	private List<String> recordImagePath;
	private String recordVideoPath;
	
	/**
	 * @return the recordImagePath
	 */
	public List<String> getRecordImagePath() {
		return recordImagePath;
	}
	/**
	 * @param recordImagePath the recordImagePath to set
	 */
	public void setRecordImagePath(List<String> recordImagePath) {
		this.recordImagePath = recordImagePath;
	}
	/**
	 * @return the recordVideoPath
	 */
	public String getRecordVideoPath() {
		return recordVideoPath;
	}
	/**
	 * @param recordVideoPath the recordVideoPath to set
	 */
	public void setRecordVideoPath(String recordVideoPath) {
		this.recordVideoPath = recordVideoPath;
	}
}
