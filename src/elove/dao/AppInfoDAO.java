package elove.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import elove.AppInfo;

/**
 * @Title: AppInfoDAO
 * @Description: DAO for appInfo model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月25日
 */
public class AppInfoDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	
	//query
	/*public AppInfo getAppInfoBySid(int sid){
		String SQL = "SELECT A.";
	}*/
}
