package elove;

import java.util.List;

/**
 * @Title: Step2Info
 * @Description: 向导第二步信息
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class Step2Info {
	private String xinLang;
	private String xinNiang;
    private List<String> storyImagePath;
    private String storyTextImagePath;
    
	/**
	 * @return the xinLang
	 */
	public String getXinLang() {
		return xinLang;
	}
	/**
	 * @param xinLang the xinLang to set
	 */
	public void setXinLang(String xinLang) {
		this.xinLang = xinLang;
	}
	/**
	 * @return the xinNiang
	 */
	public String getXinNiang() {
		return xinNiang;
	}
	/**
	 * @param xinNiang the xinNiang to set
	 */
	public void setXinNiang(String xinNiang) {
		this.xinNiang = xinNiang;
	}
	/**
	 * @return the storyImagePath
	 */
	public List<String> getStoryImagePath() {
		return storyImagePath;
	}
	/**
	 * @param storyImagePath the storyImagePath to set
	 */
	public void setStoryImagePath(List<String> storyImagePath) {
		this.storyImagePath = storyImagePath;
	}
	/**
	 * @return the storyTextImagePath
	 */
	public String getStoryTextImagePath() {
		return storyTextImagePath;
	}
	/**
	 * @param storyTextImagePath the storyTextImagePath to set
	 */
	public void setStoryTextImagePath(String storyTextImagePath) {
		this.storyTextImagePath = storyTextImagePath;
	}
}
