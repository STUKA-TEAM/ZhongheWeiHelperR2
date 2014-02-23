package elove.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import elove.EloveInfo;
import elove.EloveNotpay;

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
	
	//update
	/**
	 * @title: updateConsumeRecord
	 * @description: 更新elove消费记录
	 * @param eloveNotpay
	 * @return
	 */
	public int updateConsumeRecord(EloveNotpay eloveNotpay){
		String SQL = "UPDATE elove_consume_record SET notPayNumber = ? WHERE appid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{
				eloveNotpay.getNotPayNumber(), eloveNotpay.getAppid()});
		return effected <= 0 ? 0 : effected;
	}
	
	/**
	 * @title: updateConsumeList
	 * @description: 更新elove消费记录列表
	 * @param notpayList
	 * @return
	 */
	public int updateConsumeList(List<EloveNotpay> notpayList){
		int result = 1;
		
		for (int i = 0; i < notpayList.size(); i++) {
			result = updateConsumeRecord(notpayList.get(i));
			if (result == 0) {
				return result;
			}
		}	
		return result;
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
			infoList = new ArrayList<EloveInfo>();
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
	 * @title: getBillEloveInfoList
	 * @description: 获取elove未付款账单信息列表
	 * @param appid
	 * @param limit
	 * @return
	 */
	public List<EloveInfo> getBillEloveInfoList(String appid, int limit){
		String SQL = "SELECT createTime, xinNiang, xinLang FROM elove WHERE "
				+ "appid = ? ORDER BY createTime DESC LIMIT ?";
		List<EloveInfo> infoList = null;
		
		try {
			infoList = jdbcTemplate.query(SQL, new Object[]{appid, limit}, new BillEloveInfoMapper());
		} catch (Exception e) {
			infoList = new ArrayList<EloveInfo>();
			System.out.println(e.getMessage());
		}
        return infoList;
	}
	
	private static final class BillEloveInfoMapper implements RowMapper<EloveInfo>{
		@Override
		public EloveInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			EloveInfo eloveInfo = new EloveInfo();
			eloveInfo.setCreateTime(rs.getTimestamp("createTime"));
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
