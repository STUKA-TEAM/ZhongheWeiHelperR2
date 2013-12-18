package weixinmessage.response;

import java.util.List;

public class NewsMessageToResponse {
private String ToUserName;
private String FromUserName;
private String CreateTime;
private String MsgType;
private String ArticleCount;
private List<NewsItemToResponse> Articles;
public String getToUserName() {
	return ToUserName;
}
public void setToUserName(String toUserName) {
	ToUserName = toUserName;
}
public String getFromUserName() {
	return FromUserName;
}
public void setFromUserName(String fromUserName) {
	FromUserName = fromUserName;
}
public String getCreateTime() {
	return CreateTime;
}
public void setCreateTime(String createTime) {
	CreateTime = createTime;
}
public String getMsgType() {
	return MsgType;
}
public void setMsgType(String msgType) {
	MsgType = msgType;
}
public String getArticleCount() {
	return ArticleCount;
}
public void setArticleCount(String articleCount) {
	ArticleCount = articleCount;
}
public List<NewsItemToResponse> getArticles() {
	return Articles;
}
public void setArticles(List<NewsItemToResponse> articles) {
	Articles = articles;
}

}
