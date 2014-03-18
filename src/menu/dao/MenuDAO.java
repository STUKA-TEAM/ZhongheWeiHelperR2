package menu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import menu.Menu;
import menu.MenuNode;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
		List<Integer> nodeidList = 
		return 0;
	}
	
	//query
	/**
	 * @title getNodeidList
	 * @description 根据appid查询自定义菜单节点id列表
	 * @param appid
	 * @return
	 */
	public List<Integer> getNodeidList(String appid){
		List<Integer> nodeidList = null;
		String SQL = "SELECT nodeid FROM menu_node WHERE appid = ?";	
		try {
			nodeidList = jdbcTemplate.query(SQL, new Object[]{appid}, new NodeidMapper());
		} catch (Exception e) {
			System.out.println("getNodeidList: " + e.getMessage());
			nodeidList = new ArrayList<Integer>();
		}
		return nodeidList;
	}
	
	private static final class NodeidMapper implements RowMapper<Integer>{
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			Integer nodeid = rs.getInt("nodeid");
			return nodeid;
		}
	}
	
	public List<MenuNode> getMenuNodeList(String appid){
		List<MenuNode> nodeList = null;
		return nodeList;
	}
}
