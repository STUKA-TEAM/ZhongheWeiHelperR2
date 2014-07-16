package health;
/**
 * @author byc
 * Desc: For API-1.3
 */
public class QueueResultBanding {
private boolean BandingResult;
private String TJ_Code;
private String Name;
private QueueResult PD_Info;
public boolean isBandingResult() {
	return BandingResult;
}
public void setBandingResult(boolean bandingResult) {
	BandingResult = bandingResult;
}
public String getTJ_Code() {
	return TJ_Code;
}
public void setTJ_Code(String tJ_Code) {
	TJ_Code = tJ_Code;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public QueueResult getPD_Info() {
	return PD_Info;
}
public void setPD_Info(QueueResult pD_Info) {
	PD_Info = pD_Info;
}
}
