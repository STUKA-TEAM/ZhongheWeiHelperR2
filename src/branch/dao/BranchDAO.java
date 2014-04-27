package branch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
	public int insertBranchClass(BranchClass branchClass) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int insertBranch(Branch branch) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//delete
	public int deleteBranchClass(int classid) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int deleteBranch(int branchSid) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//update
	public int updateBranchClass(BranchClass branchClass) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int updateBranch(Branch branch) {
		// TODO Auto-generated method stub
		return 0;
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
