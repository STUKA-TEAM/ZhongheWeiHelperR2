package vote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import vote.Vote;
import vote.VoteContent;
import vote.VoteItem;

/**
 * @Title: VoteDAO
 * @Description: vote
 * @Company: Tuka
 * @author ben
 * @date 2014年3月25日
 */
public class VoteDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title insertVote
	 * @description 插入投票及选项信息
	 * @param vote
	 * @return
	 */
	public int insertVote(final Vote vote){
		int result = 0;
		final String SQL = "INSERT INTO vote (voteid, voteName, appid, createTime, "
				+ "voteDesc, coverPic, maxSelected, count) VALUES (default, ?, "
				+ "?, ?, ?, ?, ?, ?)";
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, vote.getVoteName());
		        ps.setString(2, vote.getAppid());
		        ps.setTimestamp(3, vote.getCreateTime());
		        ps.setString(4, vote.getVoteDesc());
		        ps.setString(5, vote.getCoverPic());
		        ps.setInt(6, vote.getMaxSelected());
		        ps.setInt(7, 0);
		        return ps;
		    }
		}, kHolder);
		
		if (result > 0) {
			String coverPic = vote.getCoverPic();
			if (coverPic != null && !coverPic.equals("")) {
				deleteImageTempRecord(coverPic);
			}
			
			int voteid = kHolder.getKey().intValue();
			result = insertItemList(voteid, vote.getItemList());
			if (result == 0) {
				return -1;
			}
			
			result = deleteItemPicTempRecord(vote.getItemList());
			if (result == 0) {
				return -2;
			}
			
			return result;
		}
		
		return 0;
	}
	
	/**
	 * @title insertItemList
	 * @description 插入投票选项信息
	 * @param voteid
	 * @param itemList
	 * @return
	 */
	private int insertItemList(int voteid, List<VoteItem> itemList){
		String SQL = "INSERT INTO vote_item (itemid, voteid, itemName, itemPic, count) VALUES (default, ?, ?, ?, ?)";
		int result = 1;
		
		if (itemList != null) {
			for (int i = 0; i < itemList.size(); i++) {
				VoteItem item = itemList.get(i);
				result = jdbcTemplate.update(SQL, voteid, item.getItemName(), item.getItemPic(), 0);
				if (result <= 0) {
					return 0;
				}
			}
			return result;
		} else {
			return result;
		}
	}
	
	/**
	 * @title insertVoteContent
	 * @description 插入单个用户投票内容
	 * @param content
	 * @return
	 */
	public int insertVoteContent(VoteContent content){
		int result = 0;
		
		if (content.getContact() != null && !content.getContact().equals("")) {
			result = insertVoteUser(content);
			if (result == 0) {
				return result;
			}
		}
		
		result = updateVoteCount(content.getVoteid());
		if (result == 0) {
			return 0;
		}
		
		result = updateItemCount(content.getAnswer());
		return result;
	}
	
	/**
	 * @title insertVoteUser
	 * @description 插入用户信息 (wechatOpenid, contact)
	 * @param content
	 * @return
	 */
	private int insertVoteUser(VoteContent content){
		String SQL = "INSERT INTO vote_user (id, voteid, wechatOpenid, contact) VALUES (default, ?, ?, ?)";
		int result = jdbcTemplate.update(SQL, content.getVoteid(), content.getOpenid(), content.getContact());
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: insertImageTempRecord
	 * @description: 将要删除图片的信息存入临时表
	 * @param imagePath
	 * @param current
	 * @return
	 */
	private int insertImageTempRecord(String imagePath, Timestamp current){
		String SQL = "INSERT INTO image_temp_record (id, imagePath, createDate) VALUES (default, ?, ?)";		
		int result = jdbcTemplate.update(SQL, imagePath, current);
		return result <= 0 ? 0 : result;
	}
	
	//delete
	/**
	 * @title: deleteImageTempRecord
	 * @description: 删除图片在临时表中记录
	 * @param imagePath
	 * @return
	 */
	private int deleteImageTempRecord(String imagePath){
		String SQL = "DELETE FROM image_temp_record WHERE imagePath = ?";
		int effected = jdbcTemplate.update(SQL, imagePath);
		return effected <= 0 ? 0 : effected;
	}
	
	/**
	 * @title deleteItemPicTempRecord
	 * @description 删除选项图片缓存记录
	 * @param itemList
	 * @return
	 */
	private int deleteItemPicTempRecord(List<VoteItem> itemList){
		int result = 1;
		
		if (itemList != null) {
			for (int i = 0; i < itemList.size(); i++) {
				VoteItem item = itemList.get(i);
				if (item.getItemPic() != null && !item.getItemPic().equals("")) {
					result = deleteImageTempRecord(item.getItemPic());
					if (result <= 0) {
						return 0;
					}
				}
			}
			return result;
		} else {
			return result;
		}
	}
	
	/**
	 * @title deleteVote
	 * @description 根据voteid删除投票、投票选项、投票用户信息
	 * @param voteid
	 * @return
	 */
	public int deleteVote(int voteid){
		String SQL = "DELETE FROM vote WHERE voteid = ?";
		int result = 0;
		
		Vote vote = getVoteMedia(voteid);
		if (vote != null) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			
			String coverPic = vote.getCoverPic();
			if (coverPic != null && !coverPic.equals("")) {
				insertImageTempRecord(coverPic, current);
			}
			
			for (int i = 0; i < vote.getItemList().size(); i++) {
				String itemPic = vote.getItemList().get(i).getItemPic();
				if (itemPic != null && !itemPic.equals("")) {
					insertImageTempRecord(itemPic, current);
				}
			}
		} else {
			return 0;
		}
		
		deleteItemList(voteid);
		deleteVoteUser(voteid);
		
		result = jdbcTemplate.update(SQL, voteid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteItemList
	 * @description 根据voteid删除投票选项信息
	 * @param voteid
	 * @return
	 */
	public int deleteItemList(int voteid){
		String SQL = "DELETE FROM vote_item WHERE voteid = ?";
		int result = jdbcTemplate.update(SQL, voteid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteVoteUser
	 * @description 根据voteid删除投票用户信息
	 * @param voteid
	 * @return
	 */
	public int deleteVoteUser(int voteid){
		String SQL = "DELETE FROM vote_user WHERE voteid = ?";
		int result = jdbcTemplate.update(SQL, voteid);
		return result <= 0 ? 0 : result;
	}
	
	//update
	public int updateVote(Vote vote){
		return 0;
	}
	
	/**
	 * @title updateVoteCount
	 * @description 根据voteid更新投票人数 (count + 1)
	 * @param voteid
	 * @return
	 */
	private int updateVoteCount(int voteid){
		String SQL = "UPDATE vote SET count = count + 1 WHERE voteid = ?";
		int result = jdbcTemplate.update(SQL, voteid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title updateItemCount
	 * @description 根据itemid更新该选项投票人数 (count + 1)
	 * @param itemidList
	 * @return
	 */
	private int updateItemCount(List<Integer> itemidList){
		String SQL = "UPDATE vote_item SET count = count + 1 WHERE itemid = ?";
		int result = 1;
			
		if (itemidList != null) {
			for (int i = 0; i < itemidList.size(); i++) {
				result = jdbcTemplate.update(SQL, itemidList.get(i));
				if (result <= 0) {
					return 0;
				}
			}
			return result;
		} else {
			return result;
		}
	}
	
	//query
	/**
	 * @title getVoteinfoList
	 * @description 根据appid查询该应用下投票信息列表 (voteid, voteName, createTime, count)
	 * @param appid
	 * @return
	 */
	public List<Vote> getVoteinfoList(String appid){
		String SQL = "SELECT voteid, voteName, createTime, count FROM vote WHERE appid = ?";
		List<Vote> voteList = null;
		try {
			voteList = jdbcTemplate.query(SQL, new Object[]{appid}, new VoteinfoMapper());
		} catch (Exception e) {
			System.out.println("getVoteinfoList: " + e.getMessage());
			voteList = new ArrayList<Vote>();
		}
		return voteList;
	}
	
	private static final class VoteinfoMapper implements RowMapper<Vote>{
		@Override
		public Vote mapRow(ResultSet rs, int arg1) throws SQLException {
			Vote vote = new Vote();
			vote.setVoteid(rs.getInt("voteid"));
			vote.setVoteName(rs.getString("voteName"));
			vote.setCreateTime(rs.getTimestamp("createTime"));
			vote.setCount(rs.getInt("count"));
			return vote;
		}
	}
	
	/**
	 * @title getVoteinfo
	 * @description 根据voteid查询该投票及选项详细信息 (voteid, voteName, voteDesc, coverPic, maxSelected)
	 * @param voteid
	 * @return
	 */
	public Vote getVoteinfo(int voteid){
		String SQL = "SELECT voteid, voteName, voteDesc, coverPic, maxSelected FROM vote WHERE voteid = ?";
		Vote vote = null;
		try {
			vote = jdbcTemplate.queryForObject(SQL, new Object[]{voteid}, new DetailedVoteinfoMapper());
		} catch (Exception e) {
			System.out.println("getVoteinfo: " + e.getMessage());
		}
		if (vote != null) {
			vote.setItemList(getVoteItemList(voteid));
		}
		return vote;
	}
	
	private static final class DetailedVoteinfoMapper implements RowMapper<Vote>{
		@Override
		public Vote mapRow(ResultSet rs, int arg1) throws SQLException {
			Vote vote = new Vote();
			vote.setVoteid(rs.getInt("voteid"));
			vote.setVoteName(rs.getString("voteName"));
			vote.setVoteDesc(rs.getString("voteDesc"));
			vote.setCoverPic(rs.getString("coverPic"));
			vote.setMaxSelected(rs.getInt("maxSelected"));
			return vote;
		}
	}
	
	/**
	 * @title getVoteMedia
	 * @description 根据voteid查询该投票有关的媒介信息
	 * @param voteid
	 * @return
	 */
	public Vote getVoteMedia(int voteid){
		String SQL = "SELECT coverPic FROM vote WHERE voteid = ?";
		Vote vote = null;
		
		try {
			vote = jdbcTemplate.queryForObject(SQL, new Object[]{voteid}, new VoteBasicMediaMapper());
		} catch (Exception e) {
			System.out.println("getVoteMedia: " + e.getMessage());
		}
		
		if (vote != null) {
			vote.setItemList(getItemPicList(voteid));
		}
		return vote;
	}
	
	private static final class VoteBasicMediaMapper implements RowMapper<Vote>{
		@Override
		public Vote mapRow(ResultSet rs, int arg1) throws SQLException {
			Vote vote = new Vote();
			vote.setCoverPic(rs.getString("coverPic"));
			return vote;
		}
	}
	
	/**
	 * @title getVoteForCustomer
	 * @description 根据voteid查询手机端投票及选项信息
	 * @param voteid
	 * @return
	 */
	public Vote getVoteForCustomer(int voteid){
		String SQL = "SELECT voteid, voteName, createTime, voteDesc, coverPic, "
				+ "maxSelected FROM vote WHERE voteid = ?";
		Vote vote = null;
		try {
			vote = jdbcTemplate.queryForObject(SQL, new Object[]{voteid}, new CustomerVoteinfoMapper());
		} catch (Exception e) {
			System.out.println("getVoteForCustomer: " + e.getMessage());
		}
		if (vote != null) {
			vote.setItemList(getVoteItemList(voteid));
		}
		return vote;
	}
	
	private static final class CustomerVoteinfoMapper implements RowMapper<Vote>{
		@Override
		public Vote mapRow(ResultSet rs, int arg1) throws SQLException {
			Vote vote = new Vote();
			vote.setVoteid(rs.getInt("voteid"));
			vote.setVoteName(rs.getString("voteName"));
			vote.setCreateTime(rs.getTimestamp("createTime"));
			vote.setVoteDesc(rs.getString("voteDesc"));
			vote.setCoverPic(rs.getString("coverPic"));
			vote.setMaxSelected(rs.getInt("maxSelected"));
			return vote;
		}	
	}
	
	/**
	 * @title getVoteResult
	 * @description 根据voteid查询手机端投票结果
	 * @param voteid
	 * @return
	 */
	public Vote getVoteResult(int voteid){
		String SQL = "SELECT voteid, voteName, createTime, voteDesc, coverPic, "
				+ "count FROM vote WHERE voteid = ?";
		Vote vote = null;
		try {
			vote = jdbcTemplate.queryForObject(SQL, new Object[]{voteid}, new VoteResultMapper());
		} catch (Exception e) {
			System.out.println("getVoteResult: " + e.getMessage());
		}
		if (vote != null) {
			List<VoteItem> itemList = getDetailedVoteItemList(voteid);
			int sumCount = 0;
			for (int i = 0; i < itemList.size(); i++) {
				sumCount += itemList.get(i).getCount();
			}
			for (int i = 0; i < itemList.size(); i++) {
				VoteItem item = itemList.get(i);
				item.setPercent((double)item.getCount() / sumCount * 100);
			}
			vote.setItemList(itemList);
		}
		return vote;
	}
	
	private static final class VoteResultMapper implements RowMapper<Vote>{
		@Override
		public Vote mapRow(ResultSet rs, int arg1) throws SQLException {
			Vote vote = new Vote();
			vote.setVoteid(rs.getInt("voteid"));
			vote.setVoteName(rs.getString("voteName"));
			vote.setCreateTime(rs.getTimestamp("createTime"));
			vote.setVoteDesc(rs.getString("voteDesc"));
			vote.setCoverPic(rs.getString("coverPic"));
			vote.setCount(rs.getInt("count"));
			return vote;
		}
	}
	
	/**
	 * @title getVoteItemList
	 * @description 根据voteid查询投票选项信息列表
	 * @param voteid
	 * @return
	 */
	public List<VoteItem> getVoteItemList(int voteid){
		String SQL = "SELECT itemid, itemName, itemPic FROM vote_item WHERE voteid = ?";
		List<VoteItem> itemList = null;
		try {
			itemList = jdbcTemplate.query(SQL, new Object[]{voteid}, new IteminfoMapper());
		} catch (Exception e) {
			System.out.println("getVoteItemList: " + e.getMessage());
			itemList = new ArrayList<VoteItem>();
		}
		return itemList;
	}
	
	private static final class IteminfoMapper implements RowMapper<VoteItem>{
		@Override
		public VoteItem mapRow(ResultSet rs, int arg1) throws SQLException {
			VoteItem item = new VoteItem();
			item.setItemid(rs.getInt("itemid"));
			item.setItemName(rs.getString("itemName"));
			item.setItemPic(rs.getString("itemPic"));
			return item;
		}
	}
	
	/**
	 * @title getDetailedVoteItemList
	 * @description 根据voteid查询投票选项详细信息列表
	 * @param voteid
	 * @return
	 */
	public List<VoteItem> getDetailedVoteItemList(int voteid){
		String SQL = "SELECT itemid, itemName, itemPic, count FROM vote_item WHERE voteid = ?";
		List<VoteItem> itemList = null;
		try {
			itemList = jdbcTemplate.query(SQL, new Object[]{voteid}, new DetailedIteminfoMapper());
		} catch (Exception e) {
			System.out.println("getDetailedVoteItemList: " + e.getMessage());
			itemList = new ArrayList<VoteItem>();
		}
		return itemList;
	}
	
	private static final class DetailedIteminfoMapper implements RowMapper<VoteItem>{
		@Override
		public VoteItem mapRow(ResultSet rs, int arg1) throws SQLException {
			VoteItem item = new VoteItem();
			item.setItemid(rs.getInt("itemid"));
			item.setItemName(rs.getString("itemName"));
			item.setItemPic(rs.getString("itemPic"));
			item.setCount(rs.getInt("count"));
			return item;
		}
	}
	
	/**
	 * @title getItemPicList
	 * @description 根据voteid查询投票选项图片信息列表
	 * @param voteid
	 * @return
	 */
	public List<VoteItem> getItemPicList(int voteid){
		String SQL = "SELECT itemPic FROM vote_item WHERE voteid = ?";
		List<VoteItem> itemList = null;
		try {
			itemList = jdbcTemplate.query(SQL, new Object[]{voteid}, new ItemPicMapper());
		} catch (Exception e) {
			System.out.println("getItemPicList: " + e.getMessage());
			itemList = new ArrayList<VoteItem>();
		}
		return itemList;
	}
	
	private static final class ItemPicMapper implements RowMapper<VoteItem>{
		@Override
		public VoteItem mapRow(ResultSet rs, int arg1) throws SQLException {
			VoteItem item = new VoteItem();
			item.setItemPic(rs.getString("itemPic"));
			return item;
		}
	}
	
	/**
	 * @title getWebsiteidByVoteid
	 * @description 根据voteid查询同一应用下的websiteid
	 * @param voteid
	 * @return
	 */
	public Integer getWebsiteidByVoteid(int voteid){
		Integer websiteid = null;
		String SQL = "SELECT W.websiteid FROM website W, vote V WHERE W.appid = V.appid AND V.voteid = ?";
		try {
			websiteid = jdbcTemplate.queryForObject(SQL, new Object[]{voteid}, new WebsiteidMapper());
		} catch (Exception e) {
			System.out.println("getWebsiteidByVoteid: " + e.getMessage());
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
}
