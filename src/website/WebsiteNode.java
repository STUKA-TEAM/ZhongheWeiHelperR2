package website;

/**
 * @Title: WebsiteNode
 * @Description: 微官网节点信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月24日
 */
public class WebsiteNode {
	private int nodeid;
	private int fatherid;
	private String fatherUUID;
	private String UUID;
	private String nodeName;
	private String nodePic;
	private String childrenType;
	private int articleid;
	private int classid;
	private int websiteid;
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
	 * @return the nodePic
	 */
	public String getNodePic() {
		return nodePic;
	}
	/**
	 * @param nodePic the nodePic to set
	 */
	public void setNodePic(String nodePic) {
		this.nodePic = nodePic;
	}
	/**
	 * @return the childrenType
	 */
	public String getChildrenType() {
		return childrenType;
	}
	/**
	 * @param childrenType the childrenType to set
	 */
	public void setChildrenType(String childrenType) {
		this.childrenType = childrenType;
	}
	/**
	 * @return the articleid
	 */
	public int getArticleid() {
		return articleid;
	}
	/**
	 * @param articleid the articleid to set
	 */
	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}
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
	 * @return the websiteid
	 */
	public int getWebsiteid() {
		return websiteid;
	}
	/**
	 * @param websiteid the websiteid to set
	 */
	public void setWebsiteid(int websiteid) {
		this.websiteid = websiteid;
	}
}
