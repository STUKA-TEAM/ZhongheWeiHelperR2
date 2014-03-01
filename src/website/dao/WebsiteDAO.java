package website.dao;

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

import website.Website;
import website.WebsiteNode;

/**
 * @Title: WebsiteDAO
 * @Description: DAO for website model
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月24日
 */
public class WebsiteDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title: insertWebsite
	 * @description: 插入微官网信息
	 * @param website
	 * @return
	 */
	public int insertWebsite(final Website website){
		int result = 0;
		final String SQL = "INSERT INTO website (websiteid, appid, getCode, title, "
				+ "phone, address, lng, lat, createTime, coverPic, "
				+ "coverText, shareTitle, shareContent, footerText, themeId) "
				+ "VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, website.getAppid());
		        ps.setString(2, website.getGetCode());
		        ps.setString(3, website.getTitle());
		        ps.setString(4, website.getPhone());
		        ps.setString(5, website.getAddress());
		        ps.setBigDecimal(6, website.getLng());
		        ps.setBigDecimal(7, website.getLat());
		        ps.setTimestamp(8, website.getCreateTime());
		        ps.setString(10, website.getCoverPic());
		        ps.setString(11, website.getCoverText());
		        ps.setString(12, website.getShareTitle());
		        ps.setString(13, website.getShareContent());
		        ps.setString(14, website.getFooterText());
		        ps.setInt(15, website.getThemeId());
		        return ps;
		    }
		}, kHolder);
		
		if (result > 0) {
			deleteImageTempRecord(website.getCoverPic());
			
			int websiteid = kHolder.getKey().intValue();
			
			result = insertImage(websiteid, "introduce", website.getImageList());        //插入微官网介绍图片
            if (result == 0) {
				return -1;
			}
            
            result = insertNodeList(websiteid, website.getNodeList());                      //插入微官网节点信息
            if (result == 0) {
				return -2;
			}
            
            return result;
		}else {
			return 0;
		}	
	}
	
	/**
	 * @title: insertNodeList
	 * @description: 插入节点列表信息
	 * @param websiteid
	 * @param nodeList
	 * @return
	 */
	private int insertNodeList(int websiteid, List<WebsiteNode> nodeList){
		int result = 1;
		
		if (nodeList == null) {
			return result;
		}
		
		for (int i = 0; i < nodeList.size(); i++) {
			WebsiteNode node = nodeList.get(i);
			result = insertNode(websiteid, node);
			if (result == 0) {
				return result;
			}else {
				String nodePic = node.getNodePic();
				if (nodePic != null && !nodePic.equals("")) {
					deleteImageTempRecord(node.getNodePic());
				}
				node.setNodeid(result);
			}
		}
		
		result = insertNodeTree(nodeList);	
		return result;
	}
	
	/**
	 * @title: insertNodeTree
	 * @description: 插入微官网节点树结构信息
	 * @param nodeList
	 * @return
	 */
	private int insertNodeTree(List<WebsiteNode> nodeList){
		String SQL = "INSERT INTO website_nodetree (id, nodeid, childid) VALUES (default, ?, ?)";
		int result = 1;
		
		if (nodeList == null) {
			return result;
		}
		
		for (int i = 0; i < nodeList.size(); i++) {
			WebsiteNode node1 = nodeList.get(i);
			
			for (int j = 0; j < nodeList.size(); j++) {
				WebsiteNode node2 = nodeList.get(j);
				if (node1.getFatherUUID().equals(node2.getUUID())) {
					result = jdbcTemplate.update(SQL, node2.getNodeid(), node1.getNodeid());
					if (result <= 0) {
						return 0;
					}
					break;
				}
			}
			
			if (node1.getChildrenType().equals("article")) {
				result = jdbcTemplate.update(SQL, node1.getNodeid(), node1.getArticleid());
				if (result <= 0) {
					return 0;
				}
			}
			
			if (node1.getChildrenType().equals("articleclass")) {
				result = jdbcTemplate.update(SQL, node1.getNodeid(), node1.getClassid());
				if (result <= 0) {
					return 0;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * @title: insertNode
	 * @description: 插入微官网单个节点信息
	 * @param websiteid
	 * @param node
	 * @return
	 */
	private int insertNode(final int websiteid, final WebsiteNode node){
		final String SQL = "INSERT INTO website_node (nodeid, websiteid, nodeName, "
				+ "nodePic, childrenType) VALUES (default, ?, ?, ?, ?)";
		int result = 0;
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setInt(1, websiteid);
		        ps.setString(2, node.getNodeName());
		        ps.setString(3, node.getNodePic());
		        ps.setString(4, node.getChildrenType());
		        return ps;
		    }
		}, kHolder);
		
		return result <= 0 ? 0 : kHolder.getKey().intValue();
	}
	
	/**
	 * @title: insertImage
	 * @description: 插入图片记录
	 * @param websiteid
	 * @param imageType
	 * @param imagePathList
	 * @return
	 */
	private int insertImage(int websiteid, String imageType, List<String> imageList){
	    String SQL = "INSERT INTO website_image (id, websiteid, imageType, imagePath) VALUES (default, ?, ?, ?)";
		int result = 1;

		if (imageList == null) {
			return 1;
		}
		
		for (int i = 0; i < imageList.size(); i++) {
			String imagePath = imageList.get(i);
			result = jdbcTemplate.update(SQL, websiteid, imageType, imagePath);
			if (result <= 0) {
				return 0;
			}
		}
		
		for (int i = 0; i < imageList.size(); i++) {
			deleteImageTempRecord(imageList.get(i));
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
	
	//delete
	/**
	 * @title: deleteWebsite
	 * @description: 删除微官网信息
	 * @param websiteid
	 * @return
	 */
	public int deleteWebsite(int websiteid){
		int result = 0;
		String SQL = "DELETE FROM website WHERE websiteid = ?";
		
		Website temp = getWebsiteBasicMedia(websiteid);
		if (temp != null) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			insertImageTempRecord(temp.getCoverPic(), current);
		}else {
			return 0;
		}
		
		result = jdbcTemplate.update(SQL, websiteid);
		if (result > 0) {
			deleteImage(websiteid);
			deleteNodes(websiteid);
			return result;
		}else {
			deleteImageTempRecord(temp.getCoverPic());
			return 0;
		}	
	}
	
	/**
	 * @title: deleteNodes
	 * @description: 删除节点关联信息
	 * @param websiteid
	 * @return
	 */
	public int deleteNodes(int websiteid){
		int effected = 0;
		String SQL = "DELETE FROM website_node WHERE websiteid = ?";
		
		List<WebsiteNode> nodeList = getWebsiteNodeinfo(websiteid);
		for (int i = 0; i < nodeList.size(); i++) {
			WebsiteNode node = nodeList.get(i);
			deleteNodeTree(node.getNodeid());
			String nodePic = node.getNodePic();
			if (nodePic != null && !nodePic.equals("")) {
				Timestamp current = new Timestamp(System.currentTimeMillis());
				insertImageTempRecord(nodePic, current);
			}
		}
		
		effected = jdbcTemplate.update(SQL, websiteid);
		return effected;
	}
	
	/**
	 * @title: deleteNodeTree
	 * @description: 根据nodeid删除节点树中关联的信息记录
	 * @param nodeid
	 * @return
	 */
	public int deleteNodeTree(int nodeid){
		String SQL = "DELETE FROM website_nodetree WHERE nodeid = ?";
		int effected = jdbcTemplate.update(SQL, nodeid);
		return effected;
	}
	
	/**
	 * @title: deleteImage
	 * @description: 删除与websiteid相关的所有图片
	 * @param websiteid
	 * @return
	 */
	public int deleteImage(int websiteid){
		int effected = 0;
		String SQL = "DELETE FROM website_image WHERE websiteid = ?";
		
		List<String> imageList = getWebsiteImages(websiteid);
		Timestamp current = new Timestamp(System.currentTimeMillis());
		for (int i = 0; i < imageList.size(); i++) {
			String imagePath = imageList.get(i);
			insertImageTempRecord(imagePath, current);
		}
		
		effected = jdbcTemplate.update(SQL, websiteid);			
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
	 * @title: updateWebsite
	 * @description: 更新微官网信息
	 * @param website
	 * @return
	 */
	public int updateWebsite(Website website){
		String SQL = "UPDATE website SET getCode = ?, title = ?, phone = ?, "
				+ "address = ?, lng = ?, lat = ?, coverPic = ?, coverText = ?, "
				+ "shareTitle = ?, shareContent = ?, footerText = ?, themeId = ? "
				+ "WHERE websiteid = ?";
		int result = 0;
		
		Website temp = getWebsiteBasicMedia(website.getWebsiteid());
		if (temp != null) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			insertImageTempRecord(temp.getCoverPic(), current);
		}else {
			return 0;
		}
		
		result = jdbcTemplate.update(SQL, website.getGetCode(), website.getTitle(), 
				website.getPhone(), website.getAddress(), website.getLng(), website.getLat(), 
				website.getCoverPic(), website.getCoverText(), website.getShareTitle(), 
				website.getShareContent(), website.getFooterText(), website.getThemeId(), 
				website.getWebsiteid());
		if (result > 0) {
			deleteImageTempRecord(website.getCoverPic());
			
			int websiteid = website.getWebsiteid();
			deleteImage(websiteid);
			deleteNodes(websiteid);
			
			result = insertImage(websiteid, "introduce", website.getImageList());        //插入微官网介绍图片
            if (result == 0) {
				return -1;
			}
            
            result = insertNodeList(websiteid, website.getNodeList());                      //插入微官网节点信息
            if (result == 0) {
				return -2;
			}
			
            return result;
		}else {
			return 0;
		}		
	}
	
	//query
	/**
	 * @title: getDetailedWebsiteInfo
	 * @description: 根据微官网id获取微官网详细信息
	 * @param websiteid
	 * @return
	 */
	public Website getDetailedWebsiteInfo(int websiteid){
		String SQL = "SELECT websiteid, getCode, title, phone, address, lng, lat, "
				+ "coverPic, coverText, shareTitle, shareContent, "
				+ "footerText, themeId FROM website WHERE websiteid = ?";
		Website website = null;
		
		try {
			website = jdbcTemplate.queryForObject(SQL, new Object[]{websiteid}, new DetailedWebsiteInfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (website != null) {
			website.setImageList(getWebsiteImagesWithType(websiteid, "introduce"));
			website.setNodeList(getWebsiteNodeList(websiteid));
		}
		return website;
	}
	
	private static final class DetailedWebsiteInfoMapper implements RowMapper<Website>{
		@Override
		public Website mapRow(ResultSet rs, int arg1) throws SQLException {
			Website website = new Website();
			website.setWebsiteid(rs.getInt("websiteid"));
			website.setGetCode(rs.getString("getCode"));
			website.setTitle(rs.getString("title"));
			website.setPhone(rs.getString("phone"));
			website.setAddress(rs.getString("address"));
			website.setLng(rs.getBigDecimal("lng"));
			website.setLat(rs.getBigDecimal("lat"));
			website.setCoverPic(rs.getString("coverPic"));
			website.setCoverText(rs.getString("coverText"));
			website.setShareTitle(rs.getString("shareTitle"));
			website.setShareContent(rs.getString("shareContent"));
			website.setFooterText(rs.getString("footerText"));
			website.setThemeId(rs.getInt("themeId"));
			return website;
		}		
	}
	
	/**
	 * @title: getWebsiteInfoForCustomer
	 * @description: 根据微官网id获取手机端所需微官网基本信息
	 * @param websiteid
	 * @return
	 */
	public Website getWebsiteInfoForCustomer(int websiteid){
		String SQL = "SELECT websiteid, appid, title, phone, address, lng, lat, "
				+ "footerText, themeId FROM website WHERE websiteid = ?";
		Website website = null;
		
		try {
			website = jdbcTemplate.queryForObject(SQL, new Object[]{websiteid}, new CustomerWebsiteInfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return website;
	}
		
	private static final class CustomerWebsiteInfoMapper implements RowMapper<Website>{
		@Override
		public Website mapRow(ResultSet rs, int arg1) throws SQLException {
			Website website = new Website();
			website.setWebsiteid(rs.getInt("websiteid"));
			website.setAppid(rs.getString("appid"));
			website.setTitle(rs.getString("title"));
			website.setPhone(rs.getString("phone"));
			website.setAddress(rs.getString("address"));
			website.setLng(rs.getBigDecimal("lng"));
			website.setLat(rs.getBigDecimal("lat"));
			website.setFooterText(rs.getString("footerText"));
			website.setThemeId(rs.getInt("themeId"));
			return website;
		}		
	}
	
	/**
	 * @title: getBasicWebsiteInfo
	 * @description: 获取微官网基本信息
	 * @param appid
	 * @return
	 */
	public Website getBasicWebsiteInfo(String appid){
		String SQL = "SELECT websiteid, getCode, title, createTime FROM website WHERE appid = ?";
		Website website = null;
		
		try {
			website = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, new BasicWebsiteInfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return website;
	}
	
	private static final class BasicWebsiteInfoMapper implements RowMapper<Website>{
		@Override
		public Website mapRow(ResultSet rs, int arg1) throws SQLException {
			Website website = new Website();
			website.setWebsiteid(rs.getInt("websiteid"));
			website.setGetCode(rs.getString("getCode"));
			website.setTitle(rs.getString("title"));
			website.setCreateTime(rs.getTimestamp("createTime"));
			return website;
		}		
	}
	
	/**
	 * @title: getWebsiteImages
	 * @description: 查询与websiteid相关的图片路径信息
	 * @param websiteid
	 * @return
	 */
	public List<String> getWebsiteImages(int websiteid){
		String SQL = "SELECT imagePath FROM website_image WHERE websiteid = ?";
		List<String> imageList = null;
		
		try {
			imageList = jdbcTemplate.query(SQL, new Object[]{websiteid}, new ImagePathMapper());
		} catch (Exception e) {
			imageList = new ArrayList<String>();
			System.out.println(e.getMessage());
		}
		return imageList;
	}
	
	/**
	 * @title: getWebsiteImagesWithType
	 * @description: 根据网站id和图片类型选择图片。 可选图片类型：  introduce;
	 * @param websiteid
	 * @param imageType
	 * @return
	 */
	public List<String> getWebsiteImagesWithType(int websiteid, String imageType){
		String SQL = "SELECT imagePath FROM website_image WHERE websiteid = ? AND imageType = ?";
		List<String> imageList = null;
		
		try {
			imageList = jdbcTemplate.query(SQL, new Object[]{websiteid, imageType}, new ImagePathMapper());
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
	 * @title: getFirstLayerNodeList
	 * @description: 根据websiteid查找第一层子节点详细信息
	 * @param websiteid
	 * @return
	 */
	public List<WebsiteNode> getFirstLayerNodeList(int websiteid){
		List<WebsiteNode> nodeList = new ArrayList<WebsiteNode>();
		
		Integer rootid = getNodeId(websiteid, "root");
		if (rootid == null) {
			return nodeList;
		}else {
			List<Integer> childidList = getNodeChildid(rootid);
			for (int i = 0; i < childidList.size(); i++) {
				WebsiteNode node = getWebsiteNode(childidList.get(i));
				if (node != null) {
					nodeList.add(node);
				}
			}
			return nodeList;
		}
	}
	
	/**
	 * @title: getWebsiteNode
	 * @description: 根据nodeid查找单个节点详细信息
	 * @param nodeid
	 * @return
	 */
	public WebsiteNode getWebsiteNode(int nodeid){
		String SQL = "SELECT nodeid, websiteid, nodeName, nodePic, childrenType"
				+ " FROM website_node WHERE nodeid = ?";
		WebsiteNode node = null;
		
		try {
			node = jdbcTemplate.queryForObject(SQL, new Object[]{nodeid}, new WebsiteNodeMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return node;
	}
	
	/**
	 * @title: getWebsiteNodeList
	 * @description: 根据微官网id读取节点信息nodeid, nodeName, nodePic, childrenType 以及
	 * 节点关联信息 fatherid, articleid, classid
	 * @param websiteid
	 * @return
	 */
	public List<WebsiteNode> getWebsiteNodeList(int websiteid){
		String SQL = "SELECT nodeid, websiteid, nodeName, nodePic, childrenType"
				+ " FROM website_node WHERE websiteid = ?";
		List<WebsiteNode> nodeList = null;
		
		try {
			nodeList = jdbcTemplate.query(SQL, new Object[]{websiteid}, new WebsiteNodeMapper());
		} catch (Exception e) {
			nodeList = new ArrayList<WebsiteNode>();
			System.out.println(e.getMessage());
		}
		
		for (int i = 0; i < nodeList.size(); i++) {
			WebsiteNode node = nodeList.get(i);
			List<Integer> childidList = getNodeChildid(node.getNodeid());
			String childrenType = node.getChildrenType();
			if (childrenType.equals("node")) {
				setFather(nodeList, childidList, node.getNodeid());
			}else {
				if (childrenType.equals("article")) {
					if (childidList.size() == 1) {
						node.setArticleid(childidList.get(0));
					}else {
						System.out.println("error for article");
					}
					
				}else {
					if (childrenType.equals("articleclass")) {
						if (childidList.size() == 1) {
							node.setClassid(childidList.get(0));
						}else {
							System.out.println("error for articleclass");
						}
						
					}else {
						//叶子节点，且未挂任何文章或文章类资源
					}
				}
			}
		}
		
		return nodeList;
	}
	
	private static final class WebsiteNodeMapper implements RowMapper<WebsiteNode>{
		@Override
		public WebsiteNode mapRow(ResultSet rs, int arg1) throws SQLException {
			WebsiteNode node = new WebsiteNode();
			node.setNodeid(rs.getInt("nodeid"));
			node.setWebsiteid(rs.getInt("websiteid"));
			node.setNodeName(rs.getString("nodeName"));
			node.setNodePic(rs.getString("nodePic"));
			node.setChildrenType(rs.getString("childrenType"));
			return node;
		}	
	}
	
	/**
	 * @title: getNodeChildid
	 * @description: 根据节点id获取子节点id列表 
	 * @param nodeid
	 * @return
	 */
	public List<Integer> getNodeChildid(int nodeid){
		String SQL = "SELECT childid FROM website_nodetree WHERE nodeid = ?";
		List<Integer> childidList = null;
		
		try {
			childidList = jdbcTemplate.query(SQL, new Object[]{nodeid}, new ChildidMapper());
		} catch (Exception e) {
			childidList = new ArrayList<Integer>();
			System.out.println(e.getMessage());
		}
		return childidList;
	}
	
	private static final class ChildidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer childid = rs.getInt("childid");
			return childid;
		}	
	}
	
	/**
	 * @title: setFather
	 * @description: 遍历查找子节点，并设置子节点的fatherid属性
	 * @param nodeList  所有节点集合
	 * @param childidList  子节点集合
	 * @param nodeid   父节点id
	 */
	private void setFather(List<WebsiteNode> nodeList, List<Integer> childidList, int nodeid){
		for (int i = 0; i < childidList.size(); i++) {
			int childid = childidList.get(i);
			for (int j = 0; j < nodeList.size(); j++) {
				if (nodeList.get(j).getNodeid() == childid) {
					nodeList.get(j).setFatherid(nodeid);
					break;
				}
			}
		}
	}
	
	/**
	 * @title: getNodeId
	 * @description: 根据websiteid和nodeName查找节点id
	 * @param websiteid
	 * @param nodeName
	 * @return
	 */
	public Integer getNodeId(int websiteid, String nodeName){
		String SQL = "SELECT nodeid FROM website_node WHERE websiteid = ? AND nodeName = ?";
		Integer nodeid = null;
		
		try {
			nodeid = jdbcTemplate.queryForObject(SQL, new Object[]{websiteid, nodeName}, new NodeidMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return nodeid;
	}
	
	private static final class NodeidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer nodeid = rs.getInt("nodeid");
			return nodeid;
		}
	}
	
	/**
	 * @title: getWebsiteBasicMedia
	 * @description: 获取微官网基本的图片、视频资源信息
	 * @param websiteid
	 * @return
	 */
	public Website getWebsiteBasicMedia(int websiteid){
		String SQL = "SELECT coverPic FROM website WHERE websiteid = ?";
		Website website = null;
		
		try {
			website = jdbcTemplate.queryForObject(SQL, new Object[]{websiteid}, new WebsiteBasicMediaMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return website;
	}
	
	private static final class WebsiteBasicMediaMapper implements RowMapper<Website>{
		@Override
		public Website mapRow(ResultSet rs, int arg1) throws SQLException {
			Website website = new Website();
			website.setCoverPic(rs.getString("coverPic"));
			return website;
		}	
	}
	
	/**
	 * @title: getWebsiteNodeinfo
	 * @description: 获取节点关联外表信息 (nodeid, nodePic)
	 * @param websiteid
	 * @return
	 */
	public List<WebsiteNode> getWebsiteNodeinfo(int websiteid){
		String SQL = "SELECT nodeid, nodePic FROM website_node WHERE websiteid = ?";
		List<WebsiteNode> nodeList = null;
		
		try {
			nodeList = jdbcTemplate.query(SQL, new Object[]{websiteid}, new NodeinfoMapper());
		} catch (Exception e) {
			nodeList = new ArrayList<WebsiteNode>();
			System.out.println(e.getMessage());
		}
		return nodeList;
	}
	
	private static final class NodeinfoMapper implements RowMapper<WebsiteNode>{
		@Override
		public WebsiteNode mapRow(ResultSet rs, int arg1) throws SQLException {
			WebsiteNode node = new WebsiteNode();
			node.setNodeid(rs.getInt("nodeid"));
			node.setNodePic(rs.getString("nodePic"));
			return node;
		}
	}
	
	/**
	 * @title: getThemeidList
	 * @description: 获取微官网主题id列表
	 * @return
	 */
	public List<Integer> getThemeidList(){
		String SQL = "SELECT themeid FROM website_theme";
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
			Integer themeid = rs.getInt("themeid");
			return themeid;
		}	
	}
	
	/**
	 * @title: getWebsiteNum
	 * @description: 根据appid查询已关联微官网的数量
	 * @param appid
	 * @return
	 */
	public int getWebsiteNum(String appid){
		String SQL = "SELECT COUNT(*) FROM website WHERE appid = ?";
		int count = 1;
		
		try {
			count = jdbcTemplate.queryForObject(SQL, Integer.class, appid);
		} catch (Exception e) {
			System.out.println(e.getMessage());		
	    }
		return count;
	}
	
	/**
	 * @title: getWebsiteidByAppid
	 * @description: 根据appid获取websiteid
	 * @param appid
	 * @return
	 */
	public Integer getWebsiteidByAppid(String appid){
		String SQL = "SELECT websiteid FROM website WHERE appid = ?";
		Integer websiteid = null;
		
		try {
			websiteid = jdbcTemplate.queryForObject(SQL, new Object[]{appid}, new WebsiteidMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return websiteid;
	}
	
	private static final class WebsiteidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer websiteid = rs.getInt("websiteid");
			return websiteid;
		}	
	}
}
