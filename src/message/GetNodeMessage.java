package message;

import java.util.List;

import menu.MenuNode;

/**
 * @Title: GetNodeMessage
 * @Description: 获取节点列表返回信息
 * @Company: Tuka
 * @author ben
 * @date 2014年3月18日
 */
public class GetNodeMessage extends ResponseMessage {
	private List<MenuNode> nodeList;

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
	
	
}
