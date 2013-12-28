package elove.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import elove.AuthPrice;

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
	
	//query
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
}
