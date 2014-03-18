package menu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import menu.Menu;
import menu.MenuNode;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.sun.corba.se.impl.orbutil.graph.Node;

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
	
	//insert
	/**
	 * @title insertMenu
	 * @description 插入自定义菜单信息
	 * @param menu
	 * @return
	 */
	public int insertMenu(Menu menu){
		String appid = menu.getRealAppid();
		List<MenuNode> nodeList = menu.getNodeList();
		
		for (int i = 0; i < nodeList.size(); i++) {
			MenuNode node = nodeList.get(i);
			int nodeid = insertMenuNode(appid, node);
			if (nodeid > 0) {
				node.setNodeid(nodeid);
			} else {
				return 0;
			}
		}
		
		int result = insertMenuTree(nodeList);
		return result;
	}
	
	/**
	 * @title insertMenuTree
	 * @description 插入自定义菜单结构关系
	 * @param nodeList
	 * @return
	 */
	public int insertMenuTree(List<MenuNode> nodeList){
		for (int i = 0; i < nodeList.size(); i++) {
			MenuNode node = nodeList.get(i);
			if (node.getNodeType() == 2) {
				boolean insert = false;
				for (int j = 0; j < nodeList.size(); j++) {
					MenuNode node2 = nodeList.get(j);
					if (node2.getNodeType() == 1 && node.getFatherUUID().equals(node2.getUUID())) {
						int result = insertNodeRelation(node2.getNodeid(), node.getNodeid());
						if (result > 0) {
							insert = true;
						} else {
							return 0;
						}
					}
				}
				if (insert == false) {
					return 0;
				}
			}
		}
		return 1;
	}
	
	/**
	 * @title insertNodeRelation
	 * @description 插入节点之间关系
	 * @param nodeid
	 * @param childid
	 * @return
	 */
	public int insertNodeRelation(int nodeid, int childid){
		String SQL = "INSERT INTO menu_nodetree (id, nodeid, childid) VALUES (default, ?, ?)";
		int result = jdbcTemplate.update(SQL, nodeid, childid);
		return result <= 0 ? 0 : result;
	}
	
	/**
	 * @title insertMenuNode
	 * @description 插入单个节点信息
	 * @param appid
	 * @param node
	 * @return
	 */
	public int insertMenuNode(final String appid, final MenuNode node){
		final String SQL = "INSERT INTO menu_node (nodeid, appid, nodeName, nodeLink, nodeType) VALUES (default, ?, ?, ?, ?)";
		int result = 0;
		
		KeyHolder kHolder = new GeneratedKeyHolder();
		result = jdbcTemplate.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		        PreparedStatement ps =
		            connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, appid);
		        ps.setString(2, node.getNodeName());
		        ps.setString(3, node.getNodeLink());
		        ps.setInt(4, node.getNodeType());
		        return ps;
		    }
		}, kHolder);
		
		return result <= 0 ? 0 : kHolder.getKey().intValue();
	}
	
	//delete
	/**
	 * @title deleteNodeRelation
	 * @description 根据节点id删除节点关系
	 * @param nodeid
	 * @return
	 */
	public int deleteNodeRelation(int nodeid){
		String SQL = "DELETE FROM menu_nodetree WHERE nodeid = ?";
		int result = jdbcTemplate.update(SQL, nodeid);
		return result <= 0 ? 0 : result;
	}
	
	
	/**
	 * @title deleteMenu
	 * @description 根据appid删除菜单节点信息及节点关系信息
	 * @param appid
	 * @return
	 */
	public int deleteMenu(String appid){
		List<MenuNode> nodeidList = getNodeInfoList(appid);
		for (int i = 0; i < nodeidList.size(); i++) {
			MenuNode node = nodeidList.get(i);
			if (node.getNodeType() == 1) {
				deleteNodeRelation(node.getNodeid());
			}
		}
		
		String SQL = "DELETE FROM menu_node WHERE appid = ?";
		int result = jdbcTemplate.update(SQL, appid);
		return result <= 0 ? 0 : result;
	}
	
	//update
	/**
	 * @title updateMenu
	 * @description 更新自定义菜单信息
	 * @param menu
	 * @return
	 */
	public int updateMenu(Menu menu){
		String appid = menu.getRealAppid();
		deleteMenu(appid);
		int result = insertMenu(menu);
		return result;
	}
	
	//query
	/**
	 * @title getNodeInfoList
	 * @description 根据appid查询自定义菜单节点信息列表(nodeid, nodeType)
	 * @param appid
	 * @return
	 */
	public List<MenuNode> getNodeInfoList(String appid){
		List<MenuNode> nodeInfoList = null;
		String SQL = "SELECT nodeid, nodeType FROM menu_node WHERE appid = ?";	
		try {
			nodeInfoList = jdbcTemplate.query(SQL, new Object[]{appid}, new NodeInfoMapper());
		} catch (Exception e) {
			System.out.println("getNodeInfoList: " + e.getMessage());
			nodeInfoList = new ArrayList<MenuNode>();
		}
		return nodeInfoList;
	}
	
	private static final class NodeInfoMapper implements RowMapper<MenuNode>{
		@Override
		public MenuNode mapRow(ResultSet rs, int arg1) throws SQLException {
			MenuNode node = new MenuNode();
			node.setNodeid(rs.getInt("nodeid"));
			node.setNodeType(rs.getInt("nodeType"));
			return node;
		}
	}
	
	/**
	 * @title getMenuNodeList
	 * @description 
	 * @param appid
	 * @return
	 */
	public List<MenuNode> getMenuNodeList(String appid){
		List<MenuNode> nodeList = null;
		String SQL = "SELECT nodeid, nodeName, nodeLink, nodeType FROM menu_node WHERE appid = ?";
		try {
			nodeList = jdbcTemplate.query(SQL, new Object[]{appid}, new FullNodeInfoMapper());
		} catch (Exception e) {
			System.out.println("getMenuNodeList: " + e.getMessage());
			nodeList = new ArrayList<MenuNode>();
		}
		
		for (int i = 0; i < nodeList.size(); i++) {
			MenuNode node = nodeList.get(i);
			if (node.getNodeType() == 1) {
				List<Integer> childidList = getChildidList(node.getNodeid());
				setFather(nodeList, childidList, node.getNodeid());
			}
		}
		return nodeList;
	}
	
	private static final class FullNodeInfoMapper implements RowMapper<MenuNode>{
		@Override
		public MenuNode mapRow(ResultSet rs, int arg1) throws SQLException {
			MenuNode node = new MenuNode();
			node.setNodeid(rs.getInt("nodeid"));
			node.setNodeType(rs.getInt("nodeName"));
			node.setNodeName(rs.getString("nodeLink"));
			node.setNodeLink(rs.getString("nodeType"));
			return node;
		}
	}
	
	/**
	 * @title getChildidList
	 * @description 根据节点id查询子节点id列表
	 * @param nodeid
	 * @return
	 */
	public List<Integer> getChildidList(int nodeid){
		List<Integer> childidList = null;
		String SQL = "SELECT childid FROM menu_nodetree WHERE nodeid = ?";
		try {
			childidList = jdbcTemplate.query(SQL, new Object[]{nodeid}, new ChildidMapper());
		} catch (Exception e) {
			System.out.println("getChildidList: " + e.getMessage());
			childidList = new ArrayList<Integer>();
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
	 * @title setFather
	 * @description 为二级子菜单节点添加父节点id信息
	 * @param nodeList
	 * @param childidList
	 * @param nodeid
	 */
	private void setFather(List<MenuNode> nodeList, List<Integer> childidList, int nodeid){
		for (int i = 0; i < nodeList.size(); i++) {
			MenuNode node = nodeList.get(i);
			if (node.getNodeType() == 2 && childidList.contains(node.getNodeid())) {
				node.setFatherid(nodeid);
			}
		}
	}
}
