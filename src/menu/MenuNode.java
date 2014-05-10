package menu;

/**
 * @Title: MenuNode
 * @Description: 菜单节点信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月18日
 */
public class MenuNode {
	private int nodeid;
	private int fatherid;
	private String UUID;
	private String fatherUUID;
	private String nodeName;
	private String nodeLink;
	private String nodeKey;
	private int nodeType;
	private String type;
	
	/**
	 * @return the nodeid
	 */
	public int getNodeid() {
		return nodeid;
	}
	/**
	 * @param nodeid the nodeid to set
	 */
	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}
	/**
	 * @return the fatherid
	 */
	public int getFatherid() {
		return fatherid;
	}
	/**
	 * @param fatherid the fatherid to set
	 */
	public void setFatherid(int fatherid) {
		this.fatherid = fatherid;
	}
	/**
	 * @return the uUID
	 */
	public String getUUID() {
		return UUID;
	}
	/**
	 * @param uUID the uUID to set
	 */
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	/**
	 * @return the fatherUUID
	 */
	public String getFatherUUID() {
		return fatherUUID;
	}
	/**
	 * @param fatherUUID the fatherUUID to set
	 */
	public void setFatherUUID(String fatherUUID) {
		this.fatherUUID = fatherUUID;
	}
	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * @return the nodeLink
	 */
	public String getNodeLink() {
		return nodeLink;
	}
	/**
	 * @param nodeLink the nodeLink to set
	 */
	public void setNodeLink(String nodeLink) {
		this.nodeLink = nodeLink;
	}
	/**
	 * @return the nodeKey
	 */
	public String getNodeKey() {
		return nodeKey;
	}
	/**
	 * @param nodeKey the nodeKey to set
	 */
	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}
	/**
	 * @return the nodeType
	 */
	public int getNodeType() {
		return nodeType;
	}
	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
