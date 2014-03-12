package elove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import elove.EloveJoinInfo;
import elove.EloveMessage;

/**
 * @Title: EloveInteractDAO
 * @Description: DAO for eloveMessage and eloveJoinInfo model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月27日
 */
public class EloveInteractDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title insertMessage
	 * @description 插入祝福语信息
	 * @param eloveMessage
	 * @return
	 */
	public int insertMessage(final EloveMessage eloveMessage){
		int result = 0;
	    final String SQL = "INSERT INTO elove_message (id, eloveid, createTime, name, content) VALUES (default, ?, ?, ?, ?)";

		KeyHolder kHolder = new GeneratedKeyHolder();		
		result = jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
	            ps.setInt(1, eloveMessage.getEloveid());
	            ps.setTimestamp(2, eloveMessage.getCreateTime());
	            ps.setString(3, eloveMessage.getName());
	            ps.setString(4, eloveMessage.getContent());
	            return ps;
	        }
	    }, kHolder);
		
		return result <= 0 ? 0 : kHolder.getKey().intValue();
	}
	
	/**
	 * @title insertJoinInfo
	 * @description 插入参加婚礼信息
	 * @param eloveJoinInfo
	 * @return
	 */
	public int insertJoinInfo(final EloveJoinInfo eloveJoinInfo){
		int result = 0;
	    final String SQL = "INSERT INTO elove_join (id, eloveid, createTime, name, phone, number) VALUES (default, ?, ?, ?, ?, ?)";

		KeyHolder kHolder = new GeneratedKeyHolder();		
		result = jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
	            ps.setInt(1, eloveJoinInfo.getEloveid());
	            ps.setTimestamp(2, eloveJoinInfo.getCreateTime());
	            ps.setString(3, eloveJoinInfo.getName());
	            ps.setString(4, eloveJoinInfo.getPhone());
	            ps.setInt(5, eloveJoinInfo.getNumber());
	            return ps;
	        }
	    }, kHolder);
		
		return result <= 0 ? 0 : kHolder.getKey().intValue();
	}
	
	//query
	/**
	 * @title getMessageList
	 * @description 根据eloveid获取祝福语详细信息列表(id, createTime, name, content)
	 * @param eloveid
	 * @return
	 */
	public List<EloveMessage> getMessageList(int eloveid){
		String SQL = "SELECT id, createTime, name, content FROM elove_message WHERE eloveid = ?";
		List<EloveMessage> messageList = null;
		try {
			messageList = jdbcTemplate.query(SQL, new Object[]{eloveid}, new EloveMessageMapper());
		} catch (Exception e) {
			messageList = new ArrayList<EloveMessage>();
			System.out.println(e.getMessage());
		}
		return messageList;
	}
	
	private static final class EloveMessageMapper implements RowMapper<EloveMessage>{
		@Override
		public EloveMessage mapRow(ResultSet rs, int arg1) throws SQLException {
			EloveMessage eloveMessage = new EloveMessage();
			eloveMessage.setId(rs.getInt("id"));
			eloveMessage.setCreateTime(rs.getTimestamp("createTime"));
			eloveMessage.setName(rs.getString("name"));
			eloveMessage.setContent(rs.getString("content"));
			return eloveMessage;
		}		
	}
	
	/**
	 * @title getBasicMessageList
	 * @description 根据eloveid获取基本的祝福语信息列表(name, content)
	 * @param eloveid
	 * @return
	 */
	public List<EloveMessage> getBasicMessageList(int eloveid){
		String SQL = "SELECT name, content FROM elove_message WHERE eloveid = ?";
		List<EloveMessage> messageList = null;
		try {
			messageList = jdbcTemplate.query(SQL, new Object[]{eloveid}, new BasicEloveMessageMapper());
		} catch (Exception e) {
			messageList = new ArrayList<EloveMessage>();
			System.out.println(e.getMessage());
		}
		return messageList;
	}
	
	private static final class BasicEloveMessageMapper implements RowMapper<EloveMessage>{
		@Override
		public EloveMessage mapRow(ResultSet rs, int arg1) throws SQLException {
			EloveMessage eloveMessage = new EloveMessage();
			eloveMessage.setName(rs.getString("name"));
			eloveMessage.setContent(rs.getString("content"));
			return eloveMessage;
		}		
	}

	/**
	 * @title getJoinInfoList
	 * @description 根据eloveid获取参加婚礼信息列表
	 * @param eloveid
	 * @return
	 */
	public List<EloveJoinInfo> getJoinInfoList(int eloveid){
		String SQL = "SELECT id, createTime, name, phone, number FROM elove_join WHERE eloveid = ?";
		List<EloveJoinInfo> joinInfoList = null;
		try {
			joinInfoList = jdbcTemplate.query(SQL, new Object[]{eloveid}, new EloveJoinInfoMapper());
		} catch (Exception e) {
			joinInfoList = new ArrayList<EloveJoinInfo>();
			System.out.println(e.getMessage());
		}
		return joinInfoList;
	}
	
	private static final class EloveJoinInfoMapper implements RowMapper<EloveJoinInfo>{
		@Override
		public EloveJoinInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			EloveJoinInfo eloveJoinInfo = new EloveJoinInfo();
			eloveJoinInfo.setId(rs.getInt("id"));
			eloveJoinInfo.setCreateTime(rs.getTimestamp("createTime"));
			eloveJoinInfo.setName(rs.getString("name"));
			eloveJoinInfo.setPhone(rs.getString("phone"));
			eloveJoinInfo.setNumber(rs.getInt("number"));
			return eloveJoinInfo;
		}		
	}
}
