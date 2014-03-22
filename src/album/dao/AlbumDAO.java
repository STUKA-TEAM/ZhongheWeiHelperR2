package album.dao;

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

import album.Album;
import album.AlbumClass;
import album.Photo;
/**
 * @Title: AlbumDAO
 * @Description: DAO for album and albumclass model
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月17日
 */
public class AlbumDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title insertAlbum
	 * @description 插入相册信息及所关联相册集信息
	 * @param album
	 * @return
	 */
	public int insertAlbum(final Album album){
		int result = 0;
		final String SQL = "INSERT INTO album (albumid, appid, albumName, coverPic, createTime) VALUES (default, ?, ?, ?, ?)";
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, album.getAppid());
		        ps.setString(2, album.getAlbumName());
		        ps.setString(3, album.getCoverPic());
		        ps.setTimestamp(4, album.getCreateTime());
		        return ps;
		    }
		}, kHolder);
		
		if (result > 0) {
			String coverPic = album.getCoverPic();
			if (coverPic != null && !coverPic.equals("")) {
				deleteImageTempRecord(coverPic);
			}
			
			int albumid = kHolder.getKey().intValue();
			result = insertACRByAlbumid(albumid, album.getClassidList());
			if (result == 0) {
				return -1;
			}
			
			result = insertPhotoList(albumid, album.getPhotoList());
			if (result == 0) {
				return -2;
			}
			
			result = deletePhotoImageTempRecord(album.getPhotoList());
			if (result == 0) {
				return -3;
			}
			
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * @title insertAlbumClass
	 * @description 插入相册集记录及所关联相册信息
	 * @param albumClass
	 * @return
	 */
	public int insertAlbumClass(final AlbumClass albumClass){
		int result = 0;
		final String SQL = "INSERT INTO albumclass (classid, appid, className, createTime) VALUES (default, ?, ?, ?)";
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, albumClass.getAppid());
		        ps.setString(2, albumClass.getClassName());
		        ps.setTimestamp(3, albumClass.getCreateTime());
		        return ps;
		    }
		}, kHolder);
		
		if (result > 0) {
			int classid = kHolder.getKey().intValue();
			
			result = insertACRByClassid(classid, albumClass.getAlbumidList());
			if (result == 0) {
				return -1;
			}
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * @title insertPhotoList
	 * @description 插入照片集信息
	 * @param albumid
	 * @param photoList
	 * @return
	 */
	private int insertPhotoList(int albumid, List<Photo> photoList){
		String SQL = "INSERT INTO album_image (id, albumid, imagePath, imageDesc) VALUES (default, ?, ?, ?)";
		int result = 1;
		
		if (photoList != null) {
			for (int i = 0; i < photoList.size(); i++) {
				Photo photo = photoList.get(i);
				result = jdbcTemplate.update(SQL, albumid, photo.getImagePath(), photo.getImageDesc());
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
	 * @title insertACRByClassid
	 * @description 插入相册集AlbumClass和相册Album的关系Relationship
	 * @param classid
	 * @param albumidList
	 * @return
	 */
	private int insertACRByClassid(int classid, List<Integer> albumidList) {
		String SQL = "INSERT INTO album_albumclass (id, albumid, classid) VALUES (default, ?, ?)";
		int result = 1;
		
		if (albumidList != null) {
			for (int i = 0; i < albumidList.size(); i++) {
				int albumid = albumidList.get(i);
				result = jdbcTemplate.update(SQL, albumid, classid);
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
	 * @title insertACRByAlbumid
	 * @description 插入相册集AlbumClass和相册Album的关系Relationship
	 * @param albumid
	 * @param classidList
	 * @return
	 */
	private int insertACRByAlbumid(int albumid, List<Integer> classidList){
		String SQL = "INSERT INTO album_albumclass (id, albumid, classid) VALUES (default, ?, ?)";
		int result = 1;
		
		if (classidList != null) {
			for (int i = 0; i < classidList.size(); i++) {
				int classid = classidList.get(i);
				result = jdbcTemplate.update(SQL, albumid, classid);
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
	 * @title deletePhotoImageTempRecord
	 * @description 删除照片图片缓存记录
	 * @param photoList
	 * @return
	 */
	private int deletePhotoImageTempRecord(List<Photo> photoList){
		int result = 1;
		
		if (photoList != null) {
			for (int i = 0; i < photoList.size(); i++) {
				result = deleteImageTempRecord(photoList.get(i).getImagePath());
				if (result == 0) {
					return 0;
				}
			}
			return result;
		} else {
			return result;
		}
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
	
	public int deleteAlbum(int albumid){
		String SQL = "DELETE FROM album WHERE albumid = ?";
		int result = 0;
		
		Album album = getAlbumMedia(albumid);
		if (album != null) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			
			String coverPic = album.getCoverPic();
			if (coverPic != null && !coverPic.equals("")) {
				insertImageTempRecord(coverPic, current);
			}
			
			
			
			for (int i = 0; i < album.getPhotoList().size(); i++) {
				Photo photo = album.getPhotoList().get(i);
				insertImageTempRecord(photo.getImagePath(), current);
			}
		} else {
			return 0;
		}
		
		result = jdbcTemplate.update(SQL, albumid);
		if (result > 0) {
			deleteACRByAlbumid(albumid);
			
		}
		return 0;
	}
	
	/**
	 * @title deleteAlbumClass
	 * @description 删除相册集及所关联的相册记录
	 * @param classid
	 * @return
	 */
	public int deleteAlbumClass(int classid){
		String SQL = "DELETE FROM albumclass WHERE classid = ?";
		int result = jdbcTemplate.update(SQL, classid);
		if (result > 0) {
			deleteACRByClassid(classid);
		} 
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteACRByClassid
	 * @description 根据相册集classid删除相册album与相册集albumclass之间的关系
	 * @param classid
	 * @return
	 */
	public int deleteACRByClassid(int classid){
		String SQL = "DELETE FROM album_albumclass WHERE classid = ?";
		int result = jdbcTemplate.update(SQL, classid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title deleteACRByAlbumid
	 * @description 根据相册albumid删除相册album与相册集albumclass之间的关系
	 * @param albumid
	 * @return
	 */
	public int deleteACRByAlbumid(int albumid){
		String SQL = "DELETE FROM album_albumclass WHERE albumid = ?";
		int result = jdbcTemplate.update(SQL, albumid);
		return result <= 0 ? 0 : result;
	}
	
	//update
	public int updateAlbum(Album album){
		return 0;
	}
	
	/**
	 * @title updateAlbumClass
	 * @description 更新相册集信息
	 * @param albumClass
	 * @return
	 */
	public int updateAlbumClass(AlbumClass albumClass){
		String SQL = "UPDATE albumclass SET className = ? WHERE classid = ?";
		int result = jdbcTemplate.update(SQL, albumClass.getClassName(), albumClass.getClassid());
		
		if (result > 0) {
			deleteACRByClassid(albumClass.getClassid());
			result = insertACRByClassid(albumClass.getClassid(), albumClass.getAlbumidList());
			if (result == 0) {
				return -1;
			}
			return result;
		} else {
			return 0;
		}
	}
	
	//query
	/**
	 * @title getAlbumContent
	 * @description 根据albumid查询相册基本信息以及关联相册信息 (albumid, albumName, coverPic, classidList, photoList)
	 * @param albumid
	 * @return
	 */
	public Album getAlbumContent(int albumid){
		Album album = null;
		String SQL = "SELECT albumid, albumName, coverPic FROM album WHERE albumid = ?";
		
		try {
			album = jdbcTemplate.queryForObject(SQL, new Object[]{albumid}, new AlbumContentMapper());
		} catch (Exception e) {
			System.out.println("getAlbumContent: " + e.getMessage());
		}
		
		if (album != null) {
			album.setClassidList(getClassidList(album.getAlbumid()));
			album.setPhotoList(getPhotoList(album.getAlbumid()));
		}	
		return album;
	}
	
	private static final class AlbumContentMapper implements RowMapper<Album>{
		@Override
		public Album mapRow(ResultSet rs, int arg1) throws SQLException {
			Album album = new Album();
			album.setAlbumid(rs.getInt("A.albumid"));
			album.setAlbumName(rs.getString("A.albumName"));
			album.setCoverPic(rs.getString("A.coverPic"));
			return album;
		}	
	}
	
	/**
	 * @title getClassContent
	 * @description 根据classid查询相册集基本信息以及关联相册信息 (classid, className, albumidList) 
	 * @param classid
	 * @return
	 */
	public AlbumClass getClassContent(int classid){
		String SQL = "SELECT classid, className FROM albumclass WHERE classid = ?";
		AlbumClass albumClass = null;
		
		try {
			albumClass = jdbcTemplate.queryForObject(SQL, new Object[]{classid}, new BasicClassinfoMapper());
		} catch (Exception e) {
			System.out.println("getClassContent: " + e.getMessage());
		}
		
		if (albumClass != null) {
			albumClass.setAlbumidList(getAlbumidList(classid));
		}
		return albumClass;
	}
	
	/**
	 * @title getBasicClassinfos
	 * @description 根据appid获取相册集的基本信息列表
	 * @param appid
	 * @return
	 */
	public List<AlbumClass> getBasicClassinfos(String appid){
		String SQL = "SELECT classid, className FROM albumclass WHERE appid = ?"
				+ " ORDER BY classid ASC";
		List<AlbumClass> classList = null;
		try {
			classList = jdbcTemplate.query(SQL, new Object[]{appid}, new BasicClassinfoMapper());
		} catch (Exception e) {
			System.out.println("getBasicClassinfos: " + e.getMessage());
			classList = new ArrayList<AlbumClass>();
		}
		return classList;
	}
	
	private static final class BasicClassinfoMapper implements RowMapper<AlbumClass>{
		@Override
		public AlbumClass mapRow(ResultSet rs, int arg1) throws SQLException {
			AlbumClass albumClass = new AlbumClass();
			albumClass.setClassid(rs.getInt("classid"));
			albumClass.setClassName(rs.getString("className"));
			return albumClass;
		}	
	}
	
	/**
	 * @title getDetailedClassinfos
	 * @description 根据appid查询相册集详细信息 (classid, className, createTime, albumCount)
	 * 并根据createTime字段降序排序取出
	 * @param appid
	 * @return
	 */
	public List<AlbumClass> getDetailedClassinfos(String appid){
		List<AlbumClass> classList = null;
		String SQL = "SELECT classid, className, createTime FROM albumclass "
				+ "WHERE appid = ? ORDER BY createTime DESC";
		try {
			classList = jdbcTemplate.query(SQL, new Object[]{appid}, new DetailedClassinfoMapper());
		} catch (Exception e) {
			System.out.println("getDetailedClassinfos: " + e.getMessage());
			classList = new ArrayList<AlbumClass>();
		}
		
		for (int i = 0; i < classList.size(); i++) {
			int count = getAlbumCount(classList.get(i).getClassid());
			classList.get(i).setAlbumCount(count);
		}
		return classList;
	}
	
	private static final class DetailedClassinfoMapper implements RowMapper<AlbumClass>{
		@Override
		public AlbumClass mapRow(ResultSet rs, int arg1) throws SQLException {
			AlbumClass albumClass = new AlbumClass();
			albumClass.setClassid(rs.getInt("classid"));
			albumClass.setClassName(rs.getString("className"));
			albumClass.setCreateTime(rs.getTimestamp("createTime"));
			return albumClass;
		}	
	}
	
	/**
	 * @title getClassidList
	 * @description 根据albumid查询相册所属的相册集id列表
	 * @param albumid
	 * @return
	 */
	public List<Integer> getClassidList(int albumid){
		List<Integer> classidList = null;
		String SQL = "SELECT classid FROM album_albumclass WHERE albumid = ?";
		try {
			classidList = jdbcTemplate.query(SQL, new Object[]{albumid}, new ClassidMapper());
		} catch (Exception e) {
			System.out.println("getClassidList: " + e.getMessage());
			classidList = new ArrayList<Integer>();
		}
		return classidList;
	}
	
	private static final class ClassidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer classid = rs.getInt("classid");
			return classid;
		}
	}
	
	/**
	 * @title getAlbumidList
	 * @description 根据classid查询相册集关联的相册id列表
	 * @param classid
	 * @return
	 */
	public List<Integer> getAlbumidList(int classid){
		List<Integer> albumidList = null;
		String SQL = "SELECT albumid FROM album_albumclass WHERE classid = ?";
		try {
			albumidList = jdbcTemplate.query(SQL, new Object[]{classid}, new AlbumidMapper());
		} catch (Exception e) {
			System.out.println("getAlbumidList: " + e.getMessage());
			albumidList = new ArrayList<Integer>();
		}
		return albumidList;
	}
	
	private static final class AlbumidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer albumid = rs.getInt("albumid");
			return albumid;
		}
	}
	
	/**
	 * @title getAlbumMedia
	 * @description 根据albumid查询相册媒介信息 (coverPic, photoList)
	 * @param albumid
	 * @return
	 */
	public Album getAlbumMedia(int albumid){
		String SQL = "SELECT coverPic FROM album WHERE albumid = ?";
		Album album = null;
		
		try {
			album = jdbcTemplate.queryForObject(SQL, new Object[]{albumid}, new AlbumBasicMediaMapper());
		} catch (Exception e) {
			System.out.println("getAlbumBasicMedia: " + e.getMessage());
		}
		
		if (album != null) {
			album.setPhotoList(getPhotoImageList(albumid));
		}
		return album;
	}
	
	private static final class AlbumBasicMediaMapper implements RowMapper<Album>{
		@Override
		public Album mapRow(ResultSet rs, int arg1) throws SQLException {
			Album album = new Album();
			album.setCoverPic(rs.getString("coverPic"));
			return album;
		}
	}

	/**
	 * @title getBasicAlbuminfos
	 * @description 根据appid查询相册基本信息 (albumid, albumName)
	 * @param appid
	 * @return
	 */
	public List<Album> getBasicAlbuminfos(String appid){
		List<Album> albumList = null;
		String SQL = "SELECT albumid, albumName FROM album WHERE appid = ?";
		try {
			albumList = jdbcTemplate.query(SQL, new Object[]{appid}, new BasicAlbuminfoMapper());
		} catch (Exception e) {
			System.out.println("" + e.getMessage());
			albumList = new ArrayList<Album>();
		}
		return albumList;
	}
	
	private static final class BasicAlbuminfoMapper implements RowMapper<Album>{
		@Override
		public Album mapRow(ResultSet rs, int arg1) throws SQLException {
			Album album = new Album();
			album.setAlbumid(rs.getInt("albumid"));
			album.setAlbumName(rs.getString("albumName"));
			return album;
		}	
	}
	
	/**
	 * @title getDetailedAlbuminfos
	 * @description 根据appid查询相册详细信息 (albumid, albumName, coverPic, createTime, albumCount)
	 * 并根据createTime字段降序排序取出
	 * @param appid
	 * @return
	 */
	public List<Album> getDetailedAlbuminfos(String appid){
		String SQL = "SELECT A.albumid, A.albumName, A.coverPic, A.createTime FROM album A "
				+ "WHERE A.appid = ? ORDER BY A.createTime DESC";
		List<Album> albumList = null;
		
		try {
			albumList = jdbcTemplate.query(SQL, new Object[]{appid}, new DetailedAlbuminfoMapper());
		} catch (Exception e) {
			System.out.println("getDetailedAlbuminfos: " + e.getMessage());
			albumList = new ArrayList<Album>();
		}
		
		for (int i = 0; i < albumList.size(); i++) {
			Album album = albumList.get(i);
			int count = getPhotoCount(album.getAlbumid());
			album.setPhotoCount(count);
		}
		return albumList;
	}
	
	/**
	 * @title getDetailedAlbuminfos
	 * @description 根据classid查询相册详细信息 (albumid, albumName, coverPic, createTime, albumCount)
	 * 并根据createTime字段降序排序取出
	 * @param classid
	 * @return
	 */
	public List<Album> getDetailedAlbuminfos(int classid){
		String SQL = "SELECT A.albumid, A.albumName, A.coverPic, A.createTime FROM "
				+ "album A, album_albumclass B WHERE A.albumid = B.albumid "
				+ "AND B.classid = ? ORDER BY A.createTime DESC";
        List<Album> albumList = null;
		
		try {
			albumList = jdbcTemplate.query(SQL, new Object[]{classid}, new DetailedAlbuminfoMapper());
		} catch (Exception e) {
			System.out.println("getDetailedAlbuminfos: " + e.getMessage());
			albumList = new ArrayList<Album>();
		}
		
		for (int i = 0; i < albumList.size(); i++) {
			Album album = albumList.get(i);
			int count = getPhotoCount(album.getAlbumid());
			album.setPhotoCount(count);
		}
		return albumList;
	}
	
	private static final class DetailedAlbuminfoMapper implements RowMapper<Album>{
		@Override
		public Album mapRow(ResultSet rs, int arg1) throws SQLException {
			Album album = new Album();
			album.setAlbumid(rs.getInt("A.albumid"));
			album.setAlbumName(rs.getString("A.albumName"));
			album.setCoverPic(rs.getString("A.coverPic"));
			album.setCreateTime(rs.getTimestamp("A.createTime"));
			return album;
		}	
	}
	
	/**
	 * @title getAlbumCount
	 * @description 根据classid查询相册集所关联的相册数目
	 * @param classid
	 * @return
	 */
	public int getAlbumCount(int classid){
		String SQL = "SELECT COUNT(*) FROM album_albumclass WHERE classid = ?";
		int count = 0;
		try {
			count = jdbcTemplate.queryForObject(SQL, new Object[]{classid}, Integer.class);
		} catch (Exception e) {
			System.out.println("getAlbumCount: " + e.getMessage());
		}
		return count;
	}
	
	/**
	 * @title getPhotoCount
	 * @description 根据albumid查询相册下的照片数目
	 * @param albumid
	 * @return
	 */
	public int getPhotoCount(int albumid){
		String SQL = "SELECT COUNT(*) FROM album_image WHERE albumid = ?";
		int count = 0;
		try {
			count = jdbcTemplate.queryForObject(SQL, new Object[]{albumid}, Integer.class);
		} catch (Exception e) {
			System.out.println("getPhotoCount: " + e.getMessage());
		}
		return count;
	}
	
	/**
	 * @title getPhotoList
	 * @description 根据albumid获取相册照片信息 (imagePath, imageDesc)
	 * @param albumid
	 * @return
	 */
	public List<Photo> getPhotoList(int albumid){
		List<Photo> photoList = null;
		String SQL = "SELECT imagePath, imageDesc FROM album_image WHERE albumid = ?";
		
		try {
			photoList = jdbcTemplate.query(SQL, new Object[]{albumid}, new PhotoMapper());
		} catch (Exception e) {
			System.out.println("getPhotoList: " + e.getMessage());
			photoList = new ArrayList<Photo>();
		}
		return photoList;
	}
	
	private static final class PhotoMapper implements RowMapper<Photo>{
		@Override
		public Photo mapRow(ResultSet rs, int arg1) throws SQLException {
			Photo photo = new Photo();
			photo.setImagePath(rs.getString("imagePath"));
			photo.setImageDesc(rs.getString("imageDesc"));
			return photo;
		}		
	}
	
	/**
	 * @title getPhotoImageList
	 * @description 根据albumid获取相册照片信息 (imagePath)
	 * @param albumid
	 * @return
	 */
	public List<Photo> getPhotoImageList(int albumid){
		List<Photo> photoList = null;
		String SQL = "SELECT imagePath FROM album_image WHERE albumid = ?";
		
		try {
			photoList = jdbcTemplate.query(SQL, new Object[]{albumid}, new PhotoImageMapper());
		} catch (Exception e) {
			System.out.println("getPhotoList: " + e.getMessage());
			photoList = new ArrayList<Photo>();
		}
		return photoList;
	}
	
	private static final class PhotoImageMapper implements RowMapper<Photo>{
		@Override
		public Photo mapRow(ResultSet rs, int arg1) throws SQLException {
			Photo photo = new Photo();
			photo.setImagePath(rs.getString("imagePath"));
			return photo;
		}		
	}
}
