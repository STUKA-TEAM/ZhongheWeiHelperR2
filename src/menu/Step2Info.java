package menu;

import java.util.List;

/**
 * @Title: Step2Info
 * @Description: 向导第二步信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月18日
 */
public class Step2Info {
	private List<MenuNode> nodeList;
	private boolean success;

	/**
	 * @return the nodeList
	 */
	public List<MenuNode> getNodeList() {
		return nodeList;
	}

	/**
	 * @param nodeList the nodeList to set
	 */
	public void setNodeList(List<MenuNode> nodeList) {
		this.nodeList = nodeList;
	}

	/**
	 * @return the success
	 */
	public boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
