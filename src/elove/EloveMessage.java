package elove;

import java.sql.Timestamp;

public class EloveMessage {
private int id;
private int eloveid;
private Timestamp createTime;
private String name;
private String content;
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
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Timestamp getCreateTime() {
	return createTime;
}
public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}
}