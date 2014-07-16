package health;

import java.util.List;
/**
 * @author byc
 * Desc: For API-2.2
 */
public class DepartmentReport {
private String DepartmentID;
private String DepartmentName;
private String CharacterSummary;
private String CheckDate;
private String EmployeeName;
private List<ResultItem> View2;
public String getDepartmentID() {
	return DepartmentID;
}
public void setDepartmentID(String departmentID) {
	DepartmentID = departmentID;
}
public String getDepartmentName() {
	return DepartmentName;
}
public void setDepartmentName(String departmentName) {
	DepartmentName = departmentName;
}
public String getCharacterSummary() {
	return CharacterSummary;
}
public void setCharacterSummary(String characterSummary) {
	CharacterSummary = characterSummary;
}
public String getCheckDate() {
	return CheckDate;
}
public void setCheckDate(String checkDate) {
	CheckDate = checkDate;
}
public String getEmployeeName() {
	return EmployeeName;
}
public void setEmployeeName(String employeeName) {
	EmployeeName = employeeName;
}
public List<ResultItem> getView2() {
	return View2;
}
public void setView2(List<ResultItem> view2) {
	View2 = view2;
}
}
