package register.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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
		String SQL = "INSERT INTO welcome VALUES (default, ?, ?, ?, ?)";
		int effected = jdbcTemplate.update(SQL, appid, wContent.getContent(), 
				wContent.getCoverPic(), wContent.getLink());
		return effected <= 0 ? 0 : effected;
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
		int effected = jdbcTemplate.update(SQL, appid);
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
