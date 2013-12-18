package security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import security.Role;
import security.User;

/**
 * @Title: UserDAO
 * @Description: get user information from database
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月16日
 */
public class UserDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//query
	/**
	 * @Title: loadUserByUsername
	 * @Description: 根据用户名从数据库中获取用户、角色信息
	 * @param username
	 * @return
	 */
	public User loadUserByUsername(final String username) {
		String SQL = "SELECT R.roleName, S.password, S.username FROM role R, storeuser S WHERE R.id = S.roleid AND S.username = ?";
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(SQL, new Object[]{username}, new UserInfoMapper());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
        return user;
    }
	
	private static final class UserInfoMapper implements RowMapper<User>{
		@Override
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			user.setUsername(rs.getString("S.username"));
			user.setPassword(rs.getString("S.password"));
			Role role = new Role();                         //默认一个用户对应一个角色
	        role.setName(rs.getString("R.roleName"));
	        List<Role> roles = new ArrayList<Role>();
	        roles.add(role);
	        user.setAuthorities(roles);
			return user;
		}		
	}
}
