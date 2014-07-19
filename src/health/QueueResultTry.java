package health;
/**
 * @author byc
 * Desc: For API-3.1
 */
public class QueueResultTry {
	private boolean BandingState;
	private String TJ_Code;
	private String Name;
	private QueueResult PD_Info;

	public boolean getBandingState() {
		return BandingState;
	}

	public void setBandingState(boolean bandingState) {
		BandingState = bandingState;
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
