package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import article.Article;
import article.ArticleClass;

/**
 * @Title: ArticleDAO
 * @Description: DAO for article and articleclass model
 * @Company: ZhongHe
 * @author ben
 * @date 2014年1月22日
 */
public class ArticleDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//insert
	/**
	 * @title: insertArticle
	 * @description: 插入文章记录以及文章所属类别记录
	 * @param article
	 * @return
	 */
	public int insertArticle(final Article article){
		int result = 0;
		final String SQL = "INSERT INTO article (articleid, appid, title, coverPic, "
				+ "createTime, content) VALUES (default, ?, ?, ?, ?, ?)";
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, article.getAppid());
		        ps.setString(2, article.getTitle());
		        ps.setString(3, article.getCoverPic());
		        ps.setTimestamp(4, article.getCreateTime());
		        ps.setString(5, article.getContent());
		        return ps;
		    }
		}, kHolder);
		
		if (result > 0) {
			String coverPic = article.getCoverPic();
			if (coverPic != null && !coverPic.equals("")) {
				deleteImageTempRecord(coverPic);
			}
			
			Document doc = Jsoup.parse(article.getContent());
			Elements imgs = doc.select("img[src]");
			for (int i = 0; i < imgs.size(); i++) {
				String imagePath = imgs.get(i).attr("src");
				if (!imagePath.startsWith("/resources")) {
					continue;
				}
				int index = imagePath.indexOf('_');
				deleteImageTempRecord(imagePath.substring(0, index));
			}
			
			int articleid = kHolder.getKey().intValue();
			result = insertACR(articleid, article.getClassidList());
			if (result == 0) {
				return -1;
			}
			return result;
		}else {
			return 0;
		}
	}
	
	/**
	 * @title: insertArticleClass
	 * @description: 插入文章类别记录以及所关联文章
	 * @param articleClass
	 * @return
	 */
	public int insertArticleClass(final ArticleClass articleClass){
		int result = 0;
		final String SQL = "INSERT INTO articleclass (classid, appid, className, createTime) VALUES (default, ?, ?, ?)";
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, articleClass.getAppid());
		        ps.setString(2, articleClass.getClassName());
		        ps.setTimestamp(3, articleClass.getCreateTime());
		        return ps;
		    }
		}, kHolder);
		
		if (result > 0) {
			int classid = kHolder.getKey().intValue();
			
			result = insertCAR(classid, articleClass.getArticleidList());
			if (result == 0) {
				return -1;
			}
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * @title: insertACR
	 * @description: 插入文章Article和文章类别ArticleClass的关系Relationship
	 * @param articleid
	 * @param classidList
	 * @return
	 */
	private int insertACR(int articleid, List<Integer> classidList){
		String SQL = "INSERT INTO article_articleclass (id, articleid, classid) "
				+ "VALUES (default, ?, ?)";
		int result = 1;
		
		if (classidList != null) {
			for (int i = 0; i < classidList.size(); i++) {
				int classid = classidList.get(i);
				result = jdbcTemplate.update(SQL, articleid, classid);
				if (result <= 0) {
					return 0;
				}
			}
			return result;
		}else {
			return 1;
		}
	}
	
	/**
	 * @title: insertCAR
	 * @description: 插入文章Article和文章类别ArticleClass的关系Relationship
	 * @param classid
	 * @param articleidList
	 * @return
	 */
	private int insertCAR(int classid, List<Integer> articleidList){
		String SQL = "INSERT INTO article_articleclass (id, articleid, classid) "
				+ "VALUES (default, ?, ?)";
		int result = 1;
		
		if (articleidList != null) {
			for (int i = 0; i < articleidList.size(); i++) {
				int articleid = articleidList.get(i);
				result = jdbcTemplate.update(SQL, articleid, classid);
				if (result <= 0) {
					return 0;
				}
			}
			return result;
		} else{
			return 1;
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
	 * @title: deleteArticle
	 * @description: 删除文章记录以及文章所属类别记录
	 * @param articleid
	 * @return
	 */
	public int deleteArticle(int articleid){
		String SQL = "DELETE FROM article WHERE articleid = ?";
		int result = 0;
		
		Article temp = getArticleBasicMedia(articleid);
		if (temp != null) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			
			String coverPic = temp.getCoverPic();
			if (coverPic != null && !coverPic.equals("")) {
				insertImageTempRecord(coverPic, current);
			}
			
			String content = temp.getContent();
			if (!content.equals("")) {
				Document doc = Jsoup.parse(content);
				Elements imgs = doc.select("img[src]");
				for (int i = 0; i < imgs.size(); i++) {
					String imagePath = imgs.get(i).attr("src");
					if (!imagePath.startsWith("/resources")) {
						continue;
					}
					int index = imagePath.indexOf('_');
					insertImageTempRecord(imagePath.substring(0, index), current);
				}
			}			
		} else {
			return 0;
		}
		
		deleteACRByArticleid(articleid);
		
		result = jdbcTemplate.update(SQL, articleid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: deleteArticleClass
	 * @description: 删除文章类别以及所关联的文章记录
	 * @param classid
	 * @return
	 */
	public int deleteArticleClass(int classid){
		String SQL = "DELETE FROM articleclass WHERE classid = ?";
		int result = jdbcTemplate.update(SQL, classid);
		if (result > 0) {
			deleteACRByClassid(classid);
		}
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: deleteACRByArticleid
	 * @description: 根据文章id删除文章Article和文章类别ArticleClass之间的对应关系Relationship
	 * @param articleid
	 * @return
	 */
	public int deleteACRByArticleid(int articleid){
		String SQL = "DELETE FROM article_articleclass WHERE articleid = ?";
		int result = jdbcTemplate.update(SQL, articleid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title: deleteACRByClassid
	 * @description: 根据文章类别id删除文章Article和文章类别ArticleClass之间的对应关系Relationship
	 * @param classid
	 * @return
	 */
	public int deleteACRByClassid(int classid){
		String SQL = "DELETE FROM article_articleclass WHERE classid = ?";
		int result = jdbcTemplate.update(SQL, classid);
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
	 * @title: updateArticle
	 * @description: 更新文章信息以及文章所属类别信息
	 * @param article
	 * @return
	 */
	public int updateArticle(Article article){
		String SQL = "UPDATE article SET title = ?, coverPic = ?, content = ? WHERE articleid = ?";
		
		Article temp = getArticleBasicMedia(article.getArticleid());
		if (temp == null) {
			return 0;
		}

		int result = jdbcTemplate.update(SQL, article.getTitle(), article.getCoverPic(), 
				article.getContent(), article.getArticleid());
		
		if (result > 0) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			
			String originalCoverPic = temp.getCoverPic();
			String currentCoverPic = article.getCoverPic();
			if (currentCoverPic == null) {
				currentCoverPic = "";
			}
			if (!currentCoverPic.equals(originalCoverPic)) {
				if (originalCoverPic != null && !originalCoverPic.equals("")) {
					insertImageTempRecord(originalCoverPic, current);
				}
				
				if (!currentCoverPic.equals("")) {
					deleteImageTempRecord(currentCoverPic);
				}
			}
			
			List<String> originalImages = parseEditorContent(temp.getContent());
			List<String> currentImages = parseEditorContent(article.getContent());
			for (int i = 0; i < originalImages.size(); i++) {
				String imagePath = originalImages.get(i);
				if (!currentImages.contains(imagePath)) {
					insertImageTempRecord(imagePath, current);
				}
			}
			for (int i = 0; i < currentImages.size(); i++) {
				String imagePath = currentImages.get(i);
				if (!originalImages.contains(imagePath)) {
					deleteImageTempRecord(imagePath);
				}
			}
			
			deleteACRByArticleid(article.getArticleid());
			result = insertACR(article.getArticleid(), article.getClassidList());
			if (result == 0) {
				return -1;
			}
			return result;
		}else {
			return 0;
		}
	}
	
	/**
	 * @title: parseEditorContent
	 * @description: 解析编辑器内容获取图片信息列表
	 * @param content
	 * @return
	 */
	private List<String> parseEditorContent(String content){
		List<String> imageList = new ArrayList<String>();
		
		if (!content.equals("")) {
			Document doc = Jsoup.parse(content);
			Elements imgs = doc.select("img[src]");
			for (int i = 0; i < imgs.size(); i++) {
				String imagePath = imgs.get(i).attr("src");
				if (!imagePath.startsWith("/resources")) {
					continue;
				}
				int index = imagePath.indexOf('_');
				imageList.add(imagePath.substring(0, index));
			}
		}
		return imageList;
	}
	
	/**
	 * @title: updateArticleClass
	 * @description: 更新文章类别信息
	 * @param articleClass
	 * @return
	 */
	public int updateArticleClass(ArticleClass articleClass){
		String SQL = "UPDATE articleclass SET className = ? WHERE classid = ?";		
		int result = jdbcTemplate.update(SQL, articleClass.getClassName(), articleClass.getClassid());
		
		if (result > 0) {		
			deleteACRByClassid(articleClass.getClassid());
			result = insertCAR(articleClass.getClassid(), articleClass.getArticleidList());
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
	 * @title: getBasicClassinfos
	 * @description: 根据appid获取文章类型的基本信息列表
	 * @return
	 */
	public List<ArticleClass> getBasicClassinfos(String appid){
		String SQL = "SELECT classid, className FROM articleclass WHERE appid = ? "
				+ "ORDER BY classid ASC";
		List<ArticleClass> classList = null;
		
		try {
			classList = jdbcTemplate.query(SQL, new Object[]{appid}, new BasicClassinfoMapper());
		} catch (Exception e) {
			classList = new ArrayList<ArticleClass>();
			System.out.println(e.getMessage());
		}
		
		return classList;
	}
	
	/**
	 * @title: getClassContent
	 * @description: 根据类别id获取类别信息
	 * @param classid
	 * @return
	 */
	public ArticleClass getClassContent(int classid){
		String SQL = "SELECT classid, className FROM articleclass WHERE classid = ?";
		ArticleClass articleClass = null;
		
		try {
			articleClass = jdbcTemplate.queryForObject(SQL, new Object[]{classid}, new BasicClassinfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (articleClass != null) {
			articleClass.setArticleidList(getArticleidList(classid));
		}
		return articleClass;
	}
	
	private static final class BasicClassinfoMapper implements RowMapper<ArticleClass>{
		@Override
		public ArticleClass mapRow(ResultSet rs, int arg1) throws SQLException {
			ArticleClass articleClass = new ArticleClass();
			articleClass.setClassid(rs.getInt("classid"));
			articleClass.setClassName(rs.getString("className"));
			return articleClass;
		}	
	}
	
	/**
	 * @title: getBasicArticleinfos
	 * @description: 根据appid获取文章的基本信息列表 (只有文章id和文章标题)
	 * @param appid
	 * @return
	 */
	public List<Article> getBasicArticleinfos(String appid){
		String SQL = "SELECT articleid, title FROM article WHERE appid = ?";
		List<Article> articleList = null;
		
		try {
			articleList = jdbcTemplate.query(SQL, new Object[]{appid}, new ShortArticleinfoMapper());
		} catch (Exception e) {
			articleList = new ArrayList<Article>();
			System.out.println(e.getMessage());
		}
		return articleList;
	}
	
	private static final class ShortArticleinfoMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int arg1) throws SQLException {
			Article article = new Article();
			article.setArticleid(rs.getInt("articleid"));
			article.setTitle(rs.getString("title"));
			return article;
		}	
	}	
	
	/**
	 * @title: getArticleContent
	 * @description: 根据文章id获取文章内容以及所属类别信息
	 * @param articleid
	 * @return
	 */
	public Article getArticleContent(int articleid){
		String SQL = "SELECT articleid, title, coverPic, content FROM article WHERE articleid = ?";
		Article article = null;
		
		try {
			article = jdbcTemplate.queryForObject(SQL, new Object[]{articleid}, new ArticleContentMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (article != null) {
			article.setClassidList(getClassidList(articleid));
		}
		return article;
	}
	
	private static final class ArticleContentMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int arg1) throws SQLException {
			Article article = new Article();
			article.setArticleid(rs.getInt("articleid"));
			article.setTitle(rs.getString("title"));
			article.setCoverPic(rs.getString("coverPic"));
			article.setContent(rs.getString("content"));
			return article;
		}	
	}
	
	/**
	 * @title: getArticleForCustomer
	 * @description: 根据articleid获取手机端单篇文章所需信息
	 * @param articleid
	 * @return
	 */
	public Article getArticleForCustomer(int articleid){
		String SQL = "SELECT articleid, title, createTime, content FROM article WHERE articleid = ?";
		Article article = null;
		
		try {
			article = jdbcTemplate.queryForObject(SQL, new Object[]{articleid}, new CustomerArticleMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return article;
	}
	
	private static final class CustomerArticleMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int arg1) throws SQLException {
			Article article = new Article();
			article.setArticleid(rs.getInt("articleid"));
			article.setTitle(rs.getString("title"));
			article.setCreateTime(rs.getTimestamp("createTime"));
			article.setContent(rs.getString("content"));
			return article;
		}	
	}
	
	/**
	 * @title: getArticleIntroinfo
	 * @description: 根据articleid获取手机端文章列表展示时单篇文章信息
	 * @param articleid
	 * @return
	 */
	public Article getArticleIntroinfo(int articleid){
		String SQL = "SELECT articleid, title, coverPic, createTime FROM article WHERE articleid = ?";
		Article article = null;
		
		try {
			article = jdbcTemplate.queryForObject(SQL, new Object[]{articleid}, new ArticleIntroinfoMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return article;
	}
	
	private static final class ArticleIntroinfoMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int arg1) throws SQLException {
			Article article = new Article();
			article.setArticleid(rs.getInt("articleid"));
			article.setTitle(rs.getString("title"));
			article.setCreateTime(rs.getTimestamp("createTime"));
			article.setCoverPic(rs.getString("coverPic"));
			return article;
		}	
	}
	
	/**
	 * @title: getArticleidList
	 * @description: 由类别id获取所包含文章id列表
	 * @param classid
	 * @return
	 */
	public List<Integer> getArticleidList(int classid){
		String SQL = "SELECT articleid FROM article_articleclass WHERE classid = ?";
		List<Integer> articleidList = null;
		
		try {
			articleidList = jdbcTemplate.query(SQL, new Object[]{classid}, new ArticleidMapper());
		} catch (Exception e) {
			articleidList = new ArrayList<Integer>();
			System.out.println(e.getMessage());
		}
		return articleidList;
	}
	
	/**
	 * @title: getArticleidList
	 * @description:  由appid获取所关联文章id列表
	 * @param appid
	 * @return
	 */
	public List<Integer> getArticleidList(String appid){
		String SQL = "SELECT articleid FROM article WHERE appid = ?";
        List<Integer> articleidList = null;
		
		try {
			articleidList = jdbcTemplate.query(SQL, new Object[]{appid}, new ArticleidMapper());
		} catch (Exception e) {
			articleidList = new ArrayList<Integer>();
			System.out.println(e.getMessage());
		}
		return articleidList;
	}
	
	private static final class ArticleidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer articleid = rs.getInt("articleid");
			return articleid;
		}		
	}
	
	/**
	 * @title: getClassidList
	 * @description: 由文章id获取所属类别id列表
	 * @param articleid
	 * @return
	 */
	public List<Integer> getClassidList(int articleid){
		String SQL = "SELECT classid FROM article_articleclass WHERE articleid = ?";
		List<Integer> classidList = null;
		
		try {
			classidList = jdbcTemplate.query(SQL, new Object[]{articleid}, new ClassidMapper());
		} catch (Exception e) {
			classidList = new ArrayList<Integer>();
			System.out.println(e.getMessage());
		}
		return classidList;
	}
	
	/**
	 * @title: getClassidList
	 * @description: 由appid获取所属类别id列表
	 * @param appid
	 * @return
	 */
	public List<Integer> getClassidList(String appid){
		String SQL = "SELECT classid FROM articleclass WHERE appid = ?";
		List<Integer> classidList = null;
		
		try {
			classidList = jdbcTemplate.query(SQL, new Object[]{appid}, new ClassidMapper());
		} catch (Exception e) {
			classidList = new ArrayList<Integer>();
			System.out.println(e.getMessage());
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
	 * @title: getArticleBasicMedia
	 * @description: 获取文章的所有图片信息来源
	 * @param articleid
	 * @return
	 */
	public Article getArticleBasicMedia(int articleid){
		String SQL = "SELECT coverPic, content FROM article WHERE articleid = ?";
		Article article = null;
		
		try {
			article = jdbcTemplate.queryForObject(SQL, new Object[]{articleid}, new ArticleBasicMediaMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return article;
	}
	
	private static final class ArticleBasicMediaMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int arg1) throws SQLException {
			Article article = new Article();
			article.setCoverPic(rs.getString("coverPic"));
			article.setContent(rs.getString("content"));
			return article;
		}	
	}
	
	/**
	 * @title: getArticleinfos
	 * @description: 根据appid获取文章信息列表(包括文章id, 标题, 封面图片, 创建时间)
	 * @param appid
	 * @return
	 */
	public List<Article> getArticleinfos(String appid){
		String SQL = "SELECT A.articleid, A.title, A.coverPic, A.createTime FROM "
				+ "article A WHERE A.appid = ? ORDER BY A.createTime DESC";
		List<Article> articleList = null;
		
		try {
			articleList = jdbcTemplate.query(SQL, new Object[]{appid}, new BasicArticleinfoMapper());
		} catch (Exception e) {
			articleList = new ArrayList<Article>();
			System.out.println(e.getMessage());
		}
		return articleList;
	}
	
	/**
	 * @title: getArticleinfosWithType
	 * @description: 根据文章类型获取文章信息列表
	 * @param classid
	 * @return
	 */
	public List<Article> getArticleinfosWithType(int classid){
		String SQL = "SELECT A.articleid, A.title, A.coverPic, A.createTime FROM "
				+ "article A, article_articleclass B WHERE A.articleid = B.articleid "
				+ "AND B.classid = ? ORDER BY A.createTime DESC";
        List<Article> articleList = null;
		
		try {
			articleList = jdbcTemplate.query(SQL, new Object[]{classid}, new BasicArticleinfoMapper());
		} catch (Exception e) {
			articleList = new ArrayList<Article>();
			System.out.println(e.getMessage());
		}
		return articleList;		
	}
	
	private static final class BasicArticleinfoMapper implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int arg1) throws SQLException {
			Article article = new Article();
			article.setArticleid(rs.getInt("A.articleid"));
			article.setTitle(rs.getString("A.title"));
			article.setCoverPic(rs.getString("A.coverPic"));
			article.setCreateTime(rs.getTimestamp("A.createTime"));
			return article;
		}		
	}
	
	/**
	 * @title: getDetailedClassinfos
	 * @description: 获取比较详细的文章类别信息列表 (文章类别id, 类别名称, 创建时间)
	 * @param appid
	 * @return
	 */
	public List<ArticleClass> getDetailedClassinfos(String appid){
		String SQL = "SELECT classid, className, createTime FROM articleclass "
				+ "WHERE appid = ? ORDER BY createTime DESC";
		List<ArticleClass> classList = null;
		
		try {
			classList = jdbcTemplate.query(SQL, new Object[]{appid}, new DetailedClassinfoMapper());
		} catch (Exception e) {
			classList = new ArrayList<ArticleClass>();
			System.out.println(e.getMessage());
		}
		
		for (int i = 0; i < classList.size(); i++) {
			int count = getArticleCount(classList.get(i).getClassid());
			classList.get(i).setArticleCount(count);
		}
		return classList;
	}
	
	private static final class DetailedClassinfoMapper implements RowMapper<ArticleClass>{
		@Override
		public ArticleClass mapRow(ResultSet rs, int arg1)
				throws SQLException {
			ArticleClass articleClass = new ArticleClass();
			articleClass.setClassid(rs.getInt("classid"));
			articleClass.setClassName(rs.getString("className"));
			articleClass.setCreateTime(rs.getTimestamp("createTime"));
			return articleClass;
		}		
	}
	
	/**
	 * @title: getArticleCount
	 * @description: 统计指定文章类别下的文章数量
	 * @param classid
	 * @return
	 */
	public int getArticleCount(int classid){
		String SQL = "SELECT COUNT(*) FROM article_articleclass WHERE classid = ?";
		int count = 0;		
		try {
			count = jdbcTemplate.queryForObject(SQL, new Object[]{classid}, Integer.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}
}
