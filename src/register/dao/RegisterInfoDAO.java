package register.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import register.RegisterInfo;

/**
 * @Title: RegisterInfoDAO
 * @Description: DAO for RegisterInfo model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月09日
 */
public class RegisterInfoDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @Title: insertRegisterInfo
	 * @Description: 插入一条用户注册信息记录
	 * @param registerInfo
	 * @return
	 */
	public int insertRegisterInfo(final RegisterInfo registerInfo){
		int result = 0;
		final String SQL = "INSERT INTO storeuser VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		KeyHolder kHolder = new GeneratedKeyHolder();		
		result = jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
	            ps.setString(1, registerInfo.getUsername());
	            ps.setString(2, registerInfo.getPassword());
	            ps.setTimestamp(3, registerInfo.getCreateDate());
	            ps.setString(4, registerInfo.getStoreName());
	            ps.setString(5, registerInfo.getEmail());
	            ps.setString(6, registerInfo.getPhone());
	            ps.setString(7, registerInfo.getCellPhone());
	            ps.setString(8, registerInfo.getAddress());
	            return ps;
	        }
	    }, kHolder);
		
		return result == 0 ? 0 : kHolder.getKey().intValue();
	}
}
