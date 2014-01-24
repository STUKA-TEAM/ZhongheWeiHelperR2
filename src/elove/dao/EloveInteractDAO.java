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

public class EloveInteractDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
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
	public List<EloveMessage> getMessageList(int eloveid){
		String SQL = "SELECT * FROM elove_message WHERE eloveid = ?";
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
	public int insertJoinInfo(final EloveJoinInfo eloveJoinInfo){
		int result = 0;
	    final String SQL = "INSERT INTO elove_join VALUES (default, ?, ?, ?, ?, ?)";

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
	
	public List<EloveJoinInfo> getJoinInfoList(int eloveid){
		String SQL = "SELECT * FROM elove_join WHERE eloveid = ?";
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
