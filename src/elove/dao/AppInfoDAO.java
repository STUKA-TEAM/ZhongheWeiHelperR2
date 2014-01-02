package elove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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
	/**
	 * @title: insertAppInfo
	 * @description: 插入app创建时的所有必要信息
	 * @param appInfo
	 * @return
	 */
	public int insertAppInfo(final AppInfo appInfo){
		final String SQL = "INSERT INTO application VALUES (default, ?, ?, ?, ?, ?, ?, ?)";
		int result = 0;
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, appInfo.getAppid());
		        ps.setString(2, appInfo.getWechatToken());
		        ps.setString(3, appInfo.getWechatName());
		        ps.setString(4, appInfo.getWechatOriginalId());
		        ps.setString(5, appInfo.getWechatNumber());
		        ps.setString(6, appInfo.getAddress());
		        ps.setString(7, appInfo.getIndustry());
		        return ps;
		    }
		}, kHolder);
		
		if (result > 0) {
			result = insertUserAppRelation(appInfo.getSid(), appInfo.getAppid());
			if (result == 0 ) {
				return -1;
			}
			
			
		}else {
			return 0;
		}
	}
	
	/**
	 * @title： insertUserAppRelation
	 * @description: 插入用户与应用的对应记录
	 * @param sid
	 * @param appid
	 * @return
	 */
	public int insertUserAppRelation(final int sid, final String appid){
		final String SQL = "INSERT INTO storeuser_application VALUES (default, ?, ?)";
		int result = 0;
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setInt(1, sid);
		        ps.setString(2, appid);
		        return ps;
		    }
		}, kHolder);
		
		return result <= 0 ? 0 : result;
	}
	
	public int insertAppAuthRelation(final List<String> authNameList){
		
	}
	
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
	
	/**
	 * @title: getAppNumBySid
	 * @description: 查询一个用户账号下已创建的app数量
	 * @param sid
	 * @return
	 */
	public int getAppNumBySid(int sid){
		String SQL = "SELECT COUNT(*) FROM storeuser_application WHERE sid = ?";
		int count = 3;
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, sid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;		
	}
	
	//delete
	public int deleteAppInfo(String appid){
		return 0;
	}
}
