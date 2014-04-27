package register;

/**
 * @Title: RoleInfo
 * @Description: 角色信息
 * @Company: Tuka
 * @author ben
 * @date 2014年4月25日
 */
public class RoleInfo {
	private int roleid;
	private String roleName;
	private String roleLabel;
	
	/**
	 * @return the roleid
	 */
	public int getRoleid() {
		return roleid;
	}
	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the roleLabel
	 */
	public String getRoleLabel() {
		return roleLabel;
	}
	/**
	 * @param roleLabel the roleLabel to set
	 */
	public void setRoleLabel(String roleLabel) {
		this.roleLabel = roleLabel;
	}
}
