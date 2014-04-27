package register.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import register.RoleInfo;

/**
 * @Title: RoleInfoDAO
 * @Description: DAO for RoleInfo model
 * @Company: Tuka
 * @author ben
 * @date 2014年4月25日
 */
public class RoleInfoDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//query
	/**
	 * @title getBranchRoleList
	 * @description 查询分店类角色信息列表 (id, roleLabel)
	 * @return
	 */
	public List<RoleInfo> getBranchRoleList() {
		List<RoleInfo> roleList = null;
		String SQL = "SELECT id, roleLabel FROM role WHERE roleName LIKE 'BRANCH%'";
		try {
			roleList = jdbcTemplate.query(SQL, new ChooseRoleMapper());
		} catch (Exception e) {
			System.out.println("getBranchRoleList: " + e.getMessage());
			roleList = new ArrayList<RoleInfo>();
		}
		return roleList;
	}
	
	private static final class ChooseRoleMapper implements RowMapper<RoleInfo>{
		@Override
		public RoleInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setRoleid(rs.getInt("id"));
			roleInfo.setRoleLabel(rs.getString("roleLabel"));
			return roleInfo;
		}
	}
}
