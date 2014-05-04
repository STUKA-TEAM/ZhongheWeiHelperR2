package lottery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lottery.LotteryPrize;
import lottery.LotteryResult;
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
	 * @title insertResult
	 * @description 初始化用户中奖纪录
	 * @param itemid
	 * @param openid
	 * @return
	 */
	public int insertResult(int itemid, String openid){
		String SQL = "INSERT INTO lotterywheel_result (resultid, itemid, contact, openid) VALUES (default, ?, ?, ?)";
		int result = jdbcTemplate.update(SQL, itemid, "initial", openid);
		return result <= 0 ? 0 : result;
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
	 * @description 根据wheelid删除抽奖活动信息、奖项信息及中奖记录信息
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
	 * @title deleteWheel
	 * @description 根据appid删除该应用下所有抽奖活动信息、奖项信息及中奖记录信息
	 * @param appid
	 * @return
	 */
	public int deleteWheel(String appid){
		String SQL = "DELETE FROM lotterywheel WHERE appid = ?";
		int result = 0;
		
		List<Integer> wheelidList = getWheelidList(appid);
		for (int i = 0; i < wheelidList.size(); i++) {
			deleteItemList(wheelidList.get(i));
		}
		result = jdbcTemplate.update(SQL, appid);
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
		List<Integer> itemidList = getItemidList(wheelid);
		for (int i = 0; i < itemidList.size(); i++) {
			deleteResult(itemidList.get(i));
		}
		int result = jdbcTemplate.update(SQL, wheelid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteResult
	 * @description 根据itemid删除中奖结果信息
	 * @param itemid
	 * @return
	 */
	private int deleteResult(int itemid){
		String SQL = "DELETE FROM lotterywheel_result WHERE itemid = ?";
		int result = jdbcTemplate.update(SQL, itemid);
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
	
	/**
	 * @title updateWheelCount
	 * @description 根据wheelid更新抽奖人数 (count + 1)
	 * @param wheelid
	 * @return
	 */
	public int updateWheelCount(int wheelid){
		String SQL = "UPDATE lotterywheel SET count = count + 1 WHERE wheelid = ?";
		int result = jdbcTemplate.update(SQL, wheelid);
		return result <= 0 ? 0 : result;
	}

	/**
	 * @title updateContact
	 * @description 更新中奖联系信息
	 * @param itemid
	 * @param openid
	 * @param contact
	 * @return
	 */
	public int updateContact(int itemid, String openid, String contact){
		String SQL = "UPDATE lotterywheel_result SET contact = ? WHERE itemid = ? AND openid = ?";
		int result = jdbcTemplate.update(SQL, contact, itemid, openid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title updateResultStatus
	 * @description 根据resultid更新该中奖纪录状态 (1 --> 0)
	 * @param resultid
	 * @return
	 */
	public int updateResultStatus(int resultid){
		String SQL = "UPDATE lotterywheel_result SET status = status - 1 WHERE resultid = ?";
		int result = jdbcTemplate.update(SQL, resultid);
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
	 * @title getWheelForCustomer
	 * @description 根据wheelid获取手机端抽奖显示信息 (wheelid, wheeluuid, wheelName, wheelDesc, maxDayCount, 
	 * itemList(itemDesc, itemCount))
	 * @param wheelid
	 * @return
	 */
	public LotteryWheel getWheelForCustomer(int wheelid){
		String SQL = "SELECT wheelid, wheeluuid, wheelName, wheelDesc, maxDayCount FROM lotterywheel WHERE wheelid = ?";
		LotteryWheel wheel = null;
		try {
			wheel = jdbcTemplate.queryForObject(SQL, new Object[]{wheelid}, new CustomerWheelinfoMapper());
		} catch (Exception e) {
			System.out.println("getWheelForCustomer: " + e.getMessage());
		}
		if (wheel != null) {
			wheel.setItemList(getCustomerIteminfoList(wheelid));
		}
		return wheel;
	}
	
	private static final class CustomerWheelinfoMapper implements RowMapper<LotteryWheel>{
		@Override
		public LotteryWheel mapRow(ResultSet rs, int arg1) throws SQLException {
			LotteryWheel wheel = new LotteryWheel();
			wheel.setWheelid(rs.getInt("wheelid"));
			wheel.setWheeluuid(rs.getString("wheeluuid"));
			wheel.setWheelName(rs.getString("wheelName"));
			wheel.setWheelDesc(rs.getString("wheelDesc"));
			wheel.setMaxDayCount(rs.getInt("maxDayCount"));
			return wheel;
		}	
	}
	
	/**
	 * @title getWheelCookieinfo
	 * @description 根据wheelid获取设置cookie时必要信息 (wheeluuid, maxDayCount)
	 * @param wheelid
	 * @return
	 */
	public LotteryWheel getWheelCookieinfo(int wheelid){
		String SQL = "SELECT wheeluuid, maxDayCount FROM lotterywheel WHERE wheelid = ?";
		LotteryWheel wheel = null;
		try {
			wheel = jdbcTemplate.queryForObject(SQL, new Object[]{wheelid}, new CookieWheelinfoMapper());
		} catch (Exception e) {
			System.out.println("getWheelCookieinfo: " + e.getMessage());
		}
		return wheel;
	}
	
	private static final class CookieWheelinfoMapper implements RowMapper<LotteryWheel>{
		@Override
		public LotteryWheel mapRow(ResultSet rs, int arg1) throws SQLException {
			LotteryWheel wheel = new LotteryWheel();
			wheel.setWheeluuid(rs.getString("wheeluuid"));
			wheel.setMaxDayCount(rs.getInt("maxDayCount"));
			return wheel;
		}	
	}
	
	/**
	 * @title getIteminfoList
	 * @description 根据wheelid获取奖项信息(itemid, itemDesc, itemCount, itemPercent)并按中奖率大小升序排列
	 * @param wheelid
	 * @return
	 */
	public List<LotteryWheelItem> getIteminfoList(int wheelid){
		String SQL = "SELECT itemid, itemDesc, itemCount, itemPercent FROM lotterywheel_item "
				+ "WHERE wheelid = ? ORDER BY itemPercent ASC";
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
	
	/**
	 * @title getCustomerIteminfoList
	 * @description 根据wheelid获取手机端奖项信息 (itemDesc, itemCount)并按中奖率大小升序排列
	 * @param wheelid
	 * @return
	 */
	private List<LotteryWheelItem> getCustomerIteminfoList(int wheelid){
		String SQL = "SELECT itemDesc, itemCount FROM lotterywheel_item WHERE wheelid = ? ORDER BY itemPercent ASC";
		List<LotteryWheelItem> itemList = null;
		try {
			itemList = jdbcTemplate.query(SQL, new Object[]{wheelid}, new CustomerIteminfoMapper());
		} catch (Exception e) {
			System.out.println("getCustomerIteminfoList: " + e.getMessage());
			itemList = new ArrayList<LotteryWheelItem>();
		}
		return itemList;
	}
	
	private static final class CustomerIteminfoMapper implements RowMapper<LotteryWheelItem>{
		@Override
		public LotteryWheelItem mapRow(ResultSet rs, int arg1) throws SQLException {
			LotteryWheelItem item = new LotteryWheelItem();
			item.setItemDesc(rs.getString("itemDesc"));
			item.setItemCount(rs.getInt("itemCount"));
			return item;
		}	
	}
	
	/**
	 * @title getContact
	 * @description 查询中奖纪录的联系方式信息
	 * @param itemid
	 * @param openid
	 * @return
	 */
	public String getContact(int itemid, String openid){
		String SQL = "SELECT contact FROM lotterywheel_result WHERE itemid = ? AND openid = ?";
		String contact = null;
		try {
			contact = jdbcTemplate.queryForObject(SQL, new Object[]{itemid, openid}, new ContactMapper());
		} catch (Exception e) {
			System.out.println("getContact: " + e.getMessage());
		}
		return contact;
	}
	
	private static final class ContactMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String contact = rs.getString("contact");
			return contact;
		}
	}
	
	/**
	 * @title getWheelidList
	 * @description 根据appid获取wheelid列表
	 * @param appid
	 * @return
	 */
	private List<Integer> getWheelidList(String appid){
		String SQL = "SELECT wheelid FROM lotterywheel WHERE appid = ?";
		List<Integer> wheelidList = null;
		try {
			wheelidList = jdbcTemplate.query(SQL, new Object[]{appid}, new WheelidMapper());
		} catch (Exception e) {
			System.out.println("getWheelidList: " + e.getMessage());
			wheelidList = new ArrayList<Integer>();
		}
		return wheelidList;
	}
	
	private static final class WheelidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer wheelid = rs.getInt("wheelid");
			return wheelid;
		}
	}
	
	/**
	 * @title getItemidList
	 * @description 根据wheelid获取itemid列表
	 * @param wheelid
	 * @return
	 */
	private List<Integer> getItemidList(int wheelid){
		String SQL = "SELECT itemid FROM lotterywheel_item WHERE wheelid = ?";
		List<Integer> itemidList = null;
		try {
			itemidList = jdbcTemplate.query(SQL, new Object[]{wheelid}, new ItemidMapper());
		} catch (Exception e) {
			System.out.println("getItemidList: " + e.getMessage());
			itemidList = new ArrayList<Integer>();
		}
		return itemidList;
	}
	
	private static final class ItemidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer itemid = rs.getInt("itemid");
			return itemid;
		}	
	}
	
	/**
	 * @title getPrizeCount
	 * @description 根据itemid查询该奖项已中奖数量
	 * @param itemid
	 * @return
	 */
	public int getPrizeCount(int itemid){
		String SQL = "SELECT COUNT(*) FROM lotterywheel_result WHERE itemid = ?";
		int count = Integer.MAX_VALUE;
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, itemid);
		} catch (Exception e) {
			System.out.println("getPrizeCount: " + e.getMessage());
		}
		return count;
	}
	
	/**
	 * @title getWebsiteidByWheelid
	 * @description 根据wheelid查询同一应用下的websiteid
	 * @param wheelid
	 * @return
	 */
	public Integer getWebsiteidByWheelid(int wheelid){
		Integer websiteid = null;
		String SQL = "SELECT W.websiteid FROM website W, lotterywheel L WHERE W.appid = L.appid AND L.wheelid = ?";
		try {
			websiteid = jdbcTemplate.queryForObject(SQL, new Object[]{wheelid}, new WebsiteidMapper());
		} catch (Exception e) {
			System.out.println("getWebsiteidByWheelid: " + e.getMessage());
		}
		return websiteid;
	}
	
	private static final class WebsiteidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer websiteid = rs.getInt("W.websiteid");
			return websiteid;
		}
	}
	
	/**
	 * @title getLotteryResult
	 * @description 根据wheelid查询该抽奖活动各奖项信息 (itemid, itemDesc, itemCount) 及中奖详情 (resultid, contact, status)
	 * @param wheelid
	 * @return
	 */
	public List<LotteryPrize> getLotteryResult(int wheelid){
		List<LotteryPrize> prizeList = null;
		String SQL = "SELECT itemid, itemDesc, itemCount FROM lotterywheel_item WHERE "
				+ "wheelid = ? ORDER BY itemPercent ASC";
		try {
			prizeList = jdbcTemplate.query(SQL, new Object[]{wheelid}, new PrizeIteminfoMapper());
		} catch (Exception e) {
			System.out.println("getLotteryResult: " + e.getMessage());
			prizeList = new ArrayList<LotteryPrize>();
		}
		for (int i = 0, j = prizeList.size(); i < j; i++) {
			LotteryPrize prize = prizeList.get(i);
			prize.setPrizeIndex(i + 1);
			List<LotteryResult> luckyList = getLuckyResult(prize.getPrizeid());
			prize.setLuckyList(luckyList);
			prize.setLuckyNum(luckyList.size());
		}
		return prizeList;
	}
	
	private static final class PrizeIteminfoMapper implements RowMapper<LotteryPrize>{
		@Override
		public LotteryPrize mapRow(ResultSet rs, int arg1)
				throws SQLException {
			LotteryPrize prize = new LotteryPrize();
			prize.setPrizeid(rs.getInt("itemid"));
			prize.setPrizeDesc(rs.getString("itemDesc"));
			prize.setPrizeNum(rs.getInt("itemCount"));
			return prize;
		}
	}
	
	/**
	 * @title getLuckyResult
	 * @description 根据奖项id查询该奖项中奖详情 (resultid, contact, status)
	 * @param itemid
	 * @return
	 */
	private List<LotteryResult> getLuckyResult(int itemid){
		List<LotteryResult> resultList = null;
		String SQL = "SELECT resultid, contact, status FROM lotterywheel_result WHERE itemid = ?";
		try {
			resultList = jdbcTemplate.query(SQL, new Object[]{itemid}, new ResultinfoMapper());
		} catch (Exception e) {
			System.out.println("getLuckyResult: " + e.getMessage());
			resultList = new ArrayList<LotteryResult>();
		}
		return resultList;
	}
	
	private static final class ResultinfoMapper implements RowMapper<LotteryResult>{
		@Override
		public LotteryResult mapRow(ResultSet rs, int arg1)
				throws SQLException {
			LotteryResult result = new LotteryResult();
			result.setResultid(rs.getInt("resultid"));
			result.setContact(rs.getString("contact"));
			result.setStatus(rs.getInt("status"));
			return result;
		}
	}
}
