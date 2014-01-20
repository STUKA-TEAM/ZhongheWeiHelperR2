package register.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import register.AuthPrice;
import register.Authority;

/**
 * @Title: AuthPriceDAO
 * @Description: DAO for both authPrice and authority model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class AuthPriceDAO {
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
		String SQL = "INSERT INTO store_auth_price VALUES (default, ?, ?, ?)";		
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
		String SQL = "INSERT INTO customer_authority VALUES (default, ?, ?, ?)";
		int result = jdbcTemplate.update(SQL, sid, authid, expiredTime);
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
			BigDecimal price = rs.getBigDecimal("price");
			return price;
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
