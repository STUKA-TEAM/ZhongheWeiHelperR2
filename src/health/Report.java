package health;

import java.util.List;
/**
 * @author byc
 * Desc: For API-2.2
 */
public class Report {
private String ArchivesNum;
private String Name;
private String Sex;
private String Age;
private String CheckDate;
private String CharacterSummary;
private String Advice;
private List<DepartmentReport> View1;
public String getArchivesNum() {
	return ArchivesNum;
}
public void setArchivesNum(String archivesNum) {
	ArchivesNum = archivesNum;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public String getSex() {
	return Sex;
}
public void setSex(String sex) {
	Sex = sex;
}
public String getAge() {
	return Age;
}
public void setAge(String age) {
	Age = age;
}
public String getCheckDate() {
	return CheckDate;
}
public void setCheckDate(String checkDate) {
	CheckDate = checkDate;
}
public String getCharacterSummary() {
	return CharacterSummary;
}
public void setCharacterSummary(String characterSummary) {
	CharacterSummary = characterSummary;
}
public String getAdvice() {
	return Advice;
}
public void setAdvice(String advice) {
	Advice = advice;
}
public List<DepartmentReport> getView1() {
	return View1;
}
public void setView1(List<DepartmentReport> view1) {
	View1 = view1;
}
}
