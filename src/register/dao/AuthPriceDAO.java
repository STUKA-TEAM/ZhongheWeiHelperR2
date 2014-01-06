package register.dao;

import java.math.BigDecimal;
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

import register.AuthPrice;

/**
 * @Title: AuthPriceDAO
 * @Description: DAO for authPrice model
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
	 * @param authName
	 * @param price
	 * @return
	 */
	public int insertPrice(final int sid, String authName, final BigDecimal price){
		final String SQL = "INSERT INTO store_auth_price VALUES (default, ?, ?, ?)";
		Integer id = getAuthid(authName);
		if (id == null) {
			return 0;
		}
		final int authid = id;
		int result = 0;
				
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
		        ps.setInt(1, sid);
		        ps.setInt(2, authid);
		        ps.setBigDecimal(3, price);
		        return ps;
		    }
		}, kHolder);
		
		return result;
	}
	
	//query
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
}
