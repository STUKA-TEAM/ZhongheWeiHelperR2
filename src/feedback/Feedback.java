package feedback;

import java.sql.Timestamp;


public class Feedback {
private int id;
private int sid;
private String message;
private Timestamp createTime;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getSid() {
	return sid;
}
public void setSid(int sid) {
	this.sid = sid;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Timestamp getCreateTime() {
	return createTime;
}
public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}

}
