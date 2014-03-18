package message;

/**
 * @Title: ResponseMessage
 * @Description: 回复信息
 * @Company: Tuka
 * @author byc
 * @date 2014年3月18日
 */
public class ResponseMessage {
	private boolean status;
	private String message;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
