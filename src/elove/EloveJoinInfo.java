package elove;

import java.sql.Timestamp;


public class EloveJoinInfo {
private int id;
private int eloveid;
private Timestamp createTime;
private String name;
private String phone;
private int number;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getEloveid() {
	return eloveid;
}
public void setEloveid(int eloveid) {
	this.eloveid = eloveid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public int getNumber() {
	return number;
}
public void setNumber(int number) {
	this.number = number;
}
public Timestamp getCreateTime() {
	return createTime;
}
public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}
}
