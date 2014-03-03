package register.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import register.Welcome;
import register.WelcomeContent;

/**
 * @Title: WelcomeDAO
 * @Description: DAO for welcome model
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月18日
 */
public class WelcomeDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title: insertWelcome
	 * @description: 插入欢迎页配置信息
	 * @param welcome
	 * @return
	 */
	public int insertWelcome(Welcome welcome){
		int result = 0;
		String appid = welcome.getAppid();
		result = updateWelcomeType(appid, welcome.getType());
		if (result == 0) {
			return 0;
		}
		
		List<WelcomeContent> contents = welcome.getContents();
		for (int i = 0; i < contents.size(); i++) {
			result = insertWelcomeContent(appid, contents.get(i));
			if (result == 0) {
				return 0;
			}
		}
		
		return result;
	}
	
	/**
	 * @title: insertWelcomeContent
	 * @description: 插入欢迎页的内容
	 * @param appid
	 * @param wContent
	 * @return
	 */
	private int insertWelcomeContent(String appid, WelcomeContent wContent){
		String SQL = "INSERT INTO welcome (id, appid, content, coverPic, link) VALUES (default, ?, ?, ?, ?)";
		int effected = jdbcTemplate.update(SQL, appid, wContent.getContent(), 
				wContent.getCoverPic(), wContent.getLink());
		if (effected > 0) {
			String coverPic = wContent.getCoverPic();
			if (coverPic != null && !coverPic.equals("")) {
				effected = deleteImageTempRecord(coverPic);
			}
		}
		
		return effected <= 0 ? 0 : effected;
	}
	
	/**
	 * @title: insertImageTempRecord
	 * @description: 将要删除图片的信息存入临时表
	 * @param imagePath
	 * @param current
	 * @return
	 */
	private int insertImageTempRecord(String imagePath, Timestamp current){
		String SQL = "INSERT INTO image_temp_record (id, imagePath, createDate) VALUES (default, ?, ?)";
		int result = jdbcTemplate.update(SQL, imagePath, current);
		return result <= 0 ? 0 : result;
	}
	
	//delete
	/**
	 * @title: deleteWelcomeContent
	 * @description: 删除欢迎页内容
	 * @param appid
	 * @return
	 */
	public int deleteWelcomeContent(String appid){
		String SQL = "DELETE FROM welcome WHERE appid = ?";
		List<String> coverPics = getCoverPics(appid);
		
		int effected = jdbcTemplate.update(SQL, appid);
		if (effected > 0) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			for (int i = 0; i < coverPics.size(); i++) {
				String coverPic = coverPics.get(i);
				if (coverPic != null && !coverPic.equals("")) {
					insertImageTempRecord(coverPic, current);
				}
			}
		}
		
		return effected <= 0 ? 0 : effected;
	}
	
	/**
	 * @title: deleteImageTempRecord
	 * @description: 删除图片在临时表中记录
	 * @param imagePath
	 * @return
	 */
	private int deleteImageTempRecord(String imagePath){
		String SQL = "DELETE FROM image_temp_record WHERE imagePath = ?";
		int effected = jdbcTemplate.update(SQL, imagePath);
		return effected;
	}
	
	//update
	/**
	 * @title: updateWelcome
	 * @description: 更新欢迎页配置信息
	 * @param welcome
	 * @return
	 */
	public int updateWelcome(Welcome welcome){
		int result = 0;
		String appid = welcome.getAppid();
		result = updateWelcomeType(appid, welcome.getType());
		if (result == 0) {
			return 0;
		}
		
		deleteWelcomeContent(appid);
		List<WelcomeContent> contents = welcome.getContents();
		for (int i = 0; i < contents.size(); i++) {
			result = insertWelcomeContent(appid, contents.get(i));
			if (result == 0) {
				return 0;
			}
		}
		
		return result;
	}
	
	/**
	 * @title: updateWelcomeType
	 * @description: 根据appid更新欢迎页类型
	 * @param appid
	 * @param type
	 * @return
	 */
	private int updateWelcomeType(String appid, String type){
		String SQL = "UPDATE application SET welcomeType = ? WHERE appid = ?";		
		int effected = jdbcTemplate.update(SQL, type, appid);
		return effected <= 0 ? 0 : effected;
	}
	
	//query
	/**
	 * @title: getWelcome
	 * @description: 获取欢迎页的配置信息
	 * @param appid
	 * @return
	 */
	public Welcome getWelcome(String appid){
		Welcome welcome = new Welcome();
		welcome.setAppid(appid);
		welcome.setType(getWelcomeType(appid));
		welcome.setContents(getWelcomeContents(appid));
		return welcome;
	}
	
	/**
	 * @title: getWelcomeContents
	 * @description: 根据appid获取对应的欢迎页内容
	 * @param appid
	 * @return
	 */
	public List<WelcomeContent> getWelcomeContents(String appid){
		String SQL = "SELECT content, coverPic, link FROM welcome WHERE appid = ?";
		List<WelcomeContent> contents = null;
		
		try {
			contents = jdbcTemplate.query(SQL, new Object[]{appid}, new WelcomeContentMapper());
		} catch (Exception e) {
			contents = new ArrayList<WelcomeContent>();
			System.out.println(e.getMessage());
		}
		return contents;
	}
	
	private static final class WelcomeContentMapper implements RowMapper<WelcomeContent>{
		@Override
		public WelcomeContent mapRow(ResultSet rs, int arg1)
				throws SQLException {
			WelcomeContent wContent = new WelcomeContent();
			wContent.setContent(rs.getString("content"));
			wContent.setCoverPic(rs.getString("coverPic"));
			wContent.setLink(rs.getString("link"));
			return wContent;
		}		
	}
	
	/**
	 * @title: getCoverPics
	 * @description: 根据appid获取对应的欢迎页图片信息
	 * @param appid
	 * @return
	 */
	public List<String> getCoverPics(String appid){
		String SQL = "SELECT coverPic FROM welcome WHERE appid = ?";
		List<String> coverPics = null;
		
		try {
			coverPics = jdbcTemplate.query(SQL, new Object[]{appid}, new CoverPicsMapper());
		} catch (Exception e) {
			coverPics = new ArrayList<String>();
			System.out.println(e.getMessage());
		}
		return coverPics;
	}
	
	private static final class CoverPicsMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1)
				throws SQLException {
			String coverPic = rs.getString("coverPic");
			return coverPic;
		}		
	}
	
	/**
	 * @title: getWelcomeType
	 * @description: 根据appid获取欢迎页内容的类型
	 * @param appid
	 * @return
	 */
	public String getWelcomeType(String appid){
		String SQL = "SELECT welcomeType FROM application WHERE appid = ?";
		String type = null;
		
		try {
			type = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, new WelcomeTypeMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return type;
	}
	
	private static final class WelcomeTypeMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String type = rs.getString("welcomeType");
			return type;
		}	
	}
}
