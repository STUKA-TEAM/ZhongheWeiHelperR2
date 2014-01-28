package elove.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import elove.ThemeInfo;

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
	 * @title: getThemeInfos
	 * @description: 获取主题基本信息列表
	 * @return
	 */
	public List<ThemeInfo> getThemeInfos(){
		String SQL = "SELECT id, themeName FROM elove_theme";
		List<ThemeInfo> themeList = null;
		
		try {
			themeList = jdbcTemplate.query(SQL, new ThemeInfoMapper());
		} catch (Exception e) {
			themeList = new ArrayList<ThemeInfo>();
			System.out.println(e.getMessage());
		}
		return themeList;
	}
	
	/**
	 * @title: getThemeName
	 * @description: 根据主题id获取主题name
	 * @param themeid
	 * @return
	 */
	public String getThemeName(int themeid){
		String SQL = "SELECT themeName FROM elove_theme WHERE id = ?";
		String themeName = null;
		
		try {
			themeName = jdbcTemplate.queryForObject(SQL, new Object[] {themeid}, new ThemeNameMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return themeName;
	}
	
	private static final class ThemeNameMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String themeName = rs.getString("themeName");
			return themeName;
		}		
	}
	
	private static final class ThemeInfoMapper implements RowMapper<ThemeInfo>{
		@Override
		public ThemeInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			ThemeInfo themeInfo = new ThemeInfo();
			themeInfo.setThemeid(rs.getInt("id"));
			themeInfo.setThemeName(rs.getString("themeName"));
			return themeInfo;
		}		
	}
}
