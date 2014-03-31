package lottery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lottery.LotteryWheel;
import lottery.LotteryWheelItem;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * @Title: LotteryWheelDAO
 * @Description: DAO for lottery wheel model
 * @Company: Tuka
 * @author ben
 * @date 2014年3月29日
 */
public class LotteryWheelDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title insertWheel
	 * @description 插入抽奖活动及奖项信息
	 * @param wheel
	 * @return
	 */
	public int insertWheel(final LotteryWheel wheel){
		int result = 0;
		final String SQL = "INSERT INTO lotterywheel (wheelid, wheeluuid, appid, "
				+ "wheelName, createTime, wheelDesc, maxDayCount, count) VALUES "
				+ "(default, ?, ?, ?, ?, ?, ?, ?)";
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, wheel.getWheeluuid());
		        ps.setString(2, wheel.getAppid());
		        ps.setString(3, wheel.getWheelName());
		        ps.setTimestamp(4, wheel.getCreateTime());
		        ps.setString(5, wheel.getWheelDesc());
		        ps.setInt(6, wheel.getMaxDayCount());
		        ps.setInt(7, 0);
		        return ps;
		    }
		}, kHolder);
		
		if (result > 0) {
			int wheelid = kHolder.getKey().intValue();
			result = insertItemList(wheelid, wheel.getItemList());
			if (result == 0) {
				return -1;
			}
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * @title insertItemList
	 * @description 插入抽奖活动奖项信息
	 * @param wheelid
	 * @param itemList
	 * @return
	 */
	private int insertItemList(int wheelid, List<LotteryWheelItem> itemList){
		String SQL = "INSERT INTO lotterywheel_item (itemid, wheelid, itemDesc, itemCount, itemPercent) VALUES (default, ?, ?, ?, ?)";
		int result = 1;
		if (itemList != null) {
			for (int i = 0; i < itemList.size(); i++) {
				LotteryWheelItem item = itemList.get(i);
				result = jdbcTemplate.update(SQL, wheelid, item.getItemDesc(), item.getItemCount(), item.getItemPercent());
				if (result <= 0) {
					return 0;
				}
			}
			return result;
		} else {
			return result;
		}
	}
	
	//delete
	/**
	 * @title deleteWheel
	 * @description 根据wheelid删除抽奖活动及奖项信息
	 * @param wheelid
	 * @return
	 */
	public int deleteWheel(int wheelid){
		String SQL = "DELETE FROM lotterywheel WHERE wheelid = ?";
		int result = 0;
		
		deleteItemList(wheelid);
		result = jdbcTemplate.update(SQL, wheelid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteItemList
	 * @description 根据wheelid删除奖项信息
	 * @param wheelid
	 * @return
	 */
	private int deleteItemList(int wheelid){
		String SQL = "DELETE FROM lotterywheel_item WHERE wheelid = ?";
		int result = jdbcTemplate.update(SQL, wheelid);
		return result <= 0 ? 0 : result;
	}
	
	//update
	/**
	 * @title updateWheel
	 * @description 更新抽奖活动信息
	 * @param wheel
	 * @return
	 */
	public int updateWheel(LotteryWheel wheel){
		String SQL = "UPDATE lotterywheel SET wheelName = ?, wheelDesc = ?, "
				+ "maxDayCount = ? WHERE wheelid = ?";
		int result = jdbcTemplate.update(SQL, wheel.getWheelName(), wheel.getWheelDesc(), 
				wheel.getMaxDayCount(), wheel.getWheelid());
		return result <= 0 ? 0 : result;
	}

	//query
	/**
	 * @title getWheelinfoList
	 * @description 根据appid查询该应用下大转盘抽奖活动信息列表 (wheelid, wheelName, createTime, count)
	 * @param appid
	 * @return
	 */
	public List<LotteryWheel> getWheelinfoList(String appid){
		String SQL = "SELECT wheelid, wheelName, createTime, count FROM lotterywheel WHERE appid = ?";
		List<LotteryWheel> wheelList = null;
		try {
			wheelList = jdbcTemplate.query(SQL, new Object[]{appid}, new ListWheelinfoMapper());
		} catch (Exception e) {
			System.out.println("getWheelinfoList: " + e.getMessage());
			wheelList = new ArrayList<LotteryWheel>();
		}
		return wheelList;
	}
	
	private static final class ListWheelinfoMapper implements RowMapper<LotteryWheel>{
		@Override
		public LotteryWheel mapRow(ResultSet rs, int arg1) throws SQLException {
			LotteryWheel wheel = new LotteryWheel();
			wheel.setWheelid(rs.getInt("wheelid"));
			wheel.setWheelName(rs.getString("wheelName"));
			wheel.setCreateTime(rs.getTimestamp("createTime"));
			wheel.setCount(rs.getInt("count"));
			return wheel;
		}
	}
	
	/**
	 * @title getWheelinfo
	 * @description 根据wheelid获取大转盘抽奖活动及各奖项可编辑信息 (wheelid, wheelName, wheelDesc, maxDayCount,
	 * itemList (itemid, itemDesc, itemCount, itemPercent) )
	 * @param wheelid
	 * @return
	 */
	public LotteryWheel getWheelinfo(int wheelid){
		String SQL = "SELECT wheelid, wheelName, wheelDesc, maxDayCount FROM lotterywheel WHERE wheelid = ?";
		LotteryWheel wheel = null;
		try {
			wheel = jdbcTemplate.queryForObject(SQL, new Object[]{wheelid}, new EditWheelinfoMapper());
		} catch (Exception e) {
			System.out.println("getWheelinfo: " + e.getMessage());
		}
		return wheel;
	}
	
	private static final class EditWheelinfoMapper implements RowMapper<LotteryWheel>{
		@Override
		public LotteryWheel mapRow(ResultSet rs, int arg1) throws SQLException {
			LotteryWheel wheel = new LotteryWheel();
			wheel.setWheelid(rs.getInt("wheelid"));
			wheel.setWheelName(rs.getString("wheelName"));
			wheel.setWheelDesc(rs.getString("wheelDesc"));
			wheel.setMaxDayCount(rs.getInt("maxDayCount"));
			return wheel;
		}	
	}
	
	/**
	 * @title getIteminfoList
	 * @description 根据wheelid获取奖项信息 (itemid, itemDesc, itemCount, itemPercent)
	 * @param wheelid
	 * @return
	 */
	private List<LotteryWheelItem> getIteminfoList(int wheelid){
		String SQL = "SELECT itemid, itemDesc, itemCount, itemPercent FROM lotterywheel_item WHERE wheelid = ?";
		List<LotteryWheelItem> itemList = null;
		try {
			itemList = jdbcTemplate.query(SQL, new Object[]{wheelid}, new IteminfoMapper());
		} catch (Exception e) {
			System.out.println("getIteminfoList: " + e.getMessage());
			itemList = new ArrayList<LotteryWheelItem>();
		}
		return itemList;
	}
	
	private static final class IteminfoMapper implements RowMapper<LotteryWheelItem>{
		@Override
		public LotteryWheelItem mapRow(ResultSet rs, int arg1) throws SQLException {
			LotteryWheelItem item = new LotteryWheelItem();
			item.setItemid(rs.getInt("itemid"));
			item.setItemDesc(rs.getString("itemDesc"));
			item.setItemCount(rs.getInt("itemCount"));
			item.setItemPercent(rs.getBigDecimal("itemPercent"));
			return item;
		}	
	}
}
