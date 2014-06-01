package article;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: Article
 * @Description: article信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月22日
 */
public class Article {
	private int articleid;
	private String appid;
	private int branchSid;
	private String title;
	private String coverPic;
	private Timestamp createTime;
	private String content;
	private List<Integer> classidList;
	private boolean selected;
	
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
	 * @return the branchSid
	 */
	public int getBranchSid() {
		return branchSid;
	}
	/**
	 * @param branchSid the branchSid to set
	 */
	public void setBranchSid(int branchSid) {
		this.branchSid = branchSid;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
