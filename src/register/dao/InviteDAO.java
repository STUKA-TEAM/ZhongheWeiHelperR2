package register.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * @Title: InviteDAO
 * @Description: DAO for inviteCode
 * @Company: Tuka
 * @author ben
 * @date 2014年3月21日
 */
public class InviteDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title insertCodeList
	 * @description 插入注册码列表
	 * @param codeList
	 */
	public void insertCodeList(List<String> codeList){
		String SQL = "INSERT INTO invite (id, code) VALUES (default, ?)";
		for (int i = 0; i < codeList.size(); i++) {
			jdbcTemplate.update(SQL, codeList.get(i));
		}
	}
	
	//delete
	/**
	 * @title deleteCode
	 * @description 删除注册码
	 * @param code
	 * @return
	 */
	public int deleteCode(String code){
		String SQL = "DELETE FROM invite WHERE code = ?";
		int result = jdbcTemplate.update(SQL, code);
		return result <= 0 ? 0 : result;
	}
	
	//query
	/**
	 * @title getCodeList
	 * @description 获取注册码列表
	 * @return
	 */
	public List<String> getCodeList(){
		String SQL = "SELECT code FROM invite";
		List<String> codeList = null;
		try {
			codeList = jdbcTemplate.query(SQL, new CodeMapper());
		} catch (Exception e) {
			System.out.println("getCodeList: " + e.getMessage());
			codeList = new ArrayList<String>();
		}
		return codeList;
	}
	
	private static final class CodeMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String code = rs.getString("code");
			return code;
		}	
	}
	
	/**
	 * @title checkExists
	 * @description 查询邀请码是否存在
	 * @param code
	 * @return
	 */
	public boolean checkExists(String code){
		String SQL = "SELECT COUNT(*) FROM invite WHERE code = ?";
		int count = 0;
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, code);
		} catch (Exception e) {
			System.out.println("checkExists: " + e.getMessage());
		}
		if (count >= 1) {
			return true;
		} else {
			return false;
		}
	}
}
