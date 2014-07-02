package voucher.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import voucher.Voucher;

/**
 * @Title: VoucherDAO
 * @Description: DAO for voucher model
 * @Company: tuka
 * @author ben
 * @date 2014年6月25日
 */
public class VoucherDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title insertVoucher
	 * @description 插入一条优惠券信息记录
	 * @param voucher
	 * @return
	 */
	public int insertVoucher(Voucher voucher) {
		String SQL = "INSERT INTO voucher (appid, title, createTime, description, "
				+ "coverPic, destroyKey, totalNum, isPublic) VALUES (?, ?, ?, ?, "
				+ "?, ?, ?, ?)";
		int result = jdbcTemplate.update(SQL, voucher.getAppid(), voucher.getTitle(), 
				voucher.getCreateTime(), voucher.getDescription(), voucher.getCoverPic(), 
				voucher.getDestroyKey(), voucher.getTotalNum(), voucher.getIsPublic());
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
		String SQL = "INSERT INTO image_temp_record (id, imagePath, createDate) "
				+ "VALUES (default, ?, ?)";		
		int result = jdbcTemplate.update(SQL, imagePath, current);
		return result <= 0 ? 0 : result;
	}
	
	//delete
	/**
	 * @title deleteVoucher
	 * @description 删除一条优惠券信息记录
	 * @param voucherid
	 * @return
	 */
	public int deleteVoucher(int voucherid) {
		String SQL = "DELETE FROM voucher WHERE voucherid = ?";
		Voucher voucher = getVoucherMedia(voucherid);
		if (voucher != null) {
			String coverPic = voucher.getCoverPic();
			if (coverPic != null) {
				updateImageCache("insert", coverPic, null);
			}
		} else {
			return 0;
		}
		deleteVoucherRecord(voucherid);
		int result = jdbcTemplate.update(SQL, voucherid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteVoucher
	 * @description 删除应用关联的所有优惠券信息记录
	 * @param appid
	 * @return
	 */
	public int deleteVoucher(String appid) {
		String SQL = "DELETE FROM voucher WHERE appid = ?";
		List<Voucher> voucherList = getVoucherMediaList(appid);
		for (Voucher voucher : voucherList) {
			String coverPic = voucher.getCoverPic();
			if (coverPic != null) {
				updateImageCache("insert", coverPic, null);
			}
		}
		deleteVoucherRecord(appid);
		int result = jdbcTemplate.update(SQL, appid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteVoucherRecord
	 * @description 删除某一优惠券相关的用户使用记录
	 * @param voucherid
	 * @return
	 */
	private int deleteVoucherRecord(int voucherid) {
		String SQL = "DELETE FROM voucher_record WHERE voucherid = ?";
		int result = jdbcTemplate.update(SQL, voucherid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteVoucherRecord
	 * @description 删除应用关联的所有优惠券相关的用户使用记录
	 * @param appid
	 * @return
	 */
	private int deleteVoucherRecord(String appid) {
		String SQL = "DELETE FROM voucher_record WHERE voucherid IN (SELECT "
				+ "voucherid FROM voucher WHERE appid = ?)";
		int result = jdbcTemplate.update(SQL, appid);
		return result <= 0 ? 0 : result;
	}
	
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
	
	//update
	/**
	 * @title updateVoucher
	 * @description 更新优惠券信息
	 * @param voucher
	 * @return
	 */
	public int updateVoucher(Voucher voucher) {
		String SQL = "UPDATE voucher SET title = ?, description = ?, coverPic = ?, "
				+ "destroyKey = ?, totalNum = ?, isPublic = ? WHERE voucherid = ?";
		int voucherid = voucher.getVoucherid();
		Voucher temp = getVoucherMedia(voucherid);
		if (temp != null) {
			String coverPic = temp.getCoverPic();
			if (coverPic == null) {
				coverPic = "";
			}
			if (!updateImageCache("update", coverPic, voucher.getCoverPic())) {
				return -1;
			}
		} else {
			return 0;
		}
		int result = jdbcTemplate.update(SQL, voucher.getTitle(), voucher.getDescription(), 
				voucher.getCoverPic(), voucher.getDestroyKey(), voucher.getTotalNum(), 
				voucher.getIsPublic(), voucherid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title updateImageCache
	 * @description 管理临时表记录
	 * @param action
	 * @param image
	 * @param newImage
	 * @return
	 */
	private boolean updateImageCache(String action, String image, String newImage) {
		Timestamp current = new Timestamp(System.currentTimeMillis());
		boolean result = false;
		switch (action) {
		case "delete":
			result = true;
			if (deleteImageTempRecord(image) == 0) {
				result = false;
			}
			break;
		case "insert":
			result = true;
			insertImageTempRecord(image, current);
			break;
		case "update":
			result = true;
			if (!image.equals(newImage)) {
				if (!image.equals("")) {
					insertImageTempRecord(image, current);
				}
				if (newImage != null && deleteImageTempRecord(newImage) == 0) {
					result = false;
				}
			}
			break;
		default:
			break;
		}
		return result;
	}
	
	//query
	/**
	 * @title getDetailedVoucherinfos
	 * @description 查询关联的优惠券详细信息列表(voucherid, title, createTime, destroyKey, 
	 * totalNum, isPublic, restNum)
	 * @param appid
	 * @return
	 */
	public List<Voucher> getDetailedVoucherinfos(String appid) {
		List<Voucher> voucherList = null;
		String SQL = "SELECT voucherid, title, createTime, destroyKey, totalNum, "
				+ "isPublic FROM voucher WHERE appid = ? ORDER BY createTime DESC";
		try {
			voucherList = jdbcTemplate.query(SQL, new Object[]{appid}, 
					new DetailedVoucherinfoMapper());
		} catch (Exception e) {
			System.out.println("getDetailedVoucherinfos: " + e.getMessage());
			voucherList = new ArrayList<Voucher>();
		}
		for (Voucher voucher : voucherList) {
			int restNum = voucher.getTotalNum() - getVoucherRecordCount(
					voucher.getVoucherid());
			voucher.setRestNum(restNum);
		}
		return voucherList;
	}
	
	/**
	 * @title getVoucherContent
	 * @description 查询指定的某一优惠券详细信息(voucherid, title, description, coverPic, 
	 * destroyKey, totalNum, isPublic)
	 * @param voucherid
	 * @return
	 */
	public Voucher getVoucherContent(int voucherid) {
		Voucher voucher = null;
		String SQL = "SELECT voucherid, title, description, coverPic, destroyKey, "
				+ "totalNum, isPublic FROM voucher WHERE voucherid = ?";
		try {
			voucher = jdbcTemplate.queryForObject(SQL, new Object[]{voucherid}, 
					new VoucherContentMapper());
		} catch (Exception e) {
			System.out.println("getVoucherContent: " + e.getMessage());
		}
		return voucher;
	}
	
	/**
	 * @title getVoucherMediaList
	 * @description 查询voucher表存储的图片、视频等资源信息列表
	 * @param appid
	 * @return
	 */
	private List<Voucher> getVoucherMediaList(String appid) {
		List<Voucher> voucherList = null;
		String SQL = "SELECT coverPic FROM voucher WHERE appid = ?";
		try {
			voucherList = jdbcTemplate.query(SQL, new Object[]{appid}, 
					new VoucherMediaMapper());
		} catch (Exception e) {
			System.out.println("getVoucherMediaList: " + e.getMessage());
			voucherList = new ArrayList<Voucher>();
		}
		return voucherList;
	}
	
	/**
	 * @title getVoucherMedia
	 * @description 查询voucher表存储的图片、视频等资源信息
	 * @param voucherid
	 * @return
	 */
	private Voucher getVoucherMedia(int voucherid) {
		Voucher voucher = null;
		String SQL = "SELECT coverPic FROM voucher WHERE voucherid = ?";
		try {
			voucher = jdbcTemplate.queryForObject(SQL, new Object[]{voucherid}, 
					new VoucherMediaMapper());
		} catch (Exception e) {
			System.out.println("getVoucherMedia: " + e.getMessage());
		}
		return voucher;
	}
	
	/**
	 * @title getVoucheridList
	 * @description 查询应用关联的优惠券id列表
	 * @param appid
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<Integer> getVoucheridList(String appid) {
		List<Integer> voucheridList = null;
		String SQL = "SELECT voucherid FROM voucher WHERE appid = ?";
		try {
			voucheridList = jdbcTemplate.query(SQL, new Object[]{appid}, 
					new VoucheridMapper());
		} catch (Exception e) {
			System.out.println("getVoucheridList: " + e.getMessage());
			voucheridList = new ArrayList<Integer>();
		}
		return voucheridList;
	}
	
	/**
	 * @title getVoucherRecordCount
	 * @description 查询某一优惠券被用户领取的记录总数
	 * @param voucherid
	 * @return
	 */
	private int getVoucherRecordCount(int voucherid) {
		int count = 0;
		String SQL = "SELECT COUNT(*) FROM voucher_record WHERE voucherid = ?";
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, voucherid);
		} catch (Exception e) {
			System.out.println("getVoucherRecordCount: " + e.getMessage());
		}
		return count;
	}
	
	private static final class DetailedVoucherinfoMapper implements RowMapper<Voucher>{
		@Override
		public Voucher mapRow(ResultSet rs, int arg1) throws SQLException {
			Voucher voucher = new Voucher();
			voucher.setVoucherid(rs.getInt("voucherid"));
			voucher.setTitle(rs.getString("title"));
			voucher.setCreateTime(rs.getTimestamp("createTime"));
			voucher.setDestroyKey(rs.getString("destroyKey"));
			voucher.setTotalNum(rs.getInt("totalNum"));
			voucher.setIsPublic(rs.getInt("isPublic"));
			return voucher;
		}
	}
	
	private static final class VoucherContentMapper implements RowMapper<Voucher>{
		@Override
		public Voucher mapRow(ResultSet rs, int arg1) throws SQLException {
			Voucher voucher = new Voucher();
			voucher.setVoucherid(rs.getInt("voucherid"));
			voucher.setTitle(rs.getString("title"));
			voucher.setDescription(rs.getString("description"));
			voucher.setCoverPic(rs.getString("coverPic"));
			voucher.setDestroyKey(rs.getString("destroyKey"));
			voucher.setTotalNum(rs.getInt("totalNum"));
			voucher.setIsPublic(rs.getInt("isPublic"));
			return voucher;
		}
	}
	
	private static final class VoucherMediaMapper implements RowMapper<Voucher>{
		@Override
		public Voucher mapRow(ResultSet rs, int arg1) throws SQLException {
			Voucher voucher = new Voucher();
			voucher.setCoverPic(rs.getString("coverPic"));
			return voucher;
		}
	}
	
	private static final class VoucheridMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer voucherid = rs.getInt("voucherid");
			return voucherid;
		}
	}
}
