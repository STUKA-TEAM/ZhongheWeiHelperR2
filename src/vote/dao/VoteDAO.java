package vote.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
	public int insertVote(final Vote vote){
		return 0;
	}
	
	public int insertVoteContent(VoteContent content){
		return 0;
	}
	//delete
	public int deleteVote(int voteid){
		return 0;
	}
	
	//update
	public int updateVote(Vote vote){
		return 0;
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
