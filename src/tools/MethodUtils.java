package tools;

import java.io.IOException;
import java.io.InputStream;
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
}
