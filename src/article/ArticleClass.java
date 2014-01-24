package article;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title: ArticleClass
 * @Description: articleclass信息
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月22日
 */
public class ArticleClass {
	private int classid;
	private String appid;
	private String className;
	private Timestamp createTime;
	private List<Integer> articleidList;
	private int articleCount;
	
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
	 * @return the articleidList
	 */
	public List<Integer> getArticleidList() {
		return articleidList;
	}
	/**
	 * @param articleidList the articleidList to set
	 */
	public void setArticleidList(List<Integer> articleidList) {
		this.articleidList = articleidList;
	}
	/**
	 * @return the articleCount
	 */
	public int getArticleCount() {
		return articleCount;
	}
	/**
	 * @param articleCount the articleCount to set
	 */
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
}
