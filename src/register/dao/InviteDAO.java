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
}
