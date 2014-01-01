package register.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import register.UserInfo;

/**
 * @Title: UserInfoDAO
 * @Description: DAO for userInfo model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月09日
 */
public class UserInfoDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @Title: insertUserInfo
	 * @Description: 插入一条用户注册信息记录
	 * @param userInfo
	 * @return
	 */
	public int insertUserInfo(final UserInfo userInfo){
		int result = 0;
		final String SQL = "INSERT INTO storeuser VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		KeyHolder kHolder = new GeneratedKeyHolder();		
		result = jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
	            ps.setInt(1, userInfo.getRoleid());
	            ps.setString(2, userInfo.getUsername());
	            ps.setString(3, userInfo.getPassword());
	            ps.setTimestamp(4, userInfo.getCreateDate());
	            ps.setString(5, userInfo.getStoreName());
	            ps.setString(6, userInfo.getEmail());
	            ps.setString(7, userInfo.getPhone());
	            ps.setString(8, userInfo.getCellPhone());
	            ps.setString(9, userInfo.getAddress());
	            ps.setString(10, userInfo.getMajorImage());
	            ps.setString(11, userInfo.getCorpMoreInfoLink());
	            ps.setBigDecimal(12, userInfo.getLng());
	            ps.setBigDecimal(13, userInfo.getLat());
	            return ps;
	        }
	    }, kHolder);
		
		return result == 0 ? 0 : kHolder.getKey().intValue();
	}
	
	//delete
	/**
	 * @title: deleteUserInfo
	 * @description: 删除用户信息
	 * @param sid
	 * @return
	 */
	public int deleteUserInfo(int sid){
		String SQL = "DELETE FROM storeuser WHERE sid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{sid});
		return effected;
	}
	
	//update
	/**
	 * @title: updateUserInfo
	 * @description: 更新用户信息
	 * @param userInfo
	 * @return
	 */
	public int updateUserInfo(UserInfo userInfo){
		String SQL = "UPDATE storeuser SET password = ?, "
				+ "storeName = ?, email = ?, phone = ?, cellPhone = ?, "
				+ "address = ?, majorImage = ?, corpMoreInfoLink = ?, "
				+ "lng = ?, lat = ? WHERE sid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{ userInfo.getPassword(), 
				userInfo.getStoreName(), userInfo.getEmail(), userInfo.getPhone(), 
				userInfo.getCellPhone(), userInfo.getAddress(), userInfo.getMajorImage(), 
				userInfo.getCorpMoreInfoLink(), userInfo.getLng(), 
				userInfo.getLat(), userInfo.getSid()});
		return effected;
	}
	
	//query
	/**
	 * @Title: getUserInfo
	 * @Description: 根据用户id获取用户信息
	 * @param sid
	 * @return
	 */
	public UserInfo getUserInfo(int sid){
		String SQL = "SELECT * FROM storeuser WHERE sid = ?";
		UserInfo userInfo = null;
		try {
			userInfo = jdbcTemplate.queryForObject(SQL, new Object[]{sid}, new UserInfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return userInfo;
	}
	
	private static final class UserInfoMapper implements RowMapper<UserInfo>{
		@Override
		public UserInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			UserInfo userInfo = new UserInfo();
			userInfo.setUsername(rs.getString("username"));
			userInfo.setCreateDate(rs.getTimestamp("createDate"));
			userInfo.setStoreName(rs.getString("storeName"));
			userInfo.setEmail(rs.getString("email"));
			userInfo.setPhone(rs.getString("phone"));
			userInfo.setCellPhone(rs.getString("cellPhone"));
			userInfo.setAddress(rs.getString("address"));
			userInfo.setMajorImage(rs.getString("majorImage"));
			userInfo.setCorpMoreInfoLink(rs.getString("corpMoreInfoLink"));
			return userInfo;
		}
	}
	
	/**
	 * @Title: getUserCount
	 * @Description: 查询某用户名是否已被注册 0-未注册 1-已注册
	 * @param username
	 * @return
	 */
	public int getUserCount(String username){
		String SQL = "SELECT COUNT(*) FROM storeuser WHERE username = ?";
		int count = 1;
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, username);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}
}
