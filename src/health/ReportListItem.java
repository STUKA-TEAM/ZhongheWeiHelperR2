package health;
/**
 * @author byc
 * Desc: For API-2.1
 */
public class ReportListItem {
	private String ArchivesNum;
	private String CustomerRegID;
	private String Name;
	private String CheckDate;
	private String ItemSuitIDName;
	private String AddTime;

	public String getArchivesNum() {
		return ArchivesNum;
	}

	public void setArchivesNum(String archivesNum) {
		ArchivesNum = archivesNum;
	}

	public String getCustomerRegID() {
		return CustomerRegID;
	}

	public void setCustomerRegID(String customerRegID) {
		CustomerRegID = customerRegID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCheckDate() {
		return CheckDate;
	}

	public void setCheckDate(String checkDate) {
		CheckDate = checkDate;
	}

	public String getItemSuitIDName() {
		return ItemSuitIDName;
	}

	public void setItemSuitIDName(String itemSuitIDName) {
		ItemSuitIDName = itemSuitIDName;
	}

	public String getAddTime() {
		return AddTime;
	}

	public void setAddTime(String addTime) {
		AddTime = addTime;
	}
}
