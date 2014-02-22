package register.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import register.AuthInfo;
import register.AuthPrice;
import register.Authority;

/**
 * @Title: AuthInfoDAO
 * @Description: DAO for both authPrice and authority model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class AuthInfoDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title: insertPrice
	 * @description: 插入价格相关信息
	 * @param sid
	 * @param authid
	 * @param price
	 * @return
	 */
	public int insertPrice(int sid, int authid, BigDecimal price){
		String SQL = "INSERT INTO store_auth_price (id, sid, authid, price) VALUES (default, ?, ?, ?)";		
		int result = jdbcTemplate.update(SQL, sid, authid, price);		
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: insertCAR
	 * @description: customer authority relationship management
	 * @param sid
	 * @param authid
	 * @return
	 */
	public int insertCAR(int sid, int authid, Timestamp expiredTime){
		String SQL = "INSERT INTO customer_authority (id, sid, authid, expiredTime) VALUES (default, ?, ?, ?)";
		int result = jdbcTemplate.update(SQL, sid, authid, expiredTime);
		return result <= 0 ? 0 : result;
	}
	
	//update
	/**
	 * @title: updateAuthInfo
	 * @description: 更新权限过期时间和价格
	 * @param authInfo
	 * @return
	 */
	public int updateAuthInfo(AuthInfo authInfo){
		int result = 0;
		
		Integer authid = getAuthid(authInfo.getAuthPinyin());
		if (authid != null) {
			int sid = authInfo.getSid();
			Timestamp expiredTime = authInfo.getExpiredTime();
			BigDecimal price = authInfo.getPrice();
			
			result = updateExpiredTime(sid, authid, expiredTime);
			if (result != 0) {
				result = updatePrice(sid, authid, price);
				return result;
			}else {
				return -1;
			}
		}else {
			return -2;
		}
	}
	
	/**
	 * @title: updateExpiredTime
	 * @description: 更新权限过期时间
	 * @param sid
	 * @param authid
	 * @param expiredTime
	 * @return
	 */
	public int updateExpiredTime(int sid, int authid, Timestamp expiredTime){
		String SQL = "UPDATE customer_authority SET expiredTime = ? WHERE "
				+ "sid = ? AND authid = ?";
		int result = jdbcTemplate.update(SQL, expiredTime, sid, authid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: updatePrice
	 * @description: 更新权限价格
	 * @param sid
	 * @param authid
	 * @param price
	 * @return
	 */
	public int updatePrice(int sid, int authid, BigDecimal price){
		String SQL = "UPDATE store_auth_price SET price = ? WHERE sid = ? AND "
				+ "authid = ?";
		int result = jdbcTemplate.update(SQL, price, sid, authid);
		return result <= 0 ? 0 : result;
	}
	
	//query
	/**
	 * @title: getAuthid
	 * @description: 查询权限名字对应的id
	 * @param authName
	 * @return
	 */
	public Integer getAuthid(String authName){
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
	 * @Title: getPriceBySid
	 * @Description: 根据用户id获取相关联的收费权限详情
	 * @param sid
	 * @return
	 */
	public List<AuthPrice> getPriceBySid(int sid){
		String SQL = "SELECT A.authName, S.price FROM store_auth_price S, "
				+ "authority A WHERE S.authid = A.authid AND S.sid = ?";
		List<AuthPrice> priceList = null;
		try {
			priceList = jdbcTemplate.query(SQL, new Object[]{sid}, new AuthPriceMapper());
		} catch (Exception e) {
			priceList = new ArrayList<AuthPrice>();
			System.out.println(e.getMessage());
		}
		return priceList;
	}
	
	private static final class AuthPriceMapper implements RowMapper<AuthPrice>{
		@Override
		public AuthPrice mapRow(ResultSet rs, int arg1) throws SQLException {
			AuthPrice authPrice = new AuthPrice();
			authPrice.setAuthName(rs.getString("A.authName"));
			authPrice.setPrice(rs.getBigDecimal("S.price"));
			return authPrice;
		}		
	}
	
	/**
	 * @title: getPrice
	 * @description: 获取指定用户某项权限的售价
	 * @param sid
	 * @param authName
	 * @return
	 */
	public BigDecimal getPrice(int sid, String authName){
		String SQL = "SELECT S.price FROM store_auth_price S, "
				+ "authority A WHERE S.authid = A.authid AND A.authName = ? AND S.sid = ?";
		BigDecimal price = null;
		try {
			price = jdbcTemplate.queryForObject(SQL, new Object[]{authName, sid}, new PriceMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return price;
	}
	
	private static final class PriceMapper implements RowMapper<BigDecimal>{
		@Override
		public BigDecimal mapRow(ResultSet rs, int arg1) throws SQLException {
			BigDecimal price = rs.getBigDecimal("S.price");
			return price;
		}		
	}
	
	/**
	 * @title: getExpiredTime
	 * @description: 根据sid和authPinyin查询过期时间expiredTime
	 * @param sid
	 * @param authPinyin
	 * @return
	 */
	public Timestamp getExpiredTime(int sid, String authPinyin){
		String SQL = "SELECT C.expiredTime FROM customer_authority C, authority A "
				+ "WHERE C.authid = A.authid AND C.sid = ? AND A.authPinyin = ?";
		Timestamp expiredTime = null;
		
		try {
			expiredTime = jdbcTemplate.queryForObject(SQL, new Object[]{sid, authPinyin}, new ExpiredTimeMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return expiredTime;
	}
	
	private static final class ExpiredTimeMapper implements RowMapper<Timestamp>{
		@Override
		public Timestamp mapRow(ResultSet rs, int arg1) throws SQLException {
			Timestamp expiredTime = rs.getTimestamp("C.expiredTime");
			return expiredTime;
		}	
	}
	
	/**
	 * @title: getAllAuthorities
	 * @description: 获取所有的权限信息
	 * @return
	 */
	public List<Authority> getAllAuthorities(){
		String SQL = "SELECT * FROM authority";
		List<Authority> aList = null;
		
		try {
			aList = jdbcTemplate.query(SQL, new FullAuthorityMapper());
		} catch (Exception e) {
			aList = new ArrayList<Authority>();
			System.out.println(e.getMessage());
		}
		return aList;
	}
	
	private static final class FullAuthorityMapper implements RowMapper<Authority>{
		@Override
		public Authority mapRow(ResultSet rs, int arg1) throws SQLException {
			Authority authority = new Authority();
			authority.setAuthid(rs.getInt("authid"));
			authority.setAuthName(rs.getString("authName"));
			authority.setAuthPinyin(rs.getString("authPinyin"));
			return authority;
		}	
	}
}
