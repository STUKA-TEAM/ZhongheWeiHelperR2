package feedback.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import elove.EloveMessage;
import feedback.Feedback;

public class FeedbackDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int insertFeedback(final Feedback feedback){
		int result = 0;
	    final String SQL = "INSERT INTO feedback VALUES (default, ?, ?, ?)";

		KeyHolder kHolder = new GeneratedKeyHolder();		
		result = jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
	            ps.setInt(1, feedback.getSid());
	            ps.setString(2, feedback.getMessage());
	            ps.setTimestamp(3, feedback.getCreateTime());
	            return ps;
	        }
	    }, kHolder);
		
		return result <= 0 ? 0 : kHolder.getKey().intValue();
	}
	public List<Feedback> getFeedbackList(){
		String SQL = "SELECT * FROM feedback";
		List<Feedback> feedbackList = null;
		try {
			feedbackList = jdbcTemplate.query(SQL, new Object[]{}, new FeedbackMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return feedbackList;
	}
	
	private static final class FeedbackMapper implements RowMapper<Feedback>{
		@Override
		public Feedback mapRow(ResultSet rs, int arg1) throws SQLException {
			Feedback feedback = new Feedback();
			feedback.setId(rs.getInt("id"));
			feedback.setSid(rs.getInt("sid"));
			feedback.setMessage(rs.getString("message"));
			feedback.setCreateTime(rs.getTimestamp("createTime"));
			return feedback;
		}		
	}
	
}
