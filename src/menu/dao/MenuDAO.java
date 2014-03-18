package menu.dao;

import java.util.List;

import javax.sql.DataSource;

import menu.Menu;
import menu.MenuNode;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Title: MenuDAO
 * @Description: DAO for menu model
 * @Company: ZhongHe
 * @author ben
 * @date 2014年3月18日
 */
public class MenuDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//update
	public int updateMenu(Menu menu){
		
		return 0;
	}
	
	//get
	public List<MenuNode> getMenuNodeList(String appid){
		List<MenuNode> nodeList = null;
		return nodeList;
	}
}
