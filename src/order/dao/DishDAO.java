package order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import order.Dish;
import order.DishClass;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * @Title: DishDAO
 * @Description: DAO for dish and dishclass model
 * @Company: tuka
 * @author ben
 * @date 2014年4月29日
 */
public class DishDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//insert
	/**
	 * @title insertDishClass
	 * @description 插入菜品类别信息及其关联的菜品记录
	 * @param dishClass
	 * @return
	 */
	public int insertDishClass(final DishClass dishClass) {
		final String SQL = "INSERT INTO dishclass (classid, appid, className, "
				+ "createTime) VALUES (default, ?, ?, ?)";
		KeyHolder kHolder = new GeneratedKeyHolder();
		int result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, dishClass.getAppid());
		        ps.setString(2, dishClass.getClassName());
		        ps.setTimestamp(3, dishClass.getCreateTime());
		        return ps;
		    }
		}, kHolder);
		if (result > 0) {
			int classid = kHolder.getKey().intValue();
			insertDCRByClassid(classid, dishClass.getDishidList());
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * @title insertDish
	 * @description 插入菜品信息及其关联的菜品类别与分店记录
	 * @param dish
	 * @return
	 */
	public int insertDish(final Dish dish) {
		final String SQL = "INSERT INTO dish (dishid, appid, dishName, createTime,"
				+ " dishPic, dishDesc, price, dishUnit, recomNum) VALUES (default,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?)";
		KeyHolder kHolder = new GeneratedKeyHolder();
		int result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, dish.getAppid());
		        ps.setString(2, dish.getDishName());
		        ps.setTimestamp(3, dish.getCreateTime());
		        ps.setString(4, dish.getDishPic());
		        ps.setString(5, dish.getDishDesc());
		        ps.setInt(6, dish.getPrice());
		        ps.setString(7, dish.getDishUnit());
		        ps.setInt(8, dish.getRecomNum());
		        return ps;
		    }
		}, kHolder);
		if (result > 0) {
			int dishid = kHolder.getKey().intValue();
			String dishPic = dish.getDishPic();
			if(dishPic != null && !updateImageCache("delete", dishPic, null)) {
				return -1;
			}
			insertDCRByDishid(dishid, dish.getClassidList());
			insertDBR(dishid, dish.getAppid(), dish.getPrice());
			return result;
		} else {
			return 0;	
		}
	}
	
	/**
	 * @title insertDCRByClassid
	 * @description 根据菜品类别id插入菜品Dish与菜品类别DishClass关系记录
	 * @param classid
	 * @param dishidList
	 */
	private void insertDCRByClassid(int classid, List<Integer> dishidList) {
		String SQL = "INSERT INTO dish_dishclass (id, dishid, classid) VALUES (default, ?, ?)";
		for (int i = 0, j = dishidList.size(); i < j; i++) {
			jdbcTemplate.update(SQL, dishidList.get(i), classid);
		}
	}
	
	/**
	 * @title insertDCRByDishid
	 * @description 根据菜品id插入菜品Dish与菜品类别DishClass关系记录
	 * @param dishid
	 * @param classidList
	 */
	private void insertDCRByDishid(int dishid, List<Integer> classidList) {
		String SQL = "INSERT INTO dish_dishclass (id, dishid, classid) VALUES (default, ?, ?)";
		for (int i = 0, j = classidList.size(); i < j; i++) {
			jdbcTemplate.update(SQL, dishid, classidList.get(i));
		}
	}
	
	/**
	 * @title insertDBR
	 * @description 根据菜品id插入菜品Dish与分店Branch关联记录
	 * @param dishid
	 * @param appid
	 * @param price
	 */
	private void insertDBR(int dishid, String appid, int price) {
		String SQL = "INSERT INTO dish_branch (id, dishid, branchSid, price, available) VALUES (default, ?, ?, ?, ?)";
		List<Integer> branchSidList = getBranchSidList(appid);
		for (int i = 0, j = branchSidList.size(); i < j; i++) {
			jdbcTemplate.update(SQL, dishid, branchSidList.get(i), price, 0);
		}
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
	 * @title deleteDishClass
	 * @description 根据菜品类别id删除菜品类别信息及其与菜品的关联记录
	 * @param classid
	 * @return
	 */
	public int deleteDishClass(int classid) {
		String SQL = "DELETE FROM dishclass WHERE classid = ?";
		deleteDCRByClassid(classid);
		int result = jdbcTemplate.update(SQL, classid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteDishClass
	 * @description 根据appid删除该应用下所有的菜品类别信息及其与菜品的关联记录
	 * @param appid
	 * @return
	 */
	public int deleteDishClass(String appid) {
		String SQL = "DELETE FROM dishclass WHERE appid = ?";
		List<Integer> classidList = getClassidList(appid);
		for (int i = 0, j = classidList.size(); i < j; i++) {
			deleteDCRByClassid(classidList.get(i));
		}
		int result = jdbcTemplate.update(SQL, appid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteDish
	 * @description 根据菜品id删除菜品信息及其与菜品类别、分店、订单的关联信息
	 * @param dishid
	 * @return
	 */
	public int deleteDish(int dishid) {
		String SQL = "DELETE FROM dish WHERE dishid = ?";
		Dish dish = getDishMedia(dishid);
		if (dish != null) {
			String dishPic = dish.getDishPic();
			if (dishPic != null) {
				updateImageCache("insert", dishPic, null);
			}
		} else {
			return 0;
		}
		deleteDCRByDishid(dishid);
		deleteDBR(dishid);
		deleteDOR(dishid);
		int result = jdbcTemplate.update(SQL, dishid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteDish
	 * @description 根据appid删除该应用下所有的菜品信息及其与菜品类别、分店、订单的关联记录
	 * @param appid
	 * @return
	 */
	public int deleteDish(String appid) {
		String SQL = "DELETE FROM dish WHERE appid = ?";
		List<Integer> dishidList = getDishidList(appid);
		for (int i = 0, j = dishidList.size(); i < j; i++) {
			int dishid = dishidList.get(i);
			Dish dish = getDishMedia(dishid);
			if (dish != null) {
				String dishPic = dish.getDishPic();
				if (dishPic != null) {
					updateImageCache("insert", dishPic, null);
				}
			} else {
				return 0;
			}
			deleteDCRByDishid(dishid);
			deleteDBR(dishid);
			deleteDOR(dishid);
		}
		int result = jdbcTemplate.update(SQL, appid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteDCRByClassid
	 * @description 根据菜品类别id删除菜品Dish与该菜品类别DishClass的关联记录
	 * @param classid
	 * @return
	 */
	private int deleteDCRByClassid(int classid) {
		String SQL = "DELETE FROM dish_dishclass WHERE classid = ?";
		int result = jdbcTemplate.update(SQL, classid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteDCRByDishid
	 * @description 根据菜品id删除该菜品Dish与菜品类别DishClass的关联记录
	 * @param dishid
	 * @return
	 */
	private int deleteDCRByDishid(int dishid) {
		String SQL = "DELETE FROM dish_dishclass WHERE dishid = ?";
		int result = jdbcTemplate.update(SQL, dishid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteDBR
	 * @description 根据菜品id删除该菜品Dish与分店Branch的关联记录
	 * @param dishid
	 * @return
	 */
	private int deleteDBR(int dishid) {
		String SQL = "DELETE FROM dish_branch WHERE dishid = ?";
		int result = jdbcTemplate.update(SQL, dishid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteDOR
	 * @description 根据菜品id删除该菜品Dish与订单Order的关联记录
	 * @param dishid
	 * @return
	 */
	private int deleteDOR(int dishid) {
		String SQL = "DELETE FROM dish_order WHERE dishid = ?";
		int result = jdbcTemplate.update(SQL, dishid);
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
	 * @title updateDishClass
	 * @description 更新菜品类别信息及其与菜品的关联信息
	 * @param dishClass
	 * @return
	 */
	public int updateDishClass(DishClass dishClass) {
		String SQL = "UPDATE dishclass SET className = ? WHERE classid = ?";
		int classid = dishClass.getClassid();
		int result = jdbcTemplate.update(SQL, dishClass.getClassName(), classid);
		if (result > 0) {
			deleteDCRByClassid(classid);
			insertDCRByClassid(classid, dishClass.getDishidList());
			return result;
		} else {
			return 0;
		}
	}

	/**
	 * @title updateDish
	 * @description 更新菜品信息及其与菜品类别的关联信息
	 * @param dish
	 * @return
	 */
	public int updateDish(Dish dish) {
		String SQL = "UPDATE dish SET dishName = ?, dishPic = ?, dishDesc = ?, "
				+ "price = ?, dishUnit = ?, recomNum = ? WHERE dishid = ?";
		int dishid = dish.getDishid();
		Dish temp = getDishMedia(dishid);
		if (temp != null) {
			String dishPic = temp.getDishPic();
			if (dishPic == null) {
				dishPic = "";
			}
			if (!updateImageCache("update", dishPic, dish.getDishPic())) {
				return -1;
			}
		} else {
			return 0;
		}
		int result = jdbcTemplate.update(SQL, dish.getDishName(), dish.getDishPic(),
				dish.getDishDesc(), dish.getPrice(), dish.getDishUnit(), dish.getRecomNum(), dishid);
		if (result > 0) {
			deleteDCRByDishid(dishid);
			insertDCRByDishid(dishid, dish.getClassidList());
			return result;
		} else {
			return 0;	
		}
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
	 * @title getDetailedClassinfos
	 * @description 根据appid查询应用下的菜品类别详细信息列表 (classid, className, createTime, dishCount)
	 * @param appid
	 * @return
	 */
	public List<DishClass> getDetailedClassinfos(String appid) {
		List<DishClass> classList = null;
		String SQL = "SELECT classid, className, createTime FROM dishclass "
				+ "WHERE appid = ? ORDER BY createTime DESC";
		try {
			classList = jdbcTemplate.query(SQL, new Object[]{appid}, new DetailedClassinfoMapper());
		} catch (Exception e) {
			System.out.println("getDetailedClassinfos: " + e.getMessage());
			classList = new ArrayList<DishClass>();
		}		
		for (int i = 0, j = classList.size(); i < j; i++) {
			DishClass dishClass = classList.get(i);
			int count = getDishCount(dishClass.getClassid());
			dishClass.setDishCount(count);
		}
		return classList;
	}

	/**
	 * @title getBasicClassinfos
	 * @description 根据appid查询应用下的菜品类别概要信息 (classid, className)
	 * @param appid
	 * @return
	 */
	public List<DishClass> getBasicClassinfos(String appid) {
		List<DishClass> classList = null;
		String SQL = "SELECT classid, className FROM dishclass WHERE appid = ?";
		try {
			classList = jdbcTemplate.query(SQL, new Object[]{appid}, new BasicClassinfoMapper());
		} catch (Exception e) {
			System.out.println("getBasicClassinfos: " + e.getMessage());
			classList = new ArrayList<DishClass>();
		}
		return classList;
	}
	
	/**
	 * @title getClassContent
	 * @description 根据菜品类别id查询菜品类别详细信息 (classid, className, dishidList)
	 * @param classid
	 * @return
	 */
	public DishClass getClassContent(int classid) {
		DishClass dishClass = null;
		String SQL = "SELECT classid, className FROM dishclass WHERE classid = ?";
		try {
			dishClass = jdbcTemplate.queryForObject(SQL, new Object[]{classid}, new BasicClassinfoMapper());
		} catch (Exception e) {
			System.out.println("getClassContent: " + e.getMessage());
		}		
		if (dishClass != null) {
			dishClass.setDishidList(getDishidList(classid));
		}
		return dishClass;
	}
	
	/**
	 * @title getDishidList
	 * @description 根据菜品类别id查找其关联的菜品id列表
	 * @param classid
	 * @return
	 */
	private List<Integer> getDishidList(int classid) {
		List<Integer> dishidList = null;
		String SQL = "SELECT dishid FROM dish_dishclass WHERE classid = ?";
		try {
			dishidList = jdbcTemplate.query(SQL, new Object[]{classid}, new DishidMapper());
		} catch (Exception e) {
			System.out.println("getDishidList: " + e.getMessage());
			dishidList = new ArrayList<Integer>();
		}
		return dishidList;
	}
	
	/**
	 * @title getDishidList
	 * @description 根据appid查询该应用下所有的菜品id列表
	 * @param appid
	 * @return
	 */
	private List<Integer> getDishidList(String appid) {
		List<Integer> dishidList = null;
		String SQL = "SELECT dishid FROM dish WHERE appid = ?";
		try {
			dishidList = jdbcTemplate.query(SQL, new Object[]{appid}, new DishidMapper());
		} catch (Exception e) {
			System.out.println("getDishidList: " + e.getMessage());
			dishidList = new ArrayList<Integer>();
		}
		return dishidList;
	}

	/**
	 * @title getDishCount
	 * @description 根据classid查询菜品类别下的菜品数目
	 * @param classid
	 * @return
	 */
	private int getDishCount(int classid) {
		int count = 0;
		String SQL = "SELECT COUNT(*) FROM dish_dishclass WHERE classid = ?";
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, classid);
		} catch (Exception e) {
			System.out.println("getDishCount " + e.getMessage());
		}
		return count;
	}
	
	private static final class DetailedClassinfoMapper implements RowMapper<DishClass>{
		@Override
		public DishClass mapRow(ResultSet rs, int arg1) throws SQLException {
			DishClass dishClass = new DishClass();
			dishClass.setClassid(rs.getInt("classid"));
			dishClass.setClassName(rs.getString("className"));
			dishClass.setCreateTime(rs.getTimestamp("createTime"));
			return dishClass;
		}
	}
	
	private static final class BasicClassinfoMapper implements RowMapper<DishClass>{
		@Override
		public DishClass mapRow(ResultSet rs, int arg1) throws SQLException {
			DishClass dishClass = new DishClass();
			dishClass.setClassid(rs.getInt("classid"));
			dishClass.setClassName(rs.getString("className"));
			return dishClass;
		}
	}
	
	private static final class DishidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer dishid = rs.getInt("dishid");
			return dishid;
		}
	}
	
	/**
	 * @title getDetailedDishinfos
	 * @description 根据appid查询应用下所有的菜品详细信息列表 (dishid, dishName, createTime, dishPic)
	 * @param appid
	 * @return
	 */
	public List<Dish> getDetailedDishinfos(String appid) {
		List<Dish> dishList = null;
		String SQL = "SELECT dishid, dishName, createTime, dishPic FROM dish "
				+ "WHERE appid = ? ORDER BY createTime DESC";
		try {
			dishList = jdbcTemplate.query(SQL, new Object[]{appid}, new DetailedDishinfoMapper());
		} catch (Exception e) {
			System.out.println("getDetailedDishinfos: " + e.getMessage());
			dishList = new ArrayList<Dish>();
		}
		return dishList;
	}

	/**
	 * @title getDetailedDishinfos
	 * @description 根据菜品类别id查询所关联的菜品详细信息列表 (dishid, dishName, createTime, dishPic)
	 * @param classid
	 * @return
	 */
	public List<Dish> getDetailedDishinfos(int classid) {
		List<Dish> dishList = new ArrayList<Dish>();
		String SQL = "SELECT dishid, dishName, createTime, dishPic FROM dish "
				+ "WHERE dishid = ? ORDER BY createTime DESC";
		List<Integer> dishidList = getDishidList(classid);
		for (int i = 0, j = dishidList.size(); i < j; i++) {
			try {
				Dish dish = jdbcTemplate.queryForObject(SQL, new Object[]{
						dishidList.get(i)}, new DetailedDishinfoMapper());
				dishList.add(dish);
			} catch (Exception e) {
				System.out.println("getDetailedDishinfos: " + e.getMessage());
				continue;
			}
		}
		return dishList;
	}

	/**
	 * @title getBasicDishinfos
	 * @description 根据appid查询应用下所有的菜品概要信息列表 (dishid, dishName)
	 * @param appid
	 * @return
	 */
	public List<Dish> getBasicDishinfos(String appid) {
		List<Dish> dishList = null;
		String SQL = "SELECT dishid, dishName FROM dish WHERE appid = ?";
		try {
			dishList = jdbcTemplate.query(SQL, new Object[]{appid}, new BasicDishinfoMapper());
		} catch (Exception e) {
			System.out.println("getBasicDishinfos: " + e.getMessage());
			dishList = new ArrayList<Dish>();
		}
		return dishList;
	}
	
	/**
	 * @title getDishContent
	 * @description 根据菜品id查询菜品详细信息 (dishid, dishName, dishPic, dishDesc, price,
	 *  dishUnit, recomNum, classidList)
	 * @param dishid
	 * @return
	 */
	public Dish getDishContent(int dishid) {
		Dish dish = null;
		String SQL = "SELECT dishid, dishName, dishPic, dishDesc, price, "
				+ "dishUnit, recomNum FROM dish WHERE dishid = ?";
		try {
			dish = jdbcTemplate.queryForObject(SQL, new Object[]{dishid}, new DishContentMapper());
		} catch (Exception e) {
			System.out.println("getDishContent: " + e.getMessage());
		}
		if (dish != null) {
			dish.setClassidList(getClassidList(dishid));
		}
		return dish;
	}
	
	/**
	 * @title getDishMedia
	 * @description 根据菜品id查询菜品dish表存储的图片、视频资源信息
	 * @param dishid
	 * @return
	 */
	private Dish getDishMedia(int dishid) {
		Dish dish = null;
		String SQL = "SELECT dishPic FROM dish WHERE dishid = ?";
		try {
			dish = jdbcTemplate.queryForObject(SQL, new Object[]{dishid}, new DishMediaMapper());
		} catch (Exception e) {
			System.out.println("getDishMedia: " + e.getMessage());
		}
		return dish;
	}
	
	/**
	 * @title getClassidList
	 * @description 根据菜品id查询所关联的所有菜品类别id列表
	 * @param dishid
	 * @return
	 */
	private List<Integer> getClassidList(int dishid) {
		List<Integer> classidList = null;
		String SQL = "SELECT classid FROM dish_dishclass WHERE dishid = ?";
		try {
			classidList = jdbcTemplate.query(SQL, new Object[]{dishid}, new ClassidMapper());
		} catch (Exception e) {
			System.out.println("getClassidList: " + e.getMessage());
			classidList = new ArrayList<Integer>();
		}
		return classidList;
	}
	
	/**
	 * @title getClassidList
	 * @description 根据appid查询该应用下所有的菜品类别id列表
	 * @param appid
	 * @return
	 */
	private List<Integer> getClassidList(String appid) {
		List<Integer> classidList = null;
		String SQL = "SELECT classid FROM dishclass WHERE appid = ?";
		try {
			classidList = jdbcTemplate.query(SQL, new Object[]{appid}, new ClassidMapper());
		} catch (Exception e) {
			System.out.println("getClassidList: " + e.getMessage());
			classidList = new ArrayList<Integer>();
		}
		return classidList;
	}
	
	private static final class DetailedDishinfoMapper implements RowMapper<Dish>{
		@Override
		public Dish mapRow(ResultSet rs, int arg1) throws SQLException {
			Dish dish = new Dish();
			dish.setDishid(rs.getInt("dishid"));
			dish.setDishName(rs.getString("dishName"));
			dish.setCreateTime(rs.getTimestamp("createTime"));
			dish.setDishPic(rs.getString("dishPic"));
			return dish;
		}
	}
	
	private static final class BasicDishinfoMapper implements RowMapper<Dish>{
		@Override
		public Dish mapRow(ResultSet rs, int arg1) throws SQLException {
			Dish dish = new Dish();
			dish.setDishid(rs.getInt("dishid"));
			dish.setDishName(rs.getString("dishName"));
			return dish;
		}
	}
	
	private static final class DishContentMapper implements RowMapper<Dish>{
		@Override
		public Dish mapRow(ResultSet rs, int arg1) throws SQLException {
			Dish dish = new Dish();
			dish.setDishid(rs.getInt("dishid"));
			dish.setDishName(rs.getString("dishName"));
			dish.setDishPic(rs.getString("dishPic"));
			dish.setDishDesc(rs.getString("dishDesc"));
			dish.setPrice(rs.getInt("price"));
			dish.setDishUnit(rs.getString("dishUnit"));
			dish.setRecomNum(rs.getInt("recomNum"));
			return dish;
		}
	}
	
	private static final class DishMediaMapper implements RowMapper<Dish>{
		@Override
		public Dish mapRow(ResultSet rs, int arg1) throws SQLException {
			Dish dish = new Dish();
			dish.setDishPic(rs.getString("dishPic"));
			return dish;
		}	
	}
	
	private static final class ClassidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer classid = rs.getInt("classid");
			return classid;
		}
	}
	
	/**
	 * @title getBranchSidList
	 * @description 根据appid查询所属商家的所有分店id列表
	 * @param appid
	 * @return
	 */
	private List<Integer> getBranchSidList(String appid) {
		List<Integer> branchSidList = null;
		String SQL = "SELECT B.branchSid FROM branch_store B, storeuser_application S WHERE B.storeSid = S.sid AND S.appid = ?";
		try {
			branchSidList = jdbcTemplate.query(SQL, new Object[]{appid}, new BranchSidMapper());
		} catch (Exception e) {
			System.out.println("getBranchSidList: " + e.getMessage());
			branchSidList = new ArrayList<Integer>();
		}
		return branchSidList;
	}
	
	private static final class BranchSidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer branchSid = rs.getInt("B.branchSid");
			return branchSid;
		}
	}
}
