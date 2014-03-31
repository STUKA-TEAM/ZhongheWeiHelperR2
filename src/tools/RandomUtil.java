package tools;

import java.util.Random;
import java.util.UUID;

/**
 * @Title: RandomUtil
 * @Description: 提供常见的随机字符串生成方法
 * @Company: Tuka
 * @author ben
 * @date 2014年3月21日
 */
public class RandomUtil {
    public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * @Title: generateMixedString
	 * @Description: 返回一个定长的随机字符串(包含大小写字母、数字)
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static final String generateMixedString(int length){
		StringBuffer sb = new StringBuffer();
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALL_CHAR.charAt(rand.nextInt(62)));
		}	
		return sb.toString();
	}
	
	/**
	 * @title generateUUID
	 * @description 获取UUID串
	 * @return
	 */
	public static final String generateUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
