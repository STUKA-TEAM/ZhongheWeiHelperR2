package health.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import health.StationInfo;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * @Title: HealthDAO
 * @Description: DAO for health model
 * @Company: Tuka
 * @author ben
 * @date 2014年7月18日
 */
public class HealthDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert & update
	/**
	 * @title editStationInfo
	 * @description 更新体检站信息(mid == 0 insert ; mid > 0 update)
	 * @param stationInfo
	 * @return
	 */
	public int editStationInfo(StationInfo stationInfo) {
		String SQL = null;
		int result = 0;
		int mid = stationInfo.getMid();
		if (mid == 0) {
			SQL = "INSERT INTO medicalstation (appid, host) VALUES (?, ?)";
			result = jdbcTemplate.update(SQL, stationInfo.getAppid(), 
					stationInfo.getHost());
		} else {
			if (mid > 0) {
				SQL = "UPDATE medicalstation SET host = ? WHERE appid = ? AND mid = ?";
				result = jdbcTemplate.update(SQL, stationInfo.getHost(), 
						stationInfo.getAppid(), mid);
			}
		}
		return result <= 0 ? 0 : result;
	}
	
	//query
	/**
	 * @title getStationInfo
	 * @description 根据appid查询体检站信息(mid, appid, host)
	 * @param appid
	 * @return
	 */
	public StationInfo getStationInfo(String appid) {
		StationInfo stationInfo = null;
		String SQL = "SELECT mid, appid, host FROM medicalstation WHERE appid = ?";
		try {
			stationInfo = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, 
					new StationInfoMapper());
		} catch (Exception e) {
			System.out.println("getStationInfo: " + e.getMessage());
		}
		return stationInfo;
	}
	
	/**
	 * @title getHostInfo
	 * @description 根据appid查询对应的主机信息
	 * @param appid
	 * @return
	 */
	public String getHostInfo(String appid) {
		String host = null;
		String SQL = "SELECT host FROM medicalstation WHERE appid = ?";
		try {
			host = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, 
					new HostInfoMapper());
		} catch (Exception e) {
			System.out.println("getHostInfo: " + e.getMessage());
		}
		return host;
	}
	
	private static final class StationInfoMapper implements RowMapper<StationInfo> {
		@Override
		public StationInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			StationInfo stationInfo = new StationInfo();
			stationInfo.setMid(rs.getInt("mid"));
			stationInfo.setAppid(rs.getString("appid"));
			stationInfo.setHost(rs.getString("host"));
			return stationInfo;
		}
	}
	
	private static final class HostInfoMapper implements RowMapper<String> {
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String host = rs.getString("host");
			return host;
		}
	}
}
