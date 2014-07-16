package health;

import java.util.List;
/**
 * @author byc
 * Desc: For API-1.3 API-3.1
 */
public class QueueResult {
private String WX_NowInfo;
private List<String> WX_NeedDepartment;
private List<String> WX_KFDepartment;
public String getWX_NowInfo() {
	return WX_NowInfo;
}
public void setWX_NowInfo(String wX_NowInfo) {
	WX_NowInfo = wX_NowInfo;
}
public List<String> getWX_NeedDepartment() {
	return WX_NeedDepartment;
}
public void setWX_NeedDepartment(List<String> wX_NeedDepartment) {
	WX_NeedDepartment = wX_NeedDepartment;
}
public List<String> getWX_KFDepartment() {
	return WX_KFDepartment;
}
public void setWX_KFDepartment(List<String> wX_KFDepartment) {
	WX_KFDepartment = wX_KFDepartment;
}
}
