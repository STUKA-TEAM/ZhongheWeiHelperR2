package register.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import register.AppInfo;
import register.Authority;

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
		final String SQL = "INSERT INTO application (id, appid, wechatToken, "
				+ "wechatName, wechatOriginalId, wechatNumber, address, industry) "
				+ "VALUES (default, ?, ?, ?, ?, ?, ?, ?)";
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
			
			result = insertAppAuthRelation(appInfo.getAuthidList(), appInfo.getAppid());
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
		String SQL = "INSERT INTO storeuser_application (id, sid, appid) VALUES (default, ?, ?)";
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
	private int insertAppAuthRelation(List<Integer> authidList, String appid){
		String SQL = "INSERT INTO application_authority (id, appid, authid) VALUES (default, ?, ?)";
		int result = 0;

		if (authidList == null) {
			return 0;
		}else {
			for (int i = 0; i < authidList.size(); i++) {
				int authid = authidList.get(i);		
				result = jdbcTemplate.update(SQL, appid, authid);		
				if (result <= 0) {
					return 0;
				}
			}
			return result;
		}
	}
	
	/**
	 * @title: insertConsumeRecord
	 * @description: 插入应用消费初始记录
	 * @param appid
	 * @return
	 */
	private int insertConsumeRecord(String appid){
		String SQL = "INSERT INTO elove_consume_record (id, appid, notPayNumber) VALUES (default, ?, 0)";
		int result = jdbcTemplate.update(SQL, appid);
		return result <= 0 ? 0 : result;
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
	
	//update
	/**
	 * @title updateFollowLink
	 * @description 更新该应用的关联链接
	 * @param appid
	 * @param followLink
	 * @return
	 */
	public int updateFollowLink(String appid, String followLink) {
		String SQL = "UPDATE application SET followLink = ? WHERE appid = ?";
		int result = jdbcTemplate.update(SQL, followLink, appid);
		return result <= 0 ? 0 : result;
	}
	
	//query
	/**
	 * @title getWechatNumberByAppid
	 * @description 根据appid查询微信号
	 * @param appid
	 * @return
	 */
	public String getWechatNumberByAppid(String appid){
		String wechatNumber = null;
		String SQL = "SELECT wechatNumber FROM application WHERE appid = ?";
		try {
			wechatNumber = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, new WechatNumberByAppidMapper());
		} catch (Exception e) {
			System.out.println("getWechatNumberByAppid: " + e.getMessage());
		}
		return wechatNumber;
	}
	
	private static final class WechatNumberByAppidMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String wechatNumber = rs.getString("wechatNumber");
			return wechatNumber;
		}	
	}
	
	/**
	 * @title getWechatNumberByLotteryWheel
	 * @description 根据wheelid查询微信号
	 * @param wheelid
	 * @return
	 */
	public String getWechatNumberByLotteryWheel(int wheelid){
		String wechatNumber = null;
		String SQL = "SELECT A.wechatNumber FROM application A, lotterywheel L WHERE A.appid = L.appid AND L.wheelid = ?";
		try {
			wechatNumber = jdbcTemplate.queryForObject(SQL, new Object[]{wheelid}, new WechatNumberMapper());
		} catch (Exception e) {
			System.out.println("getWechatNumberByLotteryWheel: " + e.getMessage());
		}
		return wechatNumber;
	}
	
	/**
	 * @title getWechatNumberByVote
	 * @description 根据voteid查询微信号
	 * @param voteid
	 * @return
	 */
	public String getWechatNumberByVote(int voteid){
		String wechatNumber = null;
		String SQL = "SELECT A.wechatNumber FROM application A, vote V WHERE A.appid = V.appid AND V.voteid = ?";
		try {
			wechatNumber = jdbcTemplate.queryForObject(SQL, new Object[]{voteid}, new WechatNumberMapper());
		} catch (Exception e) {
			System.out.println("getWechatNumberByVote: " + e.getMessage());
		}
		return wechatNumber;
	}
	
	/**
	 * @title getWechatNumberByElove
	 * @description 根据eloveid查询微信号
	 * @param eloveid
	 * @return
	 */
	public String getWechatNumberByElove(int eloveid){
		String wechatNumber = null;
		String SQL = "SELECT A.wechatNumber FROM application A, elove E WHERE A.appid = E.appid AND E.eloveid = ?";
		try {
			wechatNumber = jdbcTemplate.queryForObject(SQL, new Object[]{eloveid}, new WechatNumberMapper());
		} catch (Exception e) {
			System.out.println("getWechatNumberByElove: " + e.getMessage());
		}
		return wechatNumber;
	}
	
	/**
	 * @title getWechatNumberByWebsite
	 * @description 根据websiteid查询微信号
	 * @param websiteid
	 * @return
	 */
	public String getWechatNumberByWebsite(int websiteid){
		String wechatNumber = null;
		String SQL = "SELECT A.wechatNumber FROM application A, website W WHERE A.appid = W.appid AND W.websiteid = ?";
		try {
			wechatNumber = jdbcTemplate.queryForObject(SQL, new Object[]{websiteid}, new WechatNumberMapper());
		} catch (Exception e) {
			System.out.println("getWechatNumberByWebsite: " + e.getMessage());
		}
		return wechatNumber;
	}
	
	/**
	 * @title getWechatNumberByArticle
	 * @description 根据articleid查询微信号
	 * @param articleid
	 * @return
	 */
	public String getWechatNumberByArticle(int articleid){
		String wechatNumber = null;
		String SQL = "SELECT A.wechatNumber FROM application A, article B WHERE A.appid = B.appid AND B.articleid = ?";
		try {
			wechatNumber = jdbcTemplate.queryForObject(SQL, new Object[]{articleid}, new WechatNumberMapper());
		} catch (Exception e) {
			System.out.println("getWechatNumberByArticle: " + e.getMessage());
		}
		return wechatNumber;
	}
	
	/**
	 * @title getWechatNumberByAlbum
	 * @description 根据albumid查询微信号
	 * @param albumid
	 * @return
	 */
	public String getWechatNumberByAlbum(int albumid){
		String wechatNumber = null;
		String SQL = "SELECT A.wechatNumber FROM application A, album B WHERE A.appid = B.appid AND B.albumid = ?";
		try {
			wechatNumber = jdbcTemplate.queryForObject(SQL, new Object[]{albumid}, new WechatNumberMapper());
		} catch (Exception e) {
			System.out.println("getWechatNumberByAlbum: " + e.getMessage());
		}
		return wechatNumber;		
	}
	
	private static final class WechatNumberMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String wechatNumber = rs.getString("A.wechatNumber");
			return wechatNumber;
		}	
	}
	
	/**
	 * @title getFollowLink
	 * @description 根据appid查询该应用的关联链接
	 * @param appid
	 * @return
	 */
	public String getFollowLink(String appid) {
		String SQL = "SELECT A.followLink FROM application A WHERE A.appid = ?";
		String followLink = null;
		try {
			followLink = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, 
					new FollowLinkMapper());
		} catch (Exception e) {
			System.out.println("getFollowLink: " + e.getMessage());
		}
		return followLink;
	}
	
	/**
	 * @title getFollowLinkByArticle
	 * @description 根据文章id查询所在应用下的关联链接
	 * @param articleid
	 * @return
	 */
	public String getFollowLinkByArticle(int articleid) {
		String SQL = "SELECT A.followLink FROM application A, article B WHERE "
				+ "A.appid = B.appid AND B.articleid = ?";
		String followLink = null;
		try {
			followLink = jdbcTemplate.queryForObject(SQL, new Object[]{articleid}, 
					new FollowLinkMapper());
		} catch (Exception e) {
			System.out.println("getFollowLinkByArticle: " + e.getMessage());
		}
		return followLink;
	}
	
	private static final class FollowLinkMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String followLink = rs.getString("A.followLink");
			return followLink;
		}
	}
	
	/**
	 * @title getAppInfoBySid
	 * @Description 根据用户id获取管理的app信息 (appid, wechatToken, wechatName, wechatOriginalId
	 * , wechatNumber, address, industry)
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
			appInfoList = new ArrayList<AppInfo>();
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
	 * @description: 获取基本的应用信息 (appid, wechatName)
	 * @param sid 商家账户id
	 * @return
	 */
	public List<AppInfo> getBasicAppInfo(int sid){
		String SQL = "SELECT A.appid, A.wechatName FROM storeuser_application S, "
				+ "application A WHERE S.appid = A.appid AND S.sid = ?";
		List<AppInfo> appInfoList = null;
		try {
			appInfoList = jdbcTemplate.query(SQL, new Object[]{sid}, new BasicAppInfoMapper());
		} catch (Exception e) {
			appInfoList = new ArrayList<AppInfo>();
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
	 * @title getAppInfoForBranch
	 * @description 根据分店id查询所属商家的所有应用基本信息 (appid, wechatName)
	 * @param branchSid
	 * @return
	 */
	public List<AppInfo> getAppInfoForBranch(int branchSid) {
		List<AppInfo> infoList = null;
		Integer storeSid = getStoreSid(branchSid);
		if (storeSid != null) {
			infoList = getBasicAppInfo(storeSid);
		} else {
			infoList = new ArrayList<AppInfo>();
		}
		return infoList;
	}
	
	/**
	 * @title getStoreSid
	 * @description 根据分店id查询对应的商家id
	 * @param branchSid
	 * @return
	 */
	private Integer getStoreSid(int branchSid) {
		Integer storeSid = null;
		String SQL = "SELECT storeSid FROM branch_store WHERE branchSid = ?";
		try {
			storeSid = jdbcTemplate.queryForObject(SQL, new Object[]{branchSid}, new StoreSidMapper());
		} catch (Exception e) {
			System.out.println("getStoreSid: " + e.getMessage());
		}
		return storeSid;
	}
	
	private static final class StoreSidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer storeSid = rs.getInt("storeSid");
			return storeSid;
		}
	}
	
	/**
	 * @title: getAppidList
	 * @description: 根据sid获取appid列表
	 * @param sid
	 * @return
	 */
	public List<String> getAppidList(int sid){
		String SQL = "SELECT appid FROM storeuser_application WHERE sid = ?";
		List<String> appidList = null;
		try {
			appidList = jdbcTemplate.query(SQL, new Object[]{sid}, new AppidMapper());
		} catch (Exception e) {
			appidList = new ArrayList<String>();
			System.out.println(e.getMessage());
		}
		return appidList;
	}
	
	/**
	 * @title getAppidByWebsite
	 * @description 根据websiteid查询appid
	 * @param websiteid
	 * @return
	 */
	public String getAppidByWebsite(int websiteid){
		String SQL = "SELECT appid FROM website WHERE websiteid = ?";
		String appid = null;
		try {
			appid = jdbcTemplate.queryForObject(SQL, new Object[]{websiteid}, 
					new AppidMapper());
		} catch (Exception e) {
			System.out.println("getAppidByWebsite: " + e.getMessage());
		}
		return appid;
	}
	
	private static final class AppidMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String appid = rs.getString("appid");
			return appid;
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
			System.out.println("checkAppExistsByUser: " + e.getMessage());
		}
		return count;
	}
	
	private static final class AuthidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer authid = rs.getInt("authid");
			return authid;
		}		
	}
	
	/**
	 * @title: getAuthidList
	 * @description: 获取指定账户下的权限id列表 from customer_authority
	 * @param sid
	 * @return
	 */
	public List<Integer> getAuthidList(int sid){
		String SQL = "SELECT authid FROM customer_authority WHERE sid = ?";
		List<Integer> authidList = null;
		
		try {
			authidList = jdbcTemplate.query(SQL, new Object[]{sid}, new AuthidMapper());
		} catch (Exception e) {
			authidList = new ArrayList<Integer>();
			System.out.println(e.getMessage());
		}
		return authidList;
	}
	
	/**
	 * @title: getAuthPinyinList
	 * @description: 根据appid来获取对应权限拼音列表
	 * @param appid
	 * @return
	 */
	public Map<String, Boolean> getAuthPinyinList(String appid){
		String SQL = "SELECT A.authPinyin, A.authid FROM authority A, application_authority B"
				+ " WHERE A.authid = B.authid AND B.appid = ?";
		List<Authority> authorityList = null;
		
		try {
			authorityList = jdbcTemplate.query(SQL, new Object[]{appid}, new AuthorityMapper());
		} catch (Exception e) {
			authorityList = new ArrayList<Authority>();
			System.out.println(e.getMessage());
		}
		
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		Timestamp current = new Timestamp(System.currentTimeMillis());
		
		for (int i = 0; i < authorityList.size(); i++) {
			String authPinyin = authorityList.get(i).getAuthPinyin(); 
			Timestamp expiredTime = getAuthExpiredTime(appid, authorityList.get(i).getAuthid());
			result.put(authPinyin, expiredTime.after(current));
		}
		return result;
	}
	
	private static final class AuthorityMapper implements RowMapper<Authority>{
		@Override
		public Authority mapRow(ResultSet rs, int arg1) throws SQLException {
			Authority authority = new Authority();
			authority.setAuthid(rs.getInt("A.authid"));
			authority.setAuthPinyin(rs.getString("A.authPinyin"));
			return authority;
		}		
	}
	
	/**
	 * @title: getAppUpperLimit
	 * @description: 根据sid获取appUpperLimit
	 * @param sid
	 * @return
	 */
	public Integer getAppUpperLimit(int sid){
		String SQL = "SELECT appUpperLimit FROM customer_app_count WHERE sid = ?";
		Integer appUpperLimit = null;
		
		try {
			appUpperLimit = jdbcTemplate.queryForObject(SQL, new Object[]{sid}, new AppUpperLimitMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return appUpperLimit;
	}
	
	private static final class AppUpperLimitMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer appUpperLimit = rs.getInt("appUpperLimit");
			return appUpperLimit;
		}	
	}
	
	/**
	 * @title: getAuthExpiredTime
	 * @description: 获取权限过期时间
	 * @param appid
	 * @param authid
	 * @return
	 */
	private Timestamp getAuthExpiredTime(String appid, int authid){
		String SQL = "SELECT C.expiredTime FROM customer_authority C, storeuser_application S "
				+ "WHERE C.sid = S.sid AND S.appid = ? AND C.authid = ?";
		Timestamp expiredTime = null;
		
		try {
			expiredTime = jdbcTemplate.queryForObject(SQL, new Object[]{appid, authid}, new AuthExpiredTimeMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return expiredTime;
	}
	
	private static final class AuthExpiredTimeMapper implements RowMapper<Timestamp>{
		@Override
		public Timestamp mapRow(ResultSet rs, int arg1) throws SQLException {
			Timestamp expiredTime = rs.getTimestamp("C.expiredTime");
			return expiredTime;
		}		
	}
}
