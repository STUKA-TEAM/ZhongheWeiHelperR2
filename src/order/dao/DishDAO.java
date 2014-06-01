package order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import order.Dish;
import order.DishBranch;
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
	 * @description 插入菜品类别信息
	 * @param dishClass
	 * @return
	 */
	public int insertDishClass(DishClass dishClass) {
		String SQL = "INSERT INTO dishclass (classid, appid, className, "
				+ "createTime) VALUES (default, ?, ?, ?)";
		int result = jdbcTemplate.update(SQL, dishClass.getAppid(), 
				dishClass.getClassName(), dishClass.getCreateTime());
		return result <= 0 ? 0 : result;
	}

	/**
	 * @title insertDish
	 * @description 插入菜品信息及其关联的菜品类别与分店记录
	 * @param dish
	 * @return
	 */
	public int insertDish(final Dish dish) {
		final String SQL = "INSERT INTO dish (dishid, appid, creatorSid, dishName, "
				+ "createTime, dishPic, dishDesc, price, dishUnit, recomNum) VALUES "
				+ "(default, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		KeyHolder kHolder = new GeneratedKeyHolder();
		int result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, dish.getAppid());
		        ps.setInt(2, dish.getCreatorSid());
		        ps.setString(3, dish.getDishName());
		        ps.setTimestamp(4, dish.getCreateTime());
		        ps.setString(5, dish.getDishPic());
		        ps.setString(6, dish.getDishDesc());
		        ps.setInt(7, dish.getPrice());
		        ps.setString(8, dish.getDishUnit());
		        ps.setInt(9, dish.getRecomNum());
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
	 * @title insertDishOrder
	 * @description 插入新的菜品订单记录
	 * @param openid
	 * @param branchSid
	 * @param dishid
	 * @return
	 */
	public int insertDishOrder(String openid, int branchSid, int dishid) {
		String SQL = "INSERT INTO dish_order (openid, branchSid, dishid) VALUES (?, ?, ?)";
		int result = jdbcTemplate.update(SQL, openid, branchSid, dishid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title insertDCRByClassid
	 * @description 根据菜品类别id插入菜品Dish与菜品类别DishClass关系记录
	 * @param classid
	 * @param dishidList
	 */
	@SuppressWarnings("unused")
	private void insertDCRByClassid(int classid, List<Integer> dishidList) {
		String SQL = "INSERT INTO dish_dishclass (id, dishid, classid) VALUES "
				+ "(default, ?, ?)";
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
		String SQL = "INSERT INTO dish_dishclass (id, dishid, classid) VALUES "
				+ "(default, ?, ?)";
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
		String SQL = "INSERT INTO dish_branch (id, dishid, branchSid, price, "
				+ "available) VALUES (default, ?, ?, ?, ?)";
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
		String SQL = "INSERT INTO image_temp_record (id, imagePath, createDate) "
				+ "VALUES (default, ?, ?)";		
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
	 * @title deleteDishOrder
	 * @description 删除用户在某分店的某条菜品订单记录
	 * @param openid
	 * @param branchSid
	 * @param dishid
	 * @return
	 */
	public int deleteDishOrder(String openid, int branchSid, int dishid) {
		String SQL = "DELETE FROM dish_order WHERE openid = ? AND branchSid = ? "
				+ "AND dishid = ?";
		int result = jdbcTemplate.update(SQL, openid, branchSid, dishid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteDishOrder
	 * @description 删除用户在某分店的所有菜品订单记录
	 * @param openid
	 * @param branchSid
	 * @return
	 */
	public int deleteDishOrder(String openid, int branchSid) {
		String SQL = "DELETE FROM dish_order WHERE openid = ? AND branchSid = ?";
		int result = jdbcTemplate.update(SQL, openid, branchSid);
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
	 * @description 更新菜品类别信息
	 * @param dishClass
	 * @return
	 */
	public int updateDishClass(DishClass dishClass) {
		String SQL = "UPDATE dishclass SET className = ? WHERE classid = ?";
		int result = jdbcTemplate.update(SQL, dishClass.getClassName(), 
				dishClass.getClassid());
		return result <= 0 ? 0 : result;
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
	 * @title updateDishRecom
	 * @description 更新菜品推荐人数 recomNum = recomNum + 1
	 * @param dishid
	 * @return
	 */
	public int updateDishRecom(int dishid) {
		String SQL = "UPDATE dish SET recomNum = recomNum + 1 WHERE dishid = ?";
		int result = jdbcTemplate.update(SQL, dishid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title updateDishBranch
	 * @description 更新分店菜品信息
	 * @param dishBranch
	 * @param branchSid
	 * @return
	 */
	public int updateDishBranch(DishBranch dishBranch, int branchSid) {
		String SQL = "UPDATE dish_branch SET price = ?, available = ? WHERE "
				+ "dishid = ? AND branchSid = ?";
		int result = jdbcTemplate.update(SQL, dishBranch.getPrice(), 
				dishBranch.getAvailable(), dishBranch.getDishid(), branchSid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title updateDishOrderPlus
	 * @description 更新菜品订单记录  count = count + 1
	 * @param openid
	 * @param branchSid
	 * @param dishid
	 * @return
	 */
	public int updateDishOrderPlus(String openid, int branchSid, int dishid) {
		String SQL = "UPDATE dish_order SET count = count + 1 WHERE openid = ? "
				+ "AND branchSid = ? AND dishid = ?";
		int result = jdbcTemplate.update(SQL, openid, branchSid, dishid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title updateDishOrderMinus
	 * @description 更新菜品订单记录  count = count - 1
	 * @param openid
	 * @param branchSid
	 * @param dishid
	 * @return
	 */
	public int updateDishOrderMinus(String openid, int branchSid, int dishid) {
		String SQL = "UPDATE dish_order SET count = count - 1 WHERE openid = ? "
				+ "AND branchSid = ? AND dishid = ?";
		int result = jdbcTemplate.update(SQL, openid, branchSid, dishid);
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
	 * @title getDishClassMapForOrder
	 * @description 根据菜品类别id集合查询菜品类别名称className
	 * @param classidSet
	 * @return
	 */
	public Map<Integer, String> getDishClassMapForOrder(Set<Integer> classidSet) {
		Map<Integer, String> dishClassMap = new HashMap<Integer, String>();
		for (Integer classid : classidSet) {
			String className = getClassName(classid);
			dishClassMap.put(classid, className);
		}
		return dishClassMap;
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
	 * @title getClassName
	 * @description 根据菜品类别id查询菜品类别名称
	 * @param classid
	 * @return
	 */
	private String getClassName(int classid) {
		String className = null;
		String SQL = "SELECT className FROM dishclass WHERE classid = ?";
		try {
			className = jdbcTemplate.queryForObject(SQL, new Object[]{classid}, new ClassNameMapper());
		} catch (Exception e) {
			System.out.println("getClassName: " + e.getMessage());
		}
		return className;
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
	
	private static final class ClassNameMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String className = rs.getString("className");
			return className;
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
		String SQL = "SELECT D.dishid, D.dishName, D.createTime, D.dishPic FROM"
				+ " dish D WHERE D.appid = ? ORDER BY D.createTime DESC";
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
		List<Dish> dishList = null;
		String SQL = "SELECT D.dishid, D.dishName, D.createTime, D.dishPic FROM"
				+ " dish D, dish_dishclass E WHERE D.dishid = E.dishid AND "
				+ "E.classid = ? ORDER BY D.createTime DESC";	
		try {
			dishList = jdbcTemplate.query(SQL, new Object[]{classid}, new DetailedDishinfoMapper());
		} catch (Exception e) {
			System.out.println("getDetailedDishinfos: " + e.getMessage());
			dishList = new ArrayList<Dish>();
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
	 * @title getDishCount
	 * @description 查询用户在分店某类菜品下的订餐总数
	 * @param classid
	 * @param openid
	 * @param branchSid
	 * @return
	 */
	public int getDishCount(int classid, String openid, int branchSid) {
		int count = 0;
		List<Integer> dishidList = getDishidList(classid);
		for (Integer dishid : dishidList) {
			count += getDishOrderCount(dishid, branchSid, openid);
		}
		return count;
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
			dish.setDishid(rs.getInt("D.dishid"));
			dish.setDishName(rs.getString("D.dishName"));
			dish.setCreateTime(rs.getTimestamp("D.createTime"));
			dish.setDishPic(rs.getString("D.dishPic"));
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
	 * @title getBranchDishinfos
	 * @description 根据分店id和appid查询该应用下的所有菜品在该分店的详细信息 (dishid, creatorSid, dishName, 
	 * createTime, dishPic, dishUnit, price, available)
	 * @param appid
	 * @param branchSid
	 * @return
	 */
	public List<DishBranch> getBranchDishinfos(String appid, int branchSid) {
		List<DishBranch> dishList = null;
		String SQL = "SELECT D.dishid, D.creatorSid, D.dishName, D.createTime, "
				+ "D.dishPic, D.price, D.dishUnit FROM dish D WHERE D.appid = ? "
				+ "ORDER BY D.createTime DESC";
		try {
			dishList = jdbcTemplate.query(SQL, new Object[]{appid}, new BranchDishinfoMapper());
		} catch (Exception e) {
			System.out.println("getBranchDishinfos: " + e.getMessage());
			dishList = new ArrayList<DishBranch>();
		}
		for (DishBranch dishBranch : dishList) {
			DishBranch temp = getDishBranch(dishBranch.getDishid(), branchSid);
			if (temp != null) {
				dishBranch.setPrice(temp.getPrice());
				dishBranch.setAvailable(temp.getAvailable());
			}
			if (dishBranch.getCreatorSid() == branchSid) {
				dishBranch.setAllowed(true);
			} else {
				dishBranch.setAllowed(false);
			}
		}
		return dishList;
	}
	
	/**
	 * @title getBranchDishinfos
	 * @description 根据分店id和菜品类别id查询该菜品类别关联的所有菜品在该分店的详细信息  (dishid, creatorSid, dishName, 
	 * createTime, dishPic, dishUnit, price, available)
	 * @param classid
	 * @param branchSid
	 * @return
	 */
	public List<DishBranch> getBranchDishinfos(int classid, int branchSid) {
		List<DishBranch> dishList = null;
		String SQL = "SELECT D.dishid, D.creatorSid, D.dishName, D.createTime, "
				+ "D.dishPic, D.price, D.dishUnit FROM dish D, dish_dishclass E WHERE "
				+ "D.dishid = E.dishid AND E.classid = ? ORDER BY D.createTime DESC";
		try {
			dishList = jdbcTemplate.query(SQL, new Object[]{classid}, new BranchDishinfoMapper());
		} catch (Exception e) {
			System.out.println("getBranchDishinfos: " + e.getMessage());
			dishList = new ArrayList<DishBranch>();
		}
		for (DishBranch dishBranch : dishList) {
			DishBranch temp = getDishBranch(dishBranch.getDishid(), branchSid);
			if (temp != null) {
				dishBranch.setPrice(temp.getPrice());
				dishBranch.setAvailable(temp.getAvailable());
			}
			if (dishBranch.getCreatorSid() == branchSid) {
				dishBranch.setAllowed(true);
			} else {
				dishBranch.setAllowed(false);
			}
		}
		return dishList;
	}
	
	/**
	 * @title getBranchDishForCustomer
	 * @description 查询某应用下分店全部菜品在手机端显示信息 (dishid, dishName, dishPic, dishDesc, price, 
	 * dishUnit, recomNum)
	 * @param appid
	 * @param branchSid
	 * @return
	 */
	public List<DishBranch> getBranchDishForCustomer(String appid, int branchSid) {
		List<DishBranch> dishList = null;
		String SQL = "SELECT D.dishid, D.dishName, D.dishPic, D.dishDesc, D.price, "
				+ "D.dishUnit, D.recomNum FROM dish D WHERE D.appid = ? ORDER BY "
				+ "D.recomNum DESC";
		try {
			dishList = jdbcTemplate.query(SQL, new Object[]{appid}, new CustomerDishinfoMapper());
		} catch (Exception e) {
			System.out.println("getBranchDishForCustomer: " + e.getMessage());
			dishList = new ArrayList<DishBranch>();
		}
		for (int i = 0, j = dishList.size(); i < j; i++) {
			DishBranch dishBranch = dishList.get(i);
			DishBranch temp = getDishBranch(dishBranch.getDishid(), branchSid);
			if (temp != null && temp.getAvailable() == 1) {
				dishBranch.setPrice(temp.getPrice());
			} else {
				dishList.remove(i);
			}
		}
		return dishList;
	}
	
	/**
	 * @title getDishClassForCustomer
	 * @description 查询某分店某一类菜品对于某人在手机端显示信息 (dishid, dishName, dishPic, dishDesc, price, 
	 * dishUnit, recomNum, price, count)
	 * @param classid
	 * @param branchSid
	 * @param openid
	 * @return
	 */
	public List<DishBranch> getDishClassForCustomer(int classid, int branchSid, String openid) {
		List<DishBranch> dishList = null;
		String SQL = "SELECT D.dishid, D.dishName, D.dishPic, D.dishDesc, D.price, "
				+ "D.dishUnit, D.recomNum FROM dish D, dish_dishclass E WHERE "
				+ "D.dishid = E.dishid AND E.classid = ? ORDER BY D.recomNum DESC";
		try {
			dishList = jdbcTemplate.query(SQL, new Object[]{classid}, new CustomerDishinfoMapper());
			for (int i = 0, j = dishList.size(); i < j; i++) {
				DishBranch dishBranch = dishList.get(i);
				int dishid = dishBranch.getDishid();
				DishBranch temp = getDishBranch(dishid, branchSid);
				if (temp != null && temp.getAvailable() == 1) {
					dishBranch.setPrice(temp.getPrice());
					dishBranch.setCount(getDishOrderCount(dishid, branchSid, openid));
				} else {
					dishList.remove(i);
				}
			}
		} catch (Exception e) {
			System.out.println("getDishClassForCustomer: " + e.getMessage());
			dishList = new ArrayList<DishBranch>();
		}
		return dishList;
	}
	
	/**
	 * @title getDishMapForOrder
	 * @description 根据openid和分店id查询用户在该分店的订餐详情（按菜品类别划分）（dishid, dishName, dishPic, price, dishUnit, count）
	 * @param openid
	 * @param branchSid
	 * @return
	 */
	public Map<Integer, List<DishBranch>> getDishMapForOrder(String openid, int branchSid) {
		Map<Integer, List<DishBranch>> dishMap = new HashMap<Integer, List<DishBranch>>();
		List<DishBranch> dishList = getDishOrder(openid, branchSid);
		for (DishBranch dishBranch : dishList) {
			int count = dishBranch.getCount();
			if (count > 0) {
				int dishid = dishBranch.getDishid();
				DishBranch temp = getDishBranchForOrder(dishid, branchSid);
				if (temp != null) {
					temp.setCount(count);
				} else {
					temp = dishBranch;
				}
				
				List<Integer> classidList = getClassidList(dishid);
				Integer classid = classidList.get(0);
				if (dishMap.containsKey(classid)) {
					dishMap.get(classid).add(temp);
				} else {
					dishMap.put(classid, new ArrayList<DishBranch>());
					dishMap.get(classid).add(temp);
				}
			}
		}
		return dishMap;
	}
	
	/**
	 * @title getDishOrder
	 * @description 根据openid和分店id查询用户在分店的订单记录 (dishid, count)
	 * @param openid
	 * @param branchSid
	 * @return
	 */
	private List<DishBranch> getDishOrder(String openid, int branchSid) {
		List<DishBranch> dishList = null;
		String SQL = "SELECT dishid, count FROM dish_order WHERE branchSid = ? AND openid = ?";
		try {
			dishList = jdbcTemplate.query(SQL, new Object[]{branchSid, openid}, new DishOrderMapper());
		} catch (Exception e) {
			System.out.println("getDishOrder: " + e.getMessage());
			dishList = new ArrayList<DishBranch>();
		}
		return dishList;
	}
	
	/**
	 * @title getDishBranch
	 * @description 根据菜品id和分店id查询该菜品在该分店销售信息 (price, available)
	 * @param dishid
	 * @param branchSid
	 * @return
	 */
	private DishBranch getDishBranch(int dishid, int branchSid) {
		DishBranch dishBranch = null;
		String SQL = "SELECT price, available FROM dish_branch WHERE dishid = ?"
				+ " AND branchSid = ?";
		try {
			dishBranch = jdbcTemplate.queryForObject(SQL, new Object[]{dishid, 
					branchSid}, new DishBranchMapper());
		} catch (Exception e) {
			System.out.println("getDishBranch: " + e.getMessage());
		}
		return dishBranch;
	}
	
	/**
	 * @title getDishBranchForOrder
	 * @description 根据菜品id和分店id查询用于“我的菜单”显示的菜品信息 (dishid, dishName, dishPic, dishDesc, price, dishUnit, recomNum)
	 * @param dishid
	 * @param branchSid
	 * @return
	 */
	private DishBranch getDishBranchForOrder(int dishid, int branchSid) {
		DishBranch dishBranch = null;
		String SQL = "SELECT dishid, dishName, dishPic, dishDesc, price, dishUnit, recomNum FROM dish WHERE dishid = ?";
		try {
			dishBranch = jdbcTemplate.queryForObject(SQL, new Object[]{dishid}, new DishBranchForOrderMapper());
		} catch (Exception e) {
			System.out.println("getDishBranchForOrder: " + e.getMessage());
		}
		if (dishBranch != null) {
			DishBranch temp = getDishBranch(dishid, branchSid);
			if (temp != null) {
				dishBranch.setPrice(temp.getPrice());
			}
		}
		return dishBranch;
	}
	
	/**
	 * @title getDishOrderCount
	 * @description 查询某人在某分店对某道菜的订购数
	 * @param dishid
	 * @param branchSid
	 * @param openid
	 * @return
	 */
	private int getDishOrderCount(int dishid, int branchSid, String openid) {
		int count = 0;
		String SQL = "SELECT count FROM dish_order WHERE dishid = ? AND branchSid"
				+ " = ? AND openid = ?";
		try {
			count = jdbcTemplate.queryForObject(SQL, new Object[]{dishid, branchSid,
					openid}, new DishOrderCountMapper());
		} catch (Exception e) {
		}
		return count <= 0 ? 0 : count;
	}
	
	private static final class BranchDishinfoMapper implements RowMapper<DishBranch>{
		@Override
		public DishBranch mapRow(ResultSet rs, int arg1) throws SQLException {
			DishBranch dishBranch = new DishBranch();
			dishBranch.setDishid(rs.getInt("D.dishid"));
			dishBranch.setCreatorSid(rs.getInt("D.creatorSid"));
			dishBranch.setDishName(rs.getString("D.dishName"));
			dishBranch.setCreateTime(rs.getTimestamp("D.createTime"));
			dishBranch.setDishPic(rs.getString("D.dishPic"));
			dishBranch.setPrice(rs.getInt("D.price"));
			dishBranch.setDishUnit(rs.getString("D.dishUnit"));
			return dishBranch;
		}
	}
	
	private static final class CustomerDishinfoMapper implements RowMapper<DishBranch>{
		@Override
		public DishBranch mapRow(ResultSet rs, int arg1) throws SQLException {
			DishBranch dishBranch = new DishBranch();
			dishBranch.setDishid(rs.getInt("D.dishid"));
			dishBranch.setDishName(rs.getString("D.dishName"));
			dishBranch.setDishPic(rs.getString("D.dishPic"));
			dishBranch.setDishDesc(rs.getString("D.dishDesc"));
			dishBranch.setPrice(rs.getInt("D.price"));
			dishBranch.setDishUnit(rs.getString("D.dishUnit"));
			dishBranch.setRecomNum(rs.getInt("D.recomNum"));
			return dishBranch;
		}
	}
	
	private static final class DishBranchMapper implements RowMapper<DishBranch>{
		@Override
		public DishBranch mapRow(ResultSet rs, int arg1) throws SQLException {
			DishBranch dishBranch = new DishBranch();
			dishBranch.setPrice(rs.getInt("price"));
			dishBranch.setAvailable(rs.getInt("available"));
			return dishBranch;
		}	
	}
	
	private static final class DishBranchForOrderMapper implements RowMapper<DishBranch>{
		@Override
		public DishBranch mapRow(ResultSet rs, int arg1) throws SQLException {
			DishBranch dishBranch = new DishBranch();
			dishBranch.setDishid(rs.getInt("dishid"));
			dishBranch.setDishName(rs.getString("dishName"));
			dishBranch.setDishPic(rs.getString("dishPic"));
			dishBranch.setDishDesc(rs.getString("dishDesc"));
			dishBranch.setPrice(rs.getInt("price"));
			dishBranch.setDishUnit(rs.getString("dishUnit"));
			dishBranch.setRecomNum(rs.getInt("recomNum"));
			return dishBranch;
		}
	}
	
	private static final class DishOrderMapper implements RowMapper<DishBranch>{
		@Override
		public DishBranch mapRow(ResultSet rs, int arg1) throws SQLException {
			DishBranch dishBranch = new DishBranch();
			dishBranch.setDishid(rs.getInt("dishid"));
			dishBranch.setCount(rs.getInt("count"));
			return dishBranch;
		}
	}
	
	private static final class DishOrderCountMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer count = rs.getInt("count");
			return count;
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
		String SQL = "SELECT B.branchSid FROM branch_store B, storeuser_application "
				+ "S WHERE B.storeSid = S.sid AND S.appid = ?";
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
