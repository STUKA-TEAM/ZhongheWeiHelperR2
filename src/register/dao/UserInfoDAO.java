package register.dao;

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

import register.UserInfo;

/**
 * @Title: UserInfoDAO
 * @Description: DAO for userInfo model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月09日
 */
public class UserInfoDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @Title: insertUserInfo
	 * @Description: 插入一条用户注册信息记录
	 * @param userInfo
	 * @return
	 */
	public int insertUserInfo(final UserInfo userInfo){
		int result = 0;
		final String SQL = "INSERT INTO storeuser (sid, roleid, username, password, "
				+ "createDate, storeName, email, phone, cellPhone, address, corpMoreInfoLink, "
				+ "lng, lat) VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		KeyHolder kHolder = new GeneratedKeyHolder();		
		result = jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
	            ps.setInt(1, userInfo.getRoleid());
	            ps.setString(2, userInfo.getUsername());
	            ps.setString(3, userInfo.getPassword());
	            ps.setTimestamp(4, userInfo.getCreateDate());
	            ps.setString(5, userInfo.getStoreName());
	            ps.setString(6, userInfo.getEmail());
	            ps.setString(7, userInfo.getPhone());
	            ps.setString(8, userInfo.getCellPhone());
	            ps.setString(9, userInfo.getAddress());
	            ps.setString(10, userInfo.getCorpMoreInfoLink());
	            ps.setBigDecimal(11, userInfo.getLng());
	            ps.setBigDecimal(12, userInfo.getLat());
	            return ps;
	        }
	    }, kHolder);
		
		return result <= 0 ? 0 : kHolder.getKey().intValue();
	}
	
	/**
	 * @title: insertImageList
	 * @description: 插入与sid相关的图片列表记录
	 * @param sid
	 * @param imageList
	 * @return
	 */
	private int insertImageList(int sid, List<String> imageList){
		int result = 0;
		String SQL = "INSERT INTO storeimage (id, sid, imagePath) VALUES (default, ?, ?)";
		
		for (int i = 0; i < imageList.size(); i++) {
			String imagePath = imageList.get(i);
			result = jdbcTemplate.update(SQL, sid, imagePath);
			if (result <= 0) {
				return 0;
			}
		}
		
		return result;
	}
	
	/**
	 * @title: insertImageTempRecord
	 * @description: 将要删除图片的信息存入临时表
	 * @param imagePath
	 * @param current
	 * @return
	 */
	private int insertImageTempRecord(String imagePath, Timestamp current){
		int result = 0;
		String SQL = "INSERT INTO image_temp_record (id, imagePath, createDate) VALUES (default, ?, ?)";
		
		result = jdbcTemplate.update(SQL, imagePath, current);
		
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: insertAppUpperLimit
	 * @description: 插入账户对应的app默认上限数
	 * @param sid
	 * @param appUpperLimit
	 * @return
	 */
	public int insertAppUpperLimit(int sid, int appUpperLimit){
		String SQL = "INSERT INTO customer_app_count (id, sid, appUpperLimit) VALUES (default, ?, ?)";
		int result = jdbcTemplate.update(SQL, sid, appUpperLimit);
		return result <= 0 ? 0 : result;
	}
	
	//delete
	/**
	 * @title: deleteUserInfo
	 * @description: 删除用户信息
	 * @param sid
	 * @return
	 */
	public int deleteUserInfo(int sid){
		String SQL = "DELETE FROM storeuser WHERE sid = ?";
		int effected = jdbcTemplate.update(SQL, sid);
		return effected;
	}
	
	/**
	 * @title: deleteUserImage
	 * @description: 删除用户关联的图片信息
	 * @param sid
	 * @return
	 */
	private int deleteUserImages(int sid){
		String SQL = "DELETE FROM storeimage WHERE sid = ?";
		int effected = jdbcTemplate.update(SQL, sid);
		return effected;
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
		return effected;
	}
	
	//update
	/**
	 * @title: updateUserInfo
	 * @description: 更新用户信息
	 * @param userInfo
	 * @return
	 */
	public int updateUserInfo(UserInfo userInfo){
		String SQL = "UPDATE storeuser SET storeName = ?, email = ?, phone = ?, "
				+ "cellPhone = ?, address = ?, corpMoreInfoLink = ?, lng = ?, "
				+ "lat = ? WHERE sid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{userInfo.getStoreName(), 
				userInfo.getEmail(), userInfo.getPhone(), userInfo.getCellPhone(), 
				userInfo.getAddress(), userInfo.getCorpMoreInfoLink(), 
				userInfo.getLng(), userInfo.getLat(), userInfo.getSid()});
		
		if (effected <= 0) {
			return 0;
		}
		else {
			List<String> originalImages = getUserImages(userInfo.getSid());
			updateUserImages(userInfo.getSid(), userInfo.getImageList());
			
			//delete records from image temporary table
			for (int i = 0; i < userInfo.getImageList().size(); i++) {
				String imagePath = userInfo.getImageList().get(i);			
				deleteImageTempRecord(imagePath);
			}
			
			//add original images to image temporary table
			Timestamp current = new Timestamp(System.currentTimeMillis());
			for (int i = 0; i < originalImages.size(); i++) {
				String imagePath = originalImages.get(i);				
				insertImageTempRecord(imagePath, current);
			}
			
			return effected;
		}
	}
	
	/**
	 * @title: updateUserImages
	 * @description: 更新用户图片信息
	 * @param sid
	 * @param imageList
	 * @return
	 */
	private int updateUserImages(int sid, List<String> imageList){
		int result = 0;
		
		deleteUserImages(sid);
		
		result = insertImageList(sid, imageList);		
		return result;
	}
	
	/**
	 * @title: updateContactInfo
	 * @description: 更新商家联系方式
	 * @param userInfo
	 * @return
	 */
	public int updateContactInfo(UserInfo userInfo){
		String SQL = "UPDATE customer_contact SET contact = ? WHERE sid = ?";
		int result = jdbcTemplate.update(SQL, userInfo.getContact(), userInfo.getSid());
		return result <= 0 ? 0 : result;
	}
	
	//query
	/**
	 * @Title: getUserInfo
	 * @Description: 根据用户id获取用户信息
	 * @param sid
	 * @return
	 */
	public UserInfo getUserInfo(int sid){
		String SQL = "SELECT * FROM storeuser WHERE sid = ?";
		UserInfo userInfo = null;
		try {
			userInfo = jdbcTemplate.queryForObject(SQL, new Object[]{sid}, new UserInfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (userInfo != null) {   //get image list 
			List<String> imageList = getUserImages(sid);
			userInfo.setImageList(imageList);
		}
		return userInfo;
	}
	
	private static final class UserInfoMapper implements RowMapper<UserInfo>{
		@Override
		public UserInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			UserInfo userInfo = new UserInfo();
			userInfo.setUsername(rs.getString("username"));
			userInfo.setCreateDate(rs.getTimestamp("createDate"));
			userInfo.setStoreName(rs.getString("storeName"));
			userInfo.setEmail(rs.getString("email"));
			userInfo.setPhone(rs.getString("phone"));
			userInfo.setCellPhone(rs.getString("cellPhone"));
			userInfo.setAddress(rs.getString("address"));
			userInfo.setCorpMoreInfoLink(rs.getString("corpMoreInfoLink"));
			userInfo.setLng(rs.getBigDecimal("lng"));
			userInfo.setLat(rs.getBigDecimal("lat"));
			return userInfo;
		}
	}
	
	/**
	 * @title: getCustomerInfoList
	 * @description: 获取CUSTOMER类型用户的信息列表
	 * @return
	 */
	public List<UserInfo> getCustomerInfoList(){
		String SQL = "SELECT sid, createDate, storeName FROM "
				+ "storeuser WHERE roleid = 1";                                             //default 'CUSTOMER' -- 1
		List<UserInfo> infoList = null;
		
		try {
			infoList = jdbcTemplate.query(SQL, new CustomerInfoMapper());
		} catch (Exception e) {
			infoList = new ArrayList<UserInfo>();
			System.out.println(e.getMessage());
		}
		
		for (int i = 0; i < infoList.size(); i++) {
			UserInfo temp = infoList.get(i);
			temp.setContact(getContactInfo(temp.getSid()));
		}
		return infoList;
	}
	
	private static final class CustomerInfoMapper implements RowMapper<UserInfo>{
		@Override
		public UserInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			UserInfo userInfo = new UserInfo();
			userInfo.setSid(rs.getInt("sid"));
			userInfo.setCreateDate(rs.getTimestamp("createDate"));
			userInfo.setStoreName(rs.getString("storeName"));
			return userInfo;
		}
	}
	
	/**
	 * @title: getUserImages
	 * @description: 通过sid获取相关联的图片列表
	 * @param sid
	 * @return
	 */
	public List<String> getUserImages(int sid){
		String SQL = "SELECT imagePath FROM storeimage WHERE sid = ?";
		List<String> imageList = null;
		
		try {
			imageList = jdbcTemplate.query(SQL, new Object[]{sid}, new ImagePathMapper());
		} catch (Exception e) {
			imageList = new ArrayList<String>();
			System.out.println(e.getMessage());
		}
		return imageList;
	}
	
	private static final class ImagePathMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String imagePath = rs.getString("imagePath");
			return imagePath;
		}		
	}
	
	/**
	 * @Title: getUserCount
	 * @Description: 查询某用户名是否已被注册 0-未注册 1-已注册
	 * @param username
	 * @return
	 */
	public int getUserCount(String username){
		String SQL = "SELECT COUNT(*) FROM storeuser WHERE username = ?";
		int count = 1;
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, username);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	/**
	 * @title: getSidByEloveid
	 * @description: 由eloveid查询sid
	 * @param eloveid
	 * @return
	 */
	public Integer getSidByEloveid(int eloveid){
		String SQL = "SELECT S.sid FROM storeuser_application S, elove E WHERE S.appid = E.appid AND E.eloveid = ?";
		Integer sid = null;
		
		try {
			sid = jdbcTemplate.queryForObject(SQL, new Object[]{eloveid}, new SidMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sid;
	}
	
	/**
	 * @title: getSidByWebsiteid
	 * @description: 由websiteid查询sid
	 * @param websiteid
	 * @return
	 */
	public Integer getSidByWebsiteid(int websiteid){
		String SQL = "SELECT S.sid FROM storeuser_application S, website W WHERE S.appid = W.appid AND W.websiteid = ?";
		Integer sid = null;
		
		try {
			sid = jdbcTemplate.queryForObject(SQL, new Object[]{websiteid}, new SidMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sid;
	}
	
	/**
	 * @title: getSidByAppid
	 * @description: 由appid查询sid
	 * @param appid
	 * @return
	 */
	public Integer getSidByAppid(String appid){
		String SQL = "SELECT S.sid FROM storeuser_application S WHERE S.appid = ?";
		Integer sid = null;
		
		try {
			sid = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, new SidMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sid;
	}
	
	private static final class SidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer sid = rs.getInt("S.sid");
			return sid;
		}		
	}
	
	/**
	 * @title: getContactInfo
	 * @description: 根据sid查找商家联系方式
	 * @param sid
	 * @return
	 */
	public String getContactInfo(int sid){
		String SQL = "SELECT contact FROM customer_contact WHERE sid = ?";
		String contact = null;
		
		try {
			contact = jdbcTemplate.queryForObject(SQL, new Object[]{sid}, new ContactInfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return contact;
	}
	
	private static final class ContactInfoMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String contact = rs.getString("contact");
			return contact;
		}	
	}
}
