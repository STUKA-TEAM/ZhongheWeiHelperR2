package weixintools;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import weixinmessage.response.NewsItemToResponse;
import weixinmessage.response.NewsMessageToResponse;
import weixinmessage.response.TextMessageToResponse;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class WeixinMessageUtil {
public static Map<String, String> parseXml(HttpServletRequest request){
	Map<String, String> map = new HashMap<String, String>();
	SAXReader reader = new SAXReader();
	try {
		InputStream inputStream = request.getInputStream();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementlList = root.elements();
		for (Element element : elementlList) {
			map.put(element.getName(), element.getText());
		}
		
		inputStream.close();
		inputStream = null;
		return map;
	} catch (Exception e) {
		return map;
	}
	
}

public static String textMessageToXmlForResponse(Map<String, String> xmlMap, String content){
	TextMessageToResponse tResponse = new TextMessageToResponse();
	tResponse.setFromUserName(xmlMap.get("ToUserName"));
	tResponse.setToUserName(xmlMap.get("FromUserName"));
	tResponse.setCreateTime(xmlMap.get("CreateTime"));
	tResponse.setMsgType(WeiXinConstant.MSG_TYPE_TEXT_TO_RESP);
	tResponse.setContent(content);
	xstream.alias("xml", tResponse.getClass());
	return xstream.toXML(tResponse);
}

public static String newsMessageToXmlForResponse(Map<String, String> xmlMap, List<NewsItemToResponse> articles){
	NewsMessageToResponse nResponse = new NewsMessageToResponse();
	nResponse.setFromUserName(xmlMap.get("ToUserName"));
	nResponse.setToUserName(xmlMap.get("FromUserName"));
	nResponse.setCreateTime(xmlMap.get("CreateTime"));
	nResponse.setMsgType(WeiXinConstant.MSG_TYPE_NEWS_TO_RESP);
	nResponse.setArticleCount(Integer.toString(articles.size()));
	nResponse.setArticles(articles);
	
	xstream.alias("xml", nResponse.getClass());
	xstream.alias("item", new NewsItemToResponse().getClass());
	return xstream.toXML(nResponse);
}


private static XStream xstream = new XStream(new XppDriver() {  
    public HierarchicalStreamWriter createWriter(Writer out) {  
        return new PrettyPrintWriter(out) {  
            // 对所有xml节点的转换都增加CDATA标记  
            boolean cdata = true;  
  
            @SuppressWarnings("unchecked")  
            public void startNode(String name, Class clazz) {  
                super.startNode(name, clazz);  
            }  
  
            protected void writeText(QuickWriter writer, String text) {  
                if (cdata) {  
                    writer.write("<![CDATA[");  
                    writer.write(text);  
                    writer.write("]]>");  
                } else {  
                    writer.write(text);  
                }  
            }  
        };  
    }  
});
}
