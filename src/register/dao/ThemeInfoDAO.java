package register.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import register.ThemeInfo;

/**
 * @Title: ThemeInfoDAO
 * @Description: DAO for themeInfo model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年1月10日
 */
public class ThemeInfoDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//query
	/**
	 * @title: getEloveThemeInfos
	 * @description: 获取elove主题基本信息列表
	 * @return
	 */
	public List<ThemeInfo> getEloveThemeInfos(){
		String SQL = "SELECT id, themeName FROM elove_theme";
		List<ThemeInfo> themeList = null;
		
		try {
			themeList = jdbcTemplate.query(SQL, new EloveThemeInfoMapper());
		} catch (Exception e) {
			themeList = new ArrayList<ThemeInfo>();
			System.out.println(e.getMessage());
		}
		return themeList;
	}
	
	private static final class EloveThemeInfoMapper implements RowMapper<ThemeInfo>{
		@Override
		public ThemeInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			ThemeInfo themeInfo = new ThemeInfo();
			themeInfo.setThemeid(rs.getInt("id"));
			themeInfo.setThemeName(rs.getString("themeName"));
			return themeInfo;
		}		
	}
	
	/**
	 * @title: getEloveThemeName
	 * @description: 根据elove主题id获取主题name
	 * @param themeid
	 * @return
	 */
	public String getEloveThemeName(int themeid){
		String SQL = "SELECT themeName FROM elove_theme WHERE id = ?";
		String themeName = null;
		
		try {
			themeName = jdbcTemplate.queryForObject(SQL, new Object[] {themeid}, new EloveThemeNameMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return themeName;
	}
	
	private static final class EloveThemeNameMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String themeName = rs.getString("themeName");
			return themeName;
		}		
	}
	
	/**
	 * @title: getWebsiteThemeInfos
	 * @description: 获取website主题基本信息列表
	 * @return
	 */
	public List<ThemeInfo> getWebsiteThemeInfos(){
		String SQL = "SELECT themeid, themeName FROM website_theme";
		List<ThemeInfo> themeList = null;
		
		try {
			themeList = jdbcTemplate.query(SQL, new WebsiteThemeInfoMapper());
		} catch (Exception e) {
			themeList = new ArrayList<ThemeInfo>();
			System.out.println(e.getMessage());
		}
		return themeList;
	}
	
	private static final class WebsiteThemeInfoMapper implements RowMapper<ThemeInfo>{
		@Override
		public ThemeInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			ThemeInfo themeInfo = new ThemeInfo();
			themeInfo.setThemeid(rs.getInt("themeid"));
			themeInfo.setThemeName(rs.getString("themeName"));
			return themeInfo;
		}		
	}
}
