package elove.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import elove.EloveInfo;

/**
 * @Title: EloveInfoDAO
 * @Description: DAO for eloveInfo model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class EloveInfoDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//query
	/**
	 * @title: getEloveInfoList
	 * @description: 获取与appid相关的所有elove
	 * @param appid
	 * @return
	 */
	public List<EloveInfo> getEloveInfoList(String appid){
		String SQL = "SELECT eloveid, createTime, expiredTime, password, xinNiang, xinLang FROM elove WHERE appid = ?";
		List<EloveInfo> infoList = null;
		try {
			infoList = jdbcTemplate.query(SQL, new Object[]{appid}, new EloveInfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        return infoList;
	}
	
	private static final class EloveInfoMapper implements RowMapper<EloveInfo>{
		@Override
		public EloveInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			EloveInfo eloveInfo = new EloveInfo();
			eloveInfo.setEloveid(rs.getInt("eloveid"));
			eloveInfo.setCreateTime(rs.getTimestamp("createTime"));
			eloveInfo.setExpiredTime(rs.getTimestamp("expiredTime"));
			eloveInfo.setPassword(rs.getString("password"));
			eloveInfo.setXinNiang(rs.getString("xinNiang"));
			eloveInfo.setXinLang(rs.getString("xinLang"));
			return eloveInfo;
		}		
	}
}
