package branch.dao;

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

import branch.Branch;
import branch.BranchClass;

/**
 * @Title: BranchDAO
 * @Description: DAO for branch and branchclass model
 * @Company: Tuka
 * @author ben
 * @date 2014年4月24日
 */
public class BranchDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
    
	//insert
	/**
	 * @title insertBranchClass
	 * @description 插入分店类别信息及其关联的分店记录
	 * @param branchClass
	 * @return
	 */
	public int insertBranchClass(final BranchClass branchClass) {
		final String SQL = "INSERT INTO branchclass (classid, storeSid, className, "
				+ "createTime) VALUES (default, ?, ?, ?)";
		KeyHolder kHolder = new GeneratedKeyHolder();
		int result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setInt(1, branchClass.getStoreSid());
		        ps.setString(2, branchClass.getClassName());
		        ps.setTimestamp(3, branchClass.getCreateTime());
		        return ps;
		    }
		}, kHolder);
		if (result > 0) {
			int classid = kHolder.getKey().intValue();
			insertBCRByClassid(classid, branchClass.getBranchSidList());
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * @title insertBranch
	 * @description 插入分店信息、分店图片信息及其关联的分店类别记录
	 * @param branch
	 * @return
	 */
	public int insertBranch(final Branch branch) {
		final String SQL = "INSERT INTO storeuser (sid, roleid, username, "
				+ "password, createDate, storeName, phone, cellPhone, address, "
				+ "lng, lat) VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		KeyHolder kHolder = new GeneratedKeyHolder();
		int result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setInt(1, branch.getRoleid());
		        ps.setString(2, branch.getUsername());
		        ps.setString(3, branch.getPassword());
		        ps.setTimestamp(4, branch.getCreateDate());
		        ps.setString(5, branch.getStoreName());
		        ps.setString(6, branch.getPhone());
		        ps.setString(7, branch.getPhone());
		        ps.setString(8, branch.getAddress());
		        ps.setBigDecimal(9, branch.getLng());
		        ps.setBigDecimal(10, branch.getLat());
		        return ps;
		    }
		}, kHolder);
		if (result > 0) {
			int branchSid = kHolder.getKey().intValue();
			if(!updateImageCache("delete", branch.getImageList(), null)) {
				return -1;
			}
			insertImageList(branchSid, branch.getImageList());
			insertBCRByBranchSid(branchSid, branch.getClassidList());
			insertBSR(branchSid, branch.getStoreSid());
			return result;
		} else {
			return 0;	
		}
	}
	
	/**
	 * @title insertImageList
	 * @description 插入分店图片信息
	 * @param branchSid
	 * @param imageList
	 */
	private void insertImageList(int branchSid, List<String> imageList) {
		String SQL = "INSERT INTO storeimage (id, sid, imagePath) VALUES (default, ?, ?)";
		for (int i = 0; i < imageList.size(); i++) {
			jdbcTemplate.update(SQL, branchSid, imageList.get(i));
		}
	}
	
	/**
	 * @title insertBCRByClassid
	 * @description 根据分店类别id插入分店Branch与分店类别BranchClass关系记录
	 * @param classid
	 * @param branchSidList
	 * @return
	 */
	private void insertBCRByClassid(int classid, List<Integer> branchSidList) {
		String SQL = "INSERT INTO branch_branchclass (id, branchSid, classid) VALUES (default, ?, ?)";
		for (int i = 0; i < branchSidList.size(); i++) {
			jdbcTemplate.update(SQL, branchSidList.get(i), classid);
		}
	}
	
	/**
	 * @title insertBCRByBranchSid
	 * @description 根据分店id插入分店Branch与分店类别BranchClass关系记录
	 * @param branchSid
	 * @param classidList
	 */
	private void insertBCRByBranchSid(int branchSid, List<Integer> classidList) {
		String SQL = "INSERT INTO branch_branchclass (id, branchSid, classid) VALUES (default, ?, ?)";
		for (int i = 0; i < classidList.size(); i++) {
			jdbcTemplate.update(SQL, branchSid, classidList.get(i));
		}
	}
	
	/**
	 * @title insertBSR
	 * @description 根据分店id和商家id记录两者对应关系
	 * @param branchSid
	 * @param storeSid
	 * @return
	 */
	private int insertBSR(int branchSid, int storeSid) {
		String SQL = "INSERT INTO branch_store (id, branchSid, storeSid) VALUES (default, ?, ?)";
		int result = jdbcTemplate.update(SQL, branchSid, storeSid);
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
	 * @title deleteBranchClass
	 * @description 根据分店类别id删除分店类别信息及其与分店的关联信息
	 * @param classid
	 * @return
	 */
	public int deleteBranchClass(int classid) {
		String SQL = "DELETE FROM branchclass WHERE classid = ?";
		deleteBCRByClassid(classid);
		int result = jdbcTemplate.update(SQL, classid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteBranch
	 * @description 根据分店id删除分店信息、分店图片信息及其与分店类别和商家的关联信息
	 * @param branchSid
	 * @return
	 */
	public int deleteBranch(int branchSid) {
		String SQL = "DELETE FROM storeuser WHERE sid = ?";
		updateImageCache("insert", getImageList(branchSid), null);
		deleteImageList(branchSid);
		deleteBCRByBranchSid(branchSid);
		deleteBSR(branchSid);
		int result = jdbcTemplate.update(SQL, branchSid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteImageList
	 * @description 根据分店id删除分店图片信息
	 * @param branchSid
	 * @return
	 */
	private int deleteImageList(int branchSid) {
		String SQL = "DELETE FROM storeimage WHERE sid = ?";
		int result = jdbcTemplate.update(SQL, branchSid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteBCRByClassid
	 * @description 根据分店类别id删除其与分店的关联信息
	 * @param classid
	 * @return
	 */
	private int deleteBCRByClassid(int classid){
		String SQL = "DELETE FROM branch_branchclass WHERE classid = ?";
		int result = jdbcTemplate.update(SQL, classid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteBCRByBranchSid
	 * @description 根据分店id删除其与分店类别的关联信息
	 * @param branchSid
	 * @return
	 */
	private int deleteBCRByBranchSid(int branchSid){
		String SQL = "DELETE FROM branch_branchclass WHERE branchSid = ?";
		int result = jdbcTemplate.update(SQL, branchSid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteBSR
	 * @description 根据分店id删除其与商家的关联信息
	 * @param branchSid
	 * @return
	 */
	private int deleteBSR(int branchSid) {
		String SQL = "DELETE FROM branch_store WHERE branchSid = ?";
		int result = jdbcTemplate.update(SQL, branchSid);
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
	 * @title updateBranchClass
	 * @description 更新分店类别信息及其与分店的关联信息
	 * @param branchClass
	 * @return
	 */
	public int updateBranchClass(BranchClass branchClass) {
		String SQL = "UPDATE branchclass SET className = ? WHERE classid = ?";
		int result = jdbcTemplate.update(SQL, branchClass.getClassName(), branchClass.getClassid());
		if (result > 0) {
			deleteBCRByClassid(branchClass.getClassid());
			insertBCRByClassid(branchClass.getClassid(), branchClass.getBranchSidList());
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * @title updateBranch
	 * @description 更新分店信息、分店图片信息及其与分店类别和商家的关联信息
	 * @param branch
	 * @return
	 */
	public int updateBranch(Branch branch) {
		String SQL = "UPDATE storeuser SET roleid = ?, username = ?, storeName = ?, "
				+ "phone = ?, cellPhone = ?, address = ?, lng = ?, lat = ? WHERE sid = ?";
		int result = jdbcTemplate.update(SQL, branch.getRoleid(), branch.getUsername(), 
				branch.getStoreName(), branch.getPhone(), branch.getPhone(), branch.getAddress(), 
				branch.getLng(), branch.getLat(), branch.getBranchSid());
		if (result > 0) {
			int branchSid = branch.getBranchSid();
			List<String> currentImages = branch.getImageList();
			if (!updateImageCache("update", getImageList(branchSid), currentImages)) {
				return -1;
			}
			deleteImageList(branchSid);
			deleteBCRByBranchSid(branchSid);
			deleteBSR(branchSid);
			insertImageList(branchSid, currentImages);
			insertBCRByBranchSid(branchSid, branch.getClassidList());
			insertBSR(branchSid, branch.getStoreSid());
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * @title updateImageCache
	 * @description 管理临时表记录
	 * @param action
	 * @param imageList
	 * @param newList
	 * @return
	 */
	private boolean updateImageCache(String action, List<String> imageList, List<String> newList) {
		Timestamp current = new Timestamp(System.currentTimeMillis());
		boolean result = false;
		switch (action) {
		case "delete":
			result = true;
			for (int i = 0; i < imageList.size(); i++) {
				if (deleteImageTempRecord(imageList.get(i)) == 0) {
					result = false;
				}
			}
			break;
		case "insert":
			result = true;
			for (int i = 0; i < imageList.size(); i++) {
				insertImageTempRecord(imageList.get(i), current);
			}
			break;
		case "update":
			result = true;
			for (int i = 0; i < imageList.size(); i++) {
				String imagePath = imageList.get(i);
				if (!newList.contains(imagePath)) {
					insertImageTempRecord(imagePath, current);
				}
			}
			for (int i = 0; i < newList.size(); i++) {
				String imagePath = newList.get(i);
				if (!imageList.contains(imagePath)) {
					if(deleteImageTempRecord(imagePath) == 0) {
						result = false;
					}
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
	 * @title getDetailedClassinfos
	 * @description 根据商户id查询账户下的分店类别详细信息列表 (classid, className, createTime, branchCount)
	 * @param storeSid
	 * @return
	 */
	public List<BranchClass> getDetailedClassinfos(int storeSid) {
		List<BranchClass> classList = null;
		String SQL = "SELECT classid, className, createTime FROM branchclass "
				+ "WHERE storeSid = ? ORDER BY createTime DESC";
		try {
			classList = jdbcTemplate.query(SQL, new Object[]{storeSid}, new DetailedClassinfoMapper());
		} catch (Exception e) {
			System.out.println("getDetailedClassinfos: " + e.getMessage());
			classList = new ArrayList<BranchClass>();
		}		
		for (int i = 0; i < classList.size(); i++) {
			int count = getBranchCount(classList.get(i).getClassid());
			classList.get(i).setBranchCount(count);
		}
		return classList;
	}

	/**
	 * @title getBasicClassinfos
	 * @description 根据商户id查询账户下的分店类别概要信息 (classid, className)
	 * @param storeSid
	 * @return
	 */
	public List<BranchClass> getBasicClassinfos(int storeSid) {
		List<BranchClass> classList = null;
		String SQL = "SELECT classid, className FROM branchclass WHERE storeSid = ?"
				+ " ORDER BY classid ASC";
		try {
			classList = jdbcTemplate.query(SQL, new Object[]{storeSid}, new BasicClassinfoMapper());
		} catch (Exception e) {
			System.out.println("getBasicClassinfos: " + e.getMessage());
			classList = new ArrayList<BranchClass>();
		}
		return classList;
	}
	
	/**
	 * @title getClassContent
	 * @description 根据分店类别id查询分店类别详细信息 (classid, className, branchSidList)
	 * @param classid
	 * @return
	 */
	public BranchClass getClassContent(int classid) {
		BranchClass branchClass = null;
		String SQL = "SELECT classid, className FROM branchclass WHERE classid = ?";
		try {
			branchClass = jdbcTemplate.queryForObject(SQL, new Object[]{classid}, new BasicClassinfoMapper());
		} catch (Exception e) {
			System.out.println("getClassContent: " + e.getMessage());
		}		
		if (branchClass != null) {
			branchClass.setBranchSidList(getBranchSidListByClassid(classid));
		}
		return branchClass;
	}
	
	/**
	 * @title getBranchSidList
	 * @description 根据商家id查询账户下所有的分店id列表
	 * @param storeSid
	 * @return
	 */
	private List<Integer> getBranchSidList(int storeSid){
		List<Integer> branchSidList = null;
		String SQL = "SELECT branchSid FROM branch_store WHERE storeSid = ?";
		try {
			branchSidList = jdbcTemplate.query(SQL, new Object[]{storeSid}, new BranchSidMapper());
		} catch (Exception e) {
			System.out.println("getBranchSidList: " + e.getMessage());
			branchSidList = new ArrayList<Integer>();
		}
		return branchSidList;
	}
	/**
	 * @title getBranchSidListByClassid
	 * @description 根据分店类别查找其关联的分店id列表
	 * @param classid
	 * @return
	 */
	private List<Integer> getBranchSidListByClassid(int classid) {
		List<Integer> branchSidList = null;
		String SQL = "SELECT branchSid FROM branch_branchclass WHERE classid = ?";
		try {
			branchSidList = jdbcTemplate.query(SQL, new Object[]{classid}, new BranchSidMapper());
		} catch (Exception e) {
			System.out.println("getBranchSidListByClassid: " + e.getMessage());
			branchSidList = new ArrayList<Integer>();
		}
		return branchSidList;
	}

	/**
	 * @title getBranchCount
	 * @description 根据classid查询分店类别下的分店数目
	 * @param classid
	 * @return
	 */
	private int getBranchCount(int classid) {
		int count = 0;
		String SQL = "SELECT COUNT(*) FROM branch_branchclass WHERE classid = ?";
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, classid);
		} catch (Exception e) {
			System.out.println("getBranchCount: " + e.getMessage());
		}
		return count;
	}
	
	private static final class DetailedClassinfoMapper implements RowMapper<BranchClass>{
		@Override
		public BranchClass mapRow(ResultSet rs, int arg1) throws SQLException {
			BranchClass branchClass = new BranchClass();
			branchClass.setClassid(rs.getInt("classid"));
			branchClass.setClassName(rs.getString("className"));
			branchClass.setCreateTime(rs.getTimestamp("createTime"));
			return branchClass;
		}
	}
	
	private static final class BasicClassinfoMapper implements RowMapper<BranchClass>{
		@Override
		public BranchClass mapRow(ResultSet rs, int arg1) throws SQLException {
			BranchClass branchClass = new BranchClass();
			branchClass.setClassid(rs.getInt("classid"));
			branchClass.setClassName(rs.getString("className"));
			return branchClass;
		}	
	}
	
	private static final class BranchSidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer branchSid = rs.getInt("branchSid");
			return branchSid;
		}
	}

	/**
	 * @title getDetailedBranchinfos
	 * @description 根据商家id查询账户下所有的分店详细信息列表 (sid, username, createDate, storeName)
	 * @param storeSid
	 * @return
	 */
	public List<Branch> getDetailedBranchinfos(int storeSid) {
		List<Branch> branchList = new ArrayList<Branch>();
		String SQL = "SELECT sid, username, createDate, storeName FROM storeuser"
				+ " WHERE sid = ? ORDER BY createDate DESC";
		List<Integer> branchSidList = getBranchSidList(storeSid);
		for (int i = 0; i < branchSidList.size(); i++) {
			try {
				Branch temp = jdbcTemplate.queryForObject(SQL, new Object[]{
						branchSidList.get(i)}, new DetailedBranchinfoMapper());
				branchList.add(temp);
			} catch (Exception e) {
				System.out.println("getDetailedBranchinfos: " + e.getMessage());
				continue;
			}
		}
		return branchList;
	}

	/**
	 * @title getDetailedBranchinfosByClassid
	 * @description 根据分店类别id查询所关联的分店详细信息列表 (sid, username, createDate, storeName)
	 * @param classid
	 * @return
	 */
	public List<Branch> getDetailedBranchinfosByClassid(int classid) {
		List<Branch> branchList = new ArrayList<Branch>();
		String SQL = "SELECT sid, username, createDate, storeName FROM storeuser"
				+ " WHERE sid = ? ORDER BY createDate DESC";
		List<Integer> branchSidList = getBranchSidListByClassid(classid);
		for (int i = 0; i < branchSidList.size(); i++) {
			try {
				Branch temp = jdbcTemplate.queryForObject(SQL, new Object[]{
						branchSidList.get(i)}, new DetailedBranchinfoMapper());
				branchList.add(temp);
			} catch (Exception e) {
				System.out.println("getDetailedBranchinfosByClassid: " + e.getMessage());
				continue;
			}
		}
		return branchList;
	}
	
	/**
	 * @title getBasicBranchinfos
	 * @description 根据商家id查询账户下所有的分店概要信息列表 (sid, storeName)
	 * @param storeSid
	 * @return
	 */
	public List<Branch> getBasicBranchinfos(int storeSid) {
		List<Branch> branchList = new ArrayList<Branch>();
		String SQL = "SELECT sid, storeName FROM storeuser WHERE sid = ?";
		List<Integer> branchSidList = getBranchSidList(storeSid);
		for (int i = 0; i < branchSidList.size(); i++) {
			try {
				Branch temp = jdbcTemplate.queryForObject(SQL, new Object[]{
						branchSidList.get(i)}, new BasicBranchinfoMapper());
				branchList.add(temp);
			} catch (Exception e) {
				System.out.println("getBasicBranchinfos: " + e.getMessage());
				continue;
			}
		}
		return branchList;
	}

	/**
	 * @title getBranchContent
	 * @description 根据分店id查询分店详细信息 (sid, roleid, username, storeName, phone, 
	 * address, lng, lat, imageList, classidList)
	 * @param branchSid
	 * @return
	 */
	public Branch getBranchContent(int branchSid) {
		Branch branch = null;
		String SQL = "SELECT sid, roleid, username, storeName, phone, address, "
				+ "lng, lat FROM storeuser WHERE sid = ?";
		try {
			branch = jdbcTemplate.queryForObject(SQL, new Object[]{branchSid}, new BranchContentMapper());
		} catch (Exception e) {
			System.out.println("getBranchContent: " + e.getMessage());
		}
		if (branch != null) {
			branch.setImageList(getImageList(branchSid));
			branch.setClassidList(getClassidList(branchSid));
		}
		return branch;
	}

	/**
	 * @title getClassidList
	 * @description 根据分店id查询所关联的所有分店类别id列表
	 * @param branchSid
	 * @return
	 */
	private List<Integer> getClassidList(int branchSid) {
		List<Integer> classidList = null;
		String SQL = "SELECT classid FROM branch_branchclass WHERE branchSid = ?";
		try {
			classidList = jdbcTemplate.query(SQL, new Object[]{branchSid}, new ClassidMapper());
		} catch (Exception e) {
			System.out.println("getClassidList: " + e.getMessage());
			classidList = new ArrayList<Integer>();
		}
		return classidList;
	}

	/**
	 * @title getImageList
	 * @description 根据分店id查询所关联的所有图片地址列表
	 * @param branchSid
	 * @return
	 */
	private List<String> getImageList(int branchSid) {
		List<String> imageList = null;
		String SQL = "SELECT imagePath FROM storeimage WHERE sid = ?";
		try {
			imageList = jdbcTemplate.query(SQL, new Object[]{branchSid}, new ImagePathMapper());
		} catch (Exception e) {
			System.out.println("getImageList: " + e.getMessage());
			imageList = new ArrayList<String>();
		}
		return imageList;
	}

	private static final class DetailedBranchinfoMapper implements RowMapper<Branch>{
		@Override
		public Branch mapRow(ResultSet rs, int arg1) throws SQLException {
			Branch branch = new Branch();
			branch.setBranchSid(rs.getInt("sid"));
			branch.setUsername(rs.getString("username"));
			branch.setCreateDate(rs.getTimestamp("createDate"));
			branch.setStoreName(rs.getString("storeName"));
			return branch;
		}
	}
	
	private static final class BasicBranchinfoMapper implements RowMapper<Branch>{
		@Override
		public Branch mapRow(ResultSet rs, int arg1) throws SQLException {
			Branch branch = new Branch();
			branch.setBranchSid(rs.getInt("sid"));
			branch.setStoreName(rs.getString("storeName"));
			return branch;
		}
	}
	
	private static final class BranchContentMapper implements RowMapper<Branch>{
		@Override
		public Branch mapRow(ResultSet rs, int arg1) throws SQLException {
			Branch branch = new Branch();
			branch.setBranchSid(rs.getInt("sid"));
			branch.setRoleid(rs.getInt("roleid"));
			branch.setUsername(rs.getString("username"));
	        branch.setStoreName(rs.getString("storeName"));
	        branch.setPhone(rs.getString("phone"));
	        branch.setAddress(rs.getString("address"));
	        branch.setLng(rs.getBigDecimal("lng"));
	        branch.setLat(rs.getBigDecimal("lat"));
			return branch;
		}
	}
	
	private static final class ImagePathMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String imagePath = rs.getString("imagePath");
			return imagePath;
		}
	}
	
	private static final class ClassidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer classid = rs.getInt("classid");
			return classid;
		}
	}
}
