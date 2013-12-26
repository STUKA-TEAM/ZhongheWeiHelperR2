package elove.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
	/**
	 * @Description: 根据用户id获取管理的app信息
	 * @param sid
	 * @return
	 */
	public List<AppInfo> getAppInfoBySid(int sid){
		String SQL = "SELECT A.appid, A.wechatToken, A.wechatName, A.wechatOriginalId, "
				+ "A.wechatNumber, A.address, A.industry FROM storeuser_application S, "
				+ "application A WHERE S.appid = A.appid AND S.sid = ?";
		List<AppInfo> appInfoList = null;
		try {
			appInfoList = jdbcTemplate.query(SQL, new Object[]{sid}, new AppInfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        return appInfoList;
	}
	
	private static final class AppInfoMapper implements RowMapper<AppInfo>{
		@Override
		public AppInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			AppInfo appInfo = new AppInfo();
			appInfo.setAppid(rs.getString("A.appid"));
			appInfo.setWechatToken(rs.getString("A.wechatToken"));
			appInfo.setWechatName(rs.getString("A.wechatName"));
			appInfo.setWechatOriginalId(rs.getString("A.wechatOriginalId"));
			appInfo.setWechatNumber(rs.getString("A.wechatNumber"));
			appInfo.setAddress(rs.getString("A.address"));
			appInfo.setIndustry(rs.getString("A.industry"));
			return appInfo;
		}		
	}
}
