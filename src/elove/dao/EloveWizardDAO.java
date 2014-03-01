package elove.dao;

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

import elove.EloveWizard;

/**
 * @Title: EloveWizardDAO
 * @Description: DAO for eloveWizard model
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月28日
 */
public class EloveWizardDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title: insertElove
	 * @description: 插入elove完整记录
	 * @param eloveWizard
	 * @return
	 */
	public int insertElove(final EloveWizard eloveWizard){
		int result = 0;
		final String SQL = "INSERT INTO elove (eloveid, appid, createTime, expiredTime, "
				+ "title, password, coverPic, coverText, majorGroupPhoto, xinNiang, xinLang, "
				+ "storyTextImagePath, music, phone, weddingDate, weddingAddress, lng, lat, "
				+ "shareTitle, shareContent, footerText, sideCorpInfo, themeid) VALUES (default, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, eloveWizard.getAppid());
		        ps.setTimestamp(2, eloveWizard.getCreateTime());
		        ps.setTimestamp(3, eloveWizard.getExpiredTime());
		        ps.setString(4, eloveWizard.getTitle());
		        ps.setString(5, eloveWizard.getPassword());
		        ps.setString(6, eloveWizard.getCoverPic());
		        ps.setString(7, eloveWizard.getCoverText());
		        ps.setString(8, eloveWizard.getMajorGroupPhoto());
		        ps.setString(9, eloveWizard.getXinNiang());
		        ps.setString(10, eloveWizard.getXinLang());
		        ps.setString(11, eloveWizard.getStoryTextImagePath());
		        ps.setString(12, eloveWizard.getMusic());
		        ps.setString(13, eloveWizard.getPhone());
		        ps.setString(14, eloveWizard.getWeddingDate());
		        ps.setString(15, eloveWizard.getWeddingAddress());
		        ps.setBigDecimal(16, eloveWizard.getLng());
		        ps.setBigDecimal(17, eloveWizard.getLat());
		        ps.setString(18, eloveWizard.getShareTitle());
		        ps.setString(19, eloveWizard.getShareContent());
		        ps.setString(20, eloveWizard.getFooterText());
		        ps.setString(21, eloveWizard.getSideCorpInfo());
		        ps.setInt(22, eloveWizard.getThemeid());
		        return ps;
		    }
		}, kHolder);
		
		if (result > 0 ) {
			deleteImageTempRecord(eloveWizard.getCoverPic());                               //删除临时表中记录
			deleteImageTempRecord(eloveWizard.getMajorGroupPhoto());
			deleteImageTempRecord(eloveWizard.getStoryTextImagePath());
			deleteVideoTempRecord(eloveWizard.getMusic());
			
			int eloveid = kHolder.getKey().intValue();
			
			if (eloveWizard.getStoryImagePath() != null) {
				result = insertImage(eloveid, "story", eloveWizard.getStoryImagePath());    //插入相遇相知图片
	            if (result == 0) {
					return -1;
				}
			}
			
			if (eloveWizard.getDressImagePath() != null) {
				result = insertImage(eloveid, "dress", eloveWizard.getDressImagePath());    //插入婚纱剪影图片
				if (result == 0) {
					return -2;
				}
			}
			
			if (eloveWizard.getDressVideoPath() != null) {
				result = insertVideo(eloveid, "dress", eloveWizard.getDressVideoPath());    //插入婚纱剪影录像
				if (result == 0) {
					return -3;
				}
			}
			
			if (eloveWizard.getRecordImagePath() != null) {
				result = insertImage(eloveid, "record", eloveWizard.getRecordImagePath());  //插入婚礼纪录图片
				if (result == 0) {
					return -4;
				}
			}
			
			if (eloveWizard.getRecordVideoPath() != null) {
				result = insertVideo(eloveid, "record", eloveWizard.getRecordVideoPath());  //插入婚礼纪录录像
				if (result == 0) {
					return -5;
				}
			}	
			
			Integer notPayNumber = getConsumeRecord(eloveWizard.getAppid());                //更新elove消费情况
			if (notPayNumber != null) {
				result = updateConsumeRecord(notPayNumber + 1, eloveWizard.getAppid());
				if (result == 0) {
					return -6;
				}
			}else {
				return -7;
			}			
			
			return result;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * @title: insertImage
	 * @description: 插入图片记录
	 * @param eloveid
	 * @param imageType
	 * @param imagePathList
	 * @return
	 */
	private int insertImage(int eloveid, String imageType, List<String> imageList){
	    String SQL = "INSERT INTO elove_images (id, eloveid, imagePath, imageType) VALUES (default, ?, ?, ?)";
		int result = 1;
		
		for (int i = 0; i < imageList.size(); i++) {
			String imagePath = imageList.get(i);
			result = jdbcTemplate.update(SQL, eloveid, imagePath, imageType);
			if (result > 0) {
				result = deleteImageTempRecord(imageList.get(i));
				if (result <= 0) {
					return 0;
				}
			}else {
				return 0;
			}
		}
		
		return result;
	}
	
	/**
	 * @title: insertImage
	 * @description: 插入单个图片
	 * @param eloveid
	 * @param imageType
	 * @param imagePath
	 * @return
	 */
	private int insertImage(int eloveid, String imageType, String imagePath){
		String SQL = "INSERT INTO elove_images (id, eloveid, imagePath, imageType) VALUES (default, ?, ?, ?)";
		int result = jdbcTemplate.update(SQL, eloveid, imagePath, imageType);
		if (result > 0) {
			result = deleteImageTempRecord(imagePath);
		}
		return result <= 0 ? 0 :result;
	}
	
	/**
	 * @title: insertVideo
	 * @description: 插入录像记录
	 * @param eloveid
	 * @param videoType
	 * @param videoPath
	 * @return
	 */
	private int insertVideo(int eloveid, String videoType, String videoPath){
		String SQL = "INSERT INTO elove_video (id, eloveid, videoPath, videoType) VALUES (default, ?, ?, ?)";
		int result = jdbcTemplate.update(SQL, eloveid, videoPath, videoType);
		if (result > 0) {
			result = deleteVideoTempRecord(videoPath);;
		}	
		return result <= 0 ? 0 :result;
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
	 * @title: insertVideoTempRecord
	 * @description: 将要删除视频的信息存入临时表
	 * @param imagePath
	 * @param current
	 * @return
	 */
	private int insertVideoTempRecord(String videoPath, Timestamp current){
		int result = 0;
		String SQL = "INSERT INTO video_temp_record (id, videoPath, createDate) VALUES (default, ?, ?)";
		
		result = jdbcTemplate.update(SQL, videoPath, current);
		
		return result <= 0 ? 0 : result;
	}

	//delete
	/**
	 * @title: deleteImage
	 * @description: 删除与eloveid相关的所有图片
	 * @param eloveid
	 * @return
	 */
	public int deleteImage(int eloveid){
		int effected = 0;
		String SQL = "DELETE FROM elove_images WHERE eloveid = ?";
		
		List<String> imageList = getImageList(eloveid);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		for (int i = 0; i < imageList.size(); i++) {
			String imagePath = imageList.get(i);
			insertImageTempRecord(imagePath, current);
		}
		
		effected = jdbcTemplate.update(SQL, new Object[]{eloveid});			
		return effected <= 0 ? 0 : effected;
	}
	
	/**
	 * @title: deleteImage
	 * @description: 根据imagePath删除elove图片
	 * @param imagePath
	 * @return
	 */
	private int deleteImage(String imagePath){
		String SQL = "DELETE FROM elove_images WHERE imagePath = ?";
		int result = jdbcTemplate.update(SQL, imagePath);
		if (result > 0) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			result = insertImageTempRecord(imagePath, current);
		}
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: deleteVideo
	 * @description: 删除与eloveid相关的所有视频
	 * @param eloveid
	 * @return
	 */
	public int deleteVideo(int eloveid){
		int effected = 0;
		String SQL = "DELETE FROM elove_video WHERE eloveid = ?";
		
		List<String> videoList = getVideoList(eloveid);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		for (int i = 0; i < videoList.size(); i++) {
			String videoPath = videoList.get(i);
			insertVideoTempRecord(videoPath, current);
		}
		
		effected = jdbcTemplate.update(SQL, new Object[]{eloveid});
		return effected <= 0 ? 0 : effected;
	}
	
	/**
	 * @title: deleteVideo
	 * @description: 根据videoPath删除elove视频
	 * @param videoPath
	 * @return
	 */
	private int deleteVideo(String videoPath){
		String SQL = "DELETE FROM elove_video WHERE videoPath = ?";
		int result = jdbcTemplate.update(SQL, videoPath);
		if (result > 0) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			result = insertVideoTempRecord(videoPath, current);
		}
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: deleteJoin
	 * @description: 删除与eloveid相关的所有来宾记录
	 * @param eloveid
	 * @return
	 */
	public int deleteJoin(int eloveid){
		String SQL = "DELETE FROM elove_join WHERE eloveid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{eloveid});
		return effected;
	}
	
	/**
	 * @title: deleteMessage
	 * @description: 删除与eloveid相关的所有祝福记录 
	 * @param eloveid
	 * @return
	 */
	public int deleteMessage(int eloveid){
		String SQL = "DELETE FROM elove_message WHERE eloveid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{eloveid});
		return effected;
	}
	
	/**
	 * @title: deleteElove
	 * @description: 删除elove主记录
	 * @param eloveid
	 * @return
	 */
	public int deleteElove(int eloveid){
		String SQL = "DELETE FROM elove WHERE eloveid = ?";
		
		EloveWizard eloveWizard = getEloveBasicMedia(eloveid);                              //插入待删除资源到临时表
		Timestamp current = new Timestamp(System.currentTimeMillis());
		insertImageTempRecord(eloveWizard.getCoverPic(), current);
		insertImageTempRecord(eloveWizard.getMajorGroupPhoto(), current);
		insertImageTempRecord(eloveWizard.getStoryTextImagePath(), current);
		insertVideoTempRecord(eloveWizard.getMusic(), current);
		
		int effected = jdbcTemplate.update(SQL, new Object[]{eloveid});
		return effected;
	}
	
	/**
	 * @title: deleteConsumeRecord
	 * @description: 删除指定app的消费记录
	 * @param appid
	 * @return
	 */
	public int deleteConsumeRecord(String appid){
		String SQL = "DELETE FROM elove_consume_record WHERE appid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{appid});
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
	
	/**
	 * @title: deleteVideoTempRecord
	 * @description: 删除图片在临时表中记录
	 * @param videoPath
	 * @return
	 */
	private int deleteVideoTempRecord(String videoPath){
		String SQL = "DELETE FROM video_temp_record WHERE videoPath = ?";
		int effected = jdbcTemplate.update(SQL, videoPath);
		return effected;
	}
	
	//update
	/**
	 * @title: updateElove
	 * @description: 更新elove记录
	 * @param eloveWizard
	 * @return
	 */
	public int updateElove(EloveWizard eloveWizard){
		String SQL = "UPDATE elove SET title = ?, password = ?, coverPic = ?, "
				+ "coverText = ?, majorGroupPhoto = ?, storyTextImagePath = ?, "
				+ "music = ?, phone = ?, weddingDate = ?, weddingAddress = ?, "
				+ "lng = ?, lat = ?, shareTitle = ?, shareContent = ?, footerText = ?, "
				+ "sideCorpInfo = ?, themeid = ? WHERE eloveid = ?";
		
		EloveWizard temp = getEloveBasicMedia(eloveWizard.getEloveid());
		if (temp == null) {
			return 0;
		}
		
		int effected = jdbcTemplate.update(SQL, new Object[]{eloveWizard.getTitle(), 
				eloveWizard.getPassword(), eloveWizard.getCoverPic(), eloveWizard.getCoverText(), 
				eloveWizard.getMajorGroupPhoto(), eloveWizard.getStoryTextImagePath(), 
				eloveWizard.getMusic(), eloveWizard.getPhone(), eloveWizard.getWeddingDate(), 
				eloveWizard.getWeddingAddress(), eloveWizard.getLng(), eloveWizard.getLat(), 
				eloveWizard.getShareTitle(), eloveWizard.getShareContent(), eloveWizard.getFooterText(), 
				eloveWizard.getSideCorpInfo(), eloveWizard.getThemeid(), eloveWizard.getEloveid()});
		
		if (effected > 0 ) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			if (!eloveWizard.getCoverPic().equals(temp.getCoverPic())) {                      //删除临时表中记录
				deleteImageTempRecord(eloveWizard.getCoverPic());
				insertImageTempRecord(temp.getCoverPic(), current);
			}
			if (!eloveWizard.getMajorGroupPhoto().equals(temp.getMajorGroupPhoto())) {
				deleteImageTempRecord(eloveWizard.getMajorGroupPhoto());
				insertImageTempRecord(temp.getMajorGroupPhoto(), current);
			}
			if (!eloveWizard.getStoryTextImagePath().equals(temp.getStoryTextImagePath())) {
				deleteImageTempRecord(eloveWizard.getStoryTextImagePath());
				insertImageTempRecord(temp.getStoryTextImagePath(), current);
			}
			if (!eloveWizard.getMusic().equals(temp.getMusic())) {
				deleteVideoTempRecord(eloveWizard.getMusic());
				insertVideoTempRecord(temp.getMusic(), current);
			}
			
			int eloveid = eloveWizard.getEloveid();

			//story
			List<String> originalStoryImages = getImageListWithType(eloveid, "story");
			List<String> currentStoryImages = eloveWizard.getStoryImagePath();
			
			if (currentStoryImages == null) {
				currentStoryImages = new ArrayList<String>();
			}
			for (int i = 0; i < originalStoryImages.size(); i++) {
				String imagePath = originalStoryImages.get(i);
				if (!currentStoryImages.contains(imagePath)) {
				    deleteImage(imagePath);
				}
			}
			for (int i = 0; i < currentStoryImages.size(); i++) {
				String imagePath = currentStoryImages.get(i);
				if (!originalStoryImages.contains(imagePath)) {
					insertImage(eloveid, "story", imagePath);
				}
			}
			
			//dress
			List<String> originalDressImages = getImageListWithType(eloveid, "dress");
			List<String> currentDressImages = eloveWizard.getDressImagePath();
			
			if (currentDressImages == null) {
				currentDressImages = new ArrayList<String>();
			}
			for (int i = 0; i < originalDressImages.size(); i++) {
				String imagePath = originalDressImages.get(i);
				if (!currentDressImages.contains(imagePath)) {
				    deleteImage(imagePath);
				}
			}
			for (int i = 0; i < currentDressImages.size(); i++) {
				String imagePath = currentDressImages.get(i);
				if (!originalDressImages.contains(imagePath)) {
					insertImage(eloveid, "dress", imagePath);
				}
			}
			
			String originalDressVideo = getVideoWithType(eloveid, "dress");
			String currentDressVideo = eloveWizard.getDressVideoPath();
			
			if (currentDressVideo == null) {
				currentDressVideo = "";
			}
			if (!currentDressVideo.equals(originalDressVideo)) {
				if (originalDressVideo != null) {
					deleteVideo(originalDressVideo);
				}
				if (!currentDressVideo.equals("")) {
					insertVideo(eloveid, "dress", currentDressVideo);
				}
			}
			
			//record
			List<String> originalRecordImages = getImageListWithType(eloveid, "record");
			List<String> currentRecordImages = eloveWizard.getRecordImagePath();
			
			if (currentRecordImages == null) {
				currentRecordImages = new ArrayList<String>();
			}
			for (int i = 0; i < originalRecordImages.size(); i++) {
				String imagePath = originalRecordImages.get(i);
				if (!currentRecordImages.contains(imagePath)) {
				    deleteImage(imagePath);
				}
			}
			for (int i = 0; i < currentRecordImages.size(); i++) {
				String imagePath = currentRecordImages.get(i);
				if (!originalRecordImages.contains(imagePath)) {
					insertImage(eloveid, "dress", imagePath);
				}
			}
			
			String originalRecordVideo = getVideoWithType(eloveid, "record");
			String currentRecordVideo = eloveWizard.getRecordVideoPath();
			
			if (currentRecordVideo == null) {
				currentRecordVideo = "";
			}
			if (!currentRecordVideo.equals(originalRecordVideo)) {
				if (originalRecordVideo != null) {
					deleteVideo(originalRecordVideo);
				}
				if (!currentRecordVideo.equals("")) {
					insertVideo(eloveid, "dress", currentRecordVideo);
				}
			}
			
			return effected;			
		}
		else {
			return 0;
		}		
	}
	
	/**
	 * @title: updateConsumeRecord
	 * @description: 更新elove消费记录
	 * @param notPayNumber
	 * @param appid
	 * @return
	 */
	private int updateConsumeRecord(int notPayNumber, String appid){
		String SQL = "UPDATE elove_consume_record SET notPayNumber = ? WHERE appid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{notPayNumber, appid});
		return effected <= 0 ? 0 : effected;
	}
	
	//query
	/**
	 * @title: getImageList
	 * @description: 查询与eloveid相关的图片路径信息
	 * @param eloveid
	 * @return
	 */
	public List<String> getImageList(int eloveid){
		String SQL = "SELECT imagePath FROM elove_images WHERE eloveid = ?";
		List<String> imageList = null;
		try {
			imageList = jdbcTemplate.query(SQL, new Object[]{eloveid}, new ImagePathMapper());
		} catch (Exception e) {
			imageList = new ArrayList<String>();
			System.out.println(e.getMessage());
		}
		return imageList;
	}
	
	/**
	 * @title: getImageListWithType
	 * @description: 查询与eloveid相关的指定类型的图片路径信息
	 * @param eloveid
	 * @param imageType   "story"  or  "dress"  or  "record"
	 * @return
	 */
	public List<String> getImageListWithType(int eloveid, String imageType){
		String SQL = "SELECT imagePath FROM elove_images WHERE eloveid = ? AND imageType = ?";
		List<String> imageList = null;
		try {
			imageList = jdbcTemplate.query(SQL, new Object[]{eloveid, imageType}, new ImagePathMapper());
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
	 * @title: getVideoList
	 * @description: 查询与eloveid相关的视频路径信息
	 * @param eloveid
	 * @return
	 */
	public List<String> getVideoList(int eloveid){
		String SQL = "SELECT videoPath FROM elove_video WHERE eloveid = ?";
		List<String> videoList = null;
		try {
			videoList = jdbcTemplate.query(SQL, new Object[]{eloveid}, new VideoPathMapper());
		} catch (Exception e) {
			videoList = new ArrayList<String>();
			System.out.println(e.getMessage());
		}
		return videoList;
	}
	
	/**
	 * @title: getVideoWithType
	 * @description: 查询与eloveid相关的指定类型的唯一视频路径信息
	 * @param eloveid
	 * @param videoType
	 * @return
	 */
	public String getVideoWithType(int eloveid, String videoType){
		String SQL = "SELECT videoPath FROM elove_video WHERE eloveid = ? AND videoType = ?";
		String videoPath = null;
		try {
			videoPath = jdbcTemplate.queryForObject(SQL, new Object[]{eloveid, videoType}, new VideoPathMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return videoPath;
	}
	
	private static final class VideoPathMapper implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String videoPath = rs.getString("videoPath");
			return videoPath;
		}		
	}
	
	/**
	 * @title: getElove
	 * @description: 获取elove完整信息
	 * @param eloveid
	 * @return
	 */
	public EloveWizard getElove(int eloveid){
		String SQL = "SELECT * FROM elove WHERE eloveid = ?";
		EloveWizard eloveWizard = null;
		try {
			eloveWizard = jdbcTemplate.queryForObject(SQL, new Object[]{eloveid}, new EloveWizardMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (eloveWizard != null) {
			eloveWizard.setStoryImagePath(getImageListWithType(eloveid, "story"));
			eloveWizard.setDressImagePath(getImageListWithType(eloveid, "dress"));
			eloveWizard.setDressVideoPath(getVideoWithType(eloveid, "dress"));
			eloveWizard.setRecordImagePath(getImageListWithType(eloveid, "record"));
			eloveWizard.setRecordVideoPath(getVideoWithType(eloveid, "record"));
		}
		return eloveWizard;
	}
	
	/**
	 * @title: getOnlyElove
	 * @description: 获取elove除图片视频路径外的全部信息
	 * @param eloveid
	 * @return
	 */
	public EloveWizard getOnlyElove(int eloveid){
		String SQL = "SELECT * FROM elove WHERE eloveid = ?";
		EloveWizard eloveWizard = null;
		try {
			eloveWizard = jdbcTemplate.queryForObject(SQL, new Object[]{eloveid}, new EloveWizardMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return eloveWizard;
	}
	
	private static final class EloveWizardMapper implements RowMapper<EloveWizard>{
		@Override
		public EloveWizard mapRow(ResultSet rs, int arg1) throws SQLException {
			EloveWizard eloveWizard = new EloveWizard();
			eloveWizard.setEloveid(rs.getInt("eloveid"));
			eloveWizard.setAppid(rs.getString("appid"));
			eloveWizard.setCreateTime(rs.getTimestamp("createTime"));
			eloveWizard.setExpiredTime(rs.getTimestamp("expiredTime"));
			eloveWizard.setTitle(rs.getString("title"));
			eloveWizard.setPassword(rs.getString("password"));
			eloveWizard.setCoverPic(rs.getString("coverPic"));
			eloveWizard.setCoverText(rs.getString("coverText"));
			eloveWizard.setMajorGroupPhoto(rs.getString("majorGroupPhoto"));
			eloveWizard.setXinNiang(rs.getString("xinNiang"));
			eloveWizard.setXinLang(rs.getString("xinLang"));
			eloveWizard.setStoryTextImagePath(rs.getString("storyTextImagePath"));
			eloveWizard.setMusic(rs.getString("music"));
			eloveWizard.setPhone(rs.getString("phone"));
			eloveWizard.setWeddingDate(rs.getString("weddingDate"));
			eloveWizard.setWeddingAddress(rs.getString("weddingAddress"));
			eloveWizard.setLng(rs.getBigDecimal("lng"));
			eloveWizard.setLat(rs.getBigDecimal("lat"));
			eloveWizard.setShareTitle(rs.getString("shareTitle"));
			eloveWizard.setShareContent(rs.getString("shareContent"));
			eloveWizard.setFooterText(rs.getString("footerText"));
			eloveWizard.setSideCorpInfo(rs.getString("sideCorpInfo"));
			eloveWizard.setThemeid(rs.getInt("themeid"));
			return eloveWizard;
		}		
	}
	
	/**
	 * @title: getBasicElove
	 * @description: 获取elove验证时所需信息
	 * @param eloveid
	 * @return
	 */
	public EloveWizard getBasicElove(int eloveid){
		String SQL = "SELECT password FROM elove WHERE eloveid = ?";
		EloveWizard eloveWizard = null;
		
		try {
			eloveWizard = jdbcTemplate.queryForObject(SQL, new Object[]{eloveid}, new EloveBasicMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		return eloveWizard;
	}
	
	private static final class EloveBasicMapper implements RowMapper<EloveWizard>{
		@Override
		public EloveWizard mapRow(ResultSet rs, int arg1) throws SQLException {
			EloveWizard eloveWizard = new EloveWizard();
			eloveWizard.setPassword(rs.getString("password"));
			return eloveWizard;
		}		
	}
	
	/**
	 * @title: getEloveBasicMedia
	 * @description: 获取elove基础的图片、音乐配置路径
	 * @param eloveid
	 * @return
	 */
	public EloveWizard getEloveBasicMedia(int eloveid){
		String SQL = "SELECT coverPic, majorGroupPhoto, storyTextImagePath, music FROM elove WHERE eloveid = ?";
		EloveWizard eloveWizard = null;
		
		try {
			eloveWizard = jdbcTemplate.queryForObject(SQL, new Object[]{eloveid}, new BasicMediaMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return eloveWizard;
	}
	
	private static final class BasicMediaMapper implements RowMapper<EloveWizard>{
		@Override
		public EloveWizard mapRow(ResultSet rs, int arg1) throws SQLException {
			EloveWizard eloveWizard = new EloveWizard();
			eloveWizard.setCoverPic(rs.getString("coverPic"));
			eloveWizard.setMajorGroupPhoto(rs.getString("majorGroupPhoto"));
			eloveWizard.setStoryTextImagePath(rs.getString("storyTextImagePath"));
			eloveWizard.setMusic(rs.getString("music"));
			return eloveWizard;
		}	
	}
	
	/**
	 * @title: getEloveid
	 * @description: 根据密码查询eloveid
	 * @param appid
	 * @param password
	 * @return
	 */
	public Integer getEloveid(String appid, String password){
		String SQL = "SELECT eloveid FROM elove WHERE appid = ? AND password = ?";
		Integer eloveid = null;
		
		try {
			eloveid = jdbcTemplate.queryForObject(SQL, new Object[]{appid, password}, new EloveidMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return eloveid;
	}
	
	/**
	 * @title: checkPassword
	 * @description: 检查密码是否已被使用
	 * @param password
	 * @return
	 */
	public int checkPassword(String appid, String password){
		String SQL = "SELECT COUNT(*) FROM elove WHERE appid = ? AND password = ?";
		int count = 1;
		
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, appid, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	/**
	 * @title: getEloveNum
	 * @description: 获取一个appid下的所有elove数量
	 * @param appid
	 * @return
	 */
	public int getEloveNum(String appid){
		String SQL = "SELECT COUNT(*) FROM elove WHERE appid = ?";
		int count = 0;
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, appid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	/**
	 * @title: getEloveidList
	 * @description: 获取一个appid下的所有eloveid
	 * @param appid
	 * @return
	 */
	public List<Integer> getEloveidList(String appid){
		String SQL = "SELECT eloveid FROM elove WHERE appid = ?";
		List<Integer> eloveidList = null;
		try {
			eloveidList = jdbcTemplate.query(SQL, new Object[]{appid}, new EloveidMapper());
		} catch (Exception e) {
			eloveidList = new ArrayList<Integer>();
			System.out.println(e.getMessage());
		}
		return eloveidList;
	}
	
	private static final class EloveidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer eloveid = rs.getInt("eloveid");
			return eloveid;
		}		
	}
	
	/**
	 * @title: getConsumeRecord
	 * @description: 查询elove消费情况
	 * @param appid
	 * @return
	 */
	public Integer getConsumeRecord(String appid){
		String SQL = "SELECT notPayNumber FROM elove_consume_record WHERE appid = ?";
		Integer notPayNumber = null;
		try {
			notPayNumber = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, new NotPayNumberMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return notPayNumber;
	}
	
	private static final class NotPayNumberMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer notPayNumber = rs.getInt("notPayNumber");
			return notPayNumber;
		}		
	}
	
	/**
	 * @title: getThemeidList
	 * @description: 获取主题id列表
	 * @return
	 */
	public List<Integer> getThemeidList(){
		String SQL = "SELECT id FROM elove_theme";
		List<Integer> themeidList = null;
		
		try {
			themeidList = jdbcTemplate.query(SQL, new ThemeidMapper());
		} catch (Exception e) {
			themeidList = new ArrayList<Integer>();
			System.out.println(e.getMessage());
		}
		return themeidList;
	}
	
	private static final class ThemeidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer themeid = rs.getInt("id");
			return themeid;
		}	
	}
}
