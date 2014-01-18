package register.dao;

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

import register.AppInfo;

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
			
			result = insertAppAuthRelation(appInfo.getAuthNameList(), appInfo.getAppid());
			if (result == 0) {
				return -2;
			}
			
			result = insertConsumeRecord(appInfo.getAppid());
			if (result == 0) {
				return -3;
			}
			
			return result;
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
	private int insertUserAppRelation(int sid, String appid){
		String SQL = "INSERT INTO storeuser_application VALUES (default, ?, ?)";
		int result = 0;
		
		result = jdbcTemplate.update(SQL, sid, appid);
		
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: insertAppAuthRelation
	 * @description: 插入应用、权限关系对应
	 * @param authNameList
	 * @param appid
	 * @return
	 */
	private int insertAppAuthRelation(List<String> authNameList, String appid){
		String SQL = "INSERT INTO application_authority VALUES (default, ?, ?)";
		int result = 0;

		for (int i = 0; i < authNameList.size(); i++) {
			String authName = authNameList.get(i);
			Integer authid = getAuthid(authName);
			if (authid == null) {
				return 0;
			}
			int id = authid.intValue();
			
			result = jdbcTemplate.update(SQL, appid, id);		
			if (result <= 0) {
				return 0;
			}
		}
		
		return result;
	}
	
	/**
	 * @title: insertConsumeRecord
	 * @description: 插入应用消费初始记录
	 * @param appid
	 * @return
	 */
	private int insertConsumeRecord(String appid){
		String SQL = "INSERT INTO elove_consume_record VALUES (default, ?, 0)";
		int result = jdbcTemplate.update(SQL, appid);
		return result <= 0 ? 0 : result;
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
	 * @title: getBasicAppInfo
	 * @description: 获取基本的应用信息
	 * @param sid
	 * @return
	 */
	public List<AppInfo> getBasicAppInfo(int sid){
		String SQL = "SELECT A.appid, A.wechatName FROM storeuser_application S, "
				+ "application A WHERE S.appid = A.appid AND S.sid = ?";
		List<AppInfo> appInfoList = null;
		try {
			appInfoList = jdbcTemplate.query(SQL, new Object[]{sid}, new BasicAppInfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        return appInfoList;
	}
	
	private static final class BasicAppInfoMapper implements RowMapper<AppInfo>{
		@Override
		public AppInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			AppInfo appInfo = new AppInfo();
			appInfo.setAppid(rs.getString("A.appid"));
			appInfo.setWechatName(rs.getString("A.wechatName"));
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
	
	/**
	 * @title: getAppNumByAppid
	 * @description: 查询某个appid是否存在 
	 * @param appid
	 * @return
	 */
	public int getAppNumByAppid(String appid){
		String SQL = "SELECT COUNT(*) FROM application WHERE appid = ?";
		int count = 0;
		
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, appid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	/**
	 * @title: checkAppExistsByUser
	 * @description: 查看某用户下是否存在某个appid
	 * @param sid
	 * @param appid
	 * @return
	 */
	public int checkAppExistsByUser(int sid, String appid){
		String SQL = "SELECT COUNT(*) FROM storeuser_application WHERE sid = ? AND appid = ?";
		int count = 0;
		
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, sid, appid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	/**
	 * @title: getAuthid
	 * @description: 查询权限名字对应的id
	 * @param authName
	 * @return
	 */
	private Integer getAuthid(String authName){
		String SQL = "SELECT authid FROM authority WHERE authName = ?";
		Integer authid = null;
		try {
			authid = jdbcTemplate.queryForObject(SQL, new Object[]{authName}, new AuthidMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return authid;
	}
	
	private static final class AuthidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer authid = rs.getInt("authid");
			return authid;
		}		
	}
	
	/**
	 * @title: getAuthPinyinList
	 * @description: 根据appid来获取对应权限拼音列表
	 * @param appid
	 * @return
	 */
	public List<String> getAuthPinyinList(String appid){
		String SQL = "SELECT A.authPinyin FROM authority A, application_authority B"
				+ " WHERE A.authid = B.authid AND B.appid = ?";
		List<String> authPinyinList = null;
		try {
			authPinyinList = jdbcTemplate.query(SQL, new Object[]{appid}, new AuthPinyinMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return authPinyinList;
	}
	
	private static final class AuthPinyinMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String authPinyin = rs.getString("A.authPinyin");
			return authPinyin;
		}		
	}
	
	//delete
	/**
	 * @title: deleteAppInfo
	 * @description: 删除app应用信息记录
	 * @param appid
	 * @return
	 */
	public int deleteAppInfo(String appid){
		String SQL = "DELETE FROM application WHERE appid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{appid});
		return effected;
	}
	
	/**
	 * @title: deleteUserAppRelation
	 * @description: 删除用户app使用对应记录
	 * @param appid
	 * @return
	 */
	public int deleteUserAppRelation(String appid){
		String SQL = "DELETE FROM storeuser_application WHERE appid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{appid});
		return effected;
	}
	
	/**
	 * @title: deleteAppAuthRelation
	 * @description: 删除应用与权限对应关系
	 * @param appid
	 * @return
	 */
	public int deleteAppAuthRelation(String appid){
		String SQL = "DELETE FROM application_authority WHERE appid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{appid});
		return effected;
	}
	
}
