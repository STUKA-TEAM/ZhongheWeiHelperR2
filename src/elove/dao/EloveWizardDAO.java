package elove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		final String SQL = "INSERT INTO elove VALUES (default, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
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
			final int eloveid = result;
			
			result = insertImage(eloveid, "story", eloveWizard.getStoryImagePath());        //插入相遇相知图片
            if (result <= 0) {
				return -1;
			}
			
			result = insertImage(eloveid, "dress", eloveWizard.getDressImagePath());        //插入婚纱剪影图片
			if (result <= 0) {
				return -2;
			}
			
			result = insertVideo(eloveid, "dress", eloveWizard.getDressVideoPath());        //插入婚纱剪影录像
			if (result <= 0) {
				return -3;
			}
			
			result = insertImage(eloveid, "record", eloveWizard.getRecordImagePath());      //插入婚礼纪录图片
			if (result <= 0) {
				return -4;
			}
			
			result = insertVideo(eloveid, "record", eloveWizard.getRecordVideoPath());      //插入婚礼纪录录像
			if (result <= 0) {
				return -5;
			}
			
			Integer notPayNumber = getConsumeRecord(eloveWizard.getAppid());                //更新elove消费情况
			if (notPayNumber != null) {
				result = updateConsumeRecord(notPayNumber + 1, eloveWizard.getAppid());
				if (result <= 0) {
					return -6;
				}
			}else {
				return -7;
			}			
			
			return eloveid;
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
	public int insertImage(final int eloveid, final String imageType, List<String> imagePathList){
		final String sqlImage = "INSERT INTO elove_images VALUES (default, ?, ?, ?)";
		int result = 0;
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		for (int i = 0; i < imagePathList.size(); i++) {
			final String imagePath = imagePathList.get(i);
			result = jdbcTemplate.update(new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
			        PreparedStatement ps =
			            connection.prepareStatement(sqlImage, Statement.RETURN_GENERATED_KEYS);
			        ps.setInt(1, eloveid);
			        ps.setString(2, imagePath);
			        ps.setString(3, imageType);
			        return ps;
			    }
			}, kHolder);
			if (result <= 0) {
				return 0;
			}
		}		
		return result;
	}
	
	/**
	 * @title: insertVideo
	 * @description: 插入录像记录
	 * @param eloveid
	 * @param imageType
	 * @param imagePathList
	 * @return
	 */
	public int insertVideo(final int eloveid, final String videoType, List<String> videoPathList){
		final String sqlVideo = "INSERT INTO elove_video VALUES (default, ?, ?, ?)";
		int result = 0;
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		for (int i = 0; i < videoPathList.size(); i++) {
			final String videoPath = videoPathList.get(i);
			result = jdbcTemplate.update(new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
			        PreparedStatement ps =
			            connection.prepareStatement(sqlVideo, Statement.RETURN_GENERATED_KEYS);
			        ps.setInt(1, eloveid);
			        ps.setString(2, videoPath);
			        ps.setString(3, videoType);
			        return ps;
			    }
			}, kHolder);
			if (result <= 0) {
				return 0;
			}
		}		
		return result;
	}

	//delete
	/**
	 * @title: deleteImage
	 * @description: 删除与eloveid相关的所有图片
	 * @param eloveid
	 * @return
	 */
	public int deleteImage(int eloveid){
		String SQL = "DELETE FROM elove_images WHERE eloveid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{eloveid});
		return effected;
	}
	
	/**
	 * @title: deleteVideo
	 * @description: 删除与eloveid相关的所有视频
	 * @param eloveid
	 * @return
	 */
	public int deleteVideo(int eloveid){
		String SQL = "DELETE FROM elove_video WHERE eloveid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{eloveid});
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
		int effected = jdbcTemplate.update(SQL, new Object[]{eloveWizard.getTitle(), 
				eloveWizard.getPassword(), eloveWizard.getCoverPic(), eloveWizard.getCoverText(), 
				eloveWizard.getMajorGroupPhoto(), eloveWizard.getStoryTextImagePath(), 
				eloveWizard.getMusic(), eloveWizard.getPhone(), eloveWizard.getWeddingDate(), 
				eloveWizard.getWeddingAddress(), eloveWizard.getLng(), eloveWizard.getLat(), 
				eloveWizard.getShareTitle(), eloveWizard.getShareContent(), eloveWizard.getFooterText(), 
				eloveWizard.getSideCorpInfo(), eloveWizard.getThemeid(), eloveWizard.getEloveid()});
		if (effected > 0 ) {
			int eloveid = eloveWizard.getEloveid();

			deleteImage(eloveid);     //删除所有相关的图片记录
			deleteVideo(eloveid);     //删除所有相关的视频记录
			
			effected = insertImage(eloveid, "story", eloveWizard.getStoryImagePath());        //插入相遇相知图片
            if (effected <= 0) {
				return -1;
			}
			
            effected = insertImage(eloveid, "dress", eloveWizard.getDressImagePath());        //插入婚纱剪影图片
			if (effected <= 0) {
				return -2;
			}
			
			effected = insertVideo(eloveid, "dress", eloveWizard.getDressVideoPath());        //插入婚纱剪影录像
			if (effected <= 0) {
				return -3;
			}
			
			effected = insertImage(eloveid, "record", eloveWizard.getRecordImagePath());      //插入婚礼纪录图片
			if (effected <= 0) {
				return -4;
			}
			
			effected = insertVideo(eloveid, "record", eloveWizard.getRecordVideoPath());      //插入婚礼纪录录像
			if (effected <= 0) {
				return -5;
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
	public int updateConsumeRecord(int notPayNumber, String appid){
		String SQL = "UPDATE elove_consume_record SET notPayNumber = ? WHERE appid = ?";
		int effected = jdbcTemplate.update(SQL, new Object[]{notPayNumber, appid});
		return effected;
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
			System.out.println(e.getMessage());
		}
		return imageList;
	}
	
	/**
	 * @title: getImageListWithType
	 * @description: 查询与eloveid相关的指定类型的图片路径信息
	 * @param eloveid
	 * @param imageType
	 * @return
	 */
	public List<String> getImageListWithType(int eloveid, String imageType){
		String SQL = "SELECT imagePath FROM elove_images WHERE eloveid = ? AND imageType = ?";
		List<String> imageList = null;
		try {
			imageList = jdbcTemplate.query(SQL, new Object[]{eloveid, imageType}, new ImagePathMapper());
		} catch (Exception e) {
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
			System.out.println(e.getMessage());
		}
		return videoList;
	}
	
	/**
	 * @title: getVideoListWithType
	 * @description: 查询与eloveid相关的指定类型的视频路径信息
	 * @param eloveid
	 * @param videoType
	 * @return
	 */
	public List<String> getVideoListWithType(int eloveid, String videoType){
		String SQL = "SELECT videoPath FROM elove_video WHERE eloveid = ? AND videoType = ?";
		List<String> videoList = null;
		try {
			videoList = jdbcTemplate.query(SQL, new Object[]{eloveid, videoType}, new VideoPathMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return videoList;
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
			eloveWizard.setDressVideoPath(getVideoListWithType(eloveid, "dress"));
			eloveWizard.setRecordImagePath(getImageListWithType(eloveid, "record"));
			eloveWizard.setRecordVideoPath(getVideoListWithType(eloveid, "record"));
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
}
