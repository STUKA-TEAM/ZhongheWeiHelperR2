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
	
	/**
	 * @title: getEloveNum
	 * @description: 获取一个appid下的所有elove数量
	 * @param appid
	 * @return
	 */
	public int getEloveNum(String appid){
		String SQL = "SELECT COUNT(*) FROM elove WHERE appid = ?";
		int count = 0;
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, appid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	/**
	 * @title: getConsumeRecord
	 * @description: 查询elove消费情况
	 * @param appid
	 * @return
	 */
	public Integer getConsumeRecord(String appid){
		String SQL = "SELECT notPayNumber FROM elove_consume_record WHERE appid = ?";
		Integer notPayNumber = null;
		try {
			notPayNumber = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, new NotPayNumberMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return notPayNumber;
	}
	
	private static final class NotPayNumberMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer notPayNumber = rs.getInt("notPayNumber");
			return notPayNumber;
		}		
	}
}
