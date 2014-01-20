package tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MethodUtils {
public static String getApplicationPath(){
	String applicationPath = "";	
	InputStream inputStream = MethodUtils.class.getResourceAsStream("/environment.properties");
	Properties properties = new Properties();
	try {
		properties.load(inputStream);
		applicationPath = (String) properties.get("applicationPath");
	} catch (IOException e) {
		e.printStackTrace();
	}
	return applicationPath;
}

public static String getImageHost(){
	String imageHost = "";	
	InputStream inputStream = MethodUtils.class.getResourceAsStream("/environment.properties");
	Properties properties = new Properties();
	try {
		properties.load(inputStream);
		imageHost = (String) properties.get("imageHost");
	} catch (IOException e) {
		e.printStackTrace();
	}
	return imageHost;
}

public static List<Integer> getEloveDemoIdList(){
	String idString = "";	
	InputStream inputStream = MethodUtils.class.getResourceAsStream("/elovedemo.properties");
	Properties properties = new Properties();
	ArrayList<Integer> idList = new ArrayList<Integer>();
	try {
		properties.load(inputStream);
		idString = (String) properties.get("elovedemoid");
		String[] idStringList = idString.split(",");
		for (String string : idStringList) {
			idList.add(Integer.parseInt(string));
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	return idList;
}
}
