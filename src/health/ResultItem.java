package health;
/**
 * @author byc
 * Desc: For API-2.2
 */
public class ResultItem {
private String ItemID;
private String ItemName;
private String ItemResult;
private String Value;
private String Unit;
public String getItemID() {
	return ItemID;
}
public void setItemID(String itemID) {
	ItemID = itemID;
}
public String getItemName() {
	return ItemName;
}
public void setItemName(String itemName) {
	ItemName = itemName;
}
public String getItemResult() {
	return ItemResult;
}
public void setItemResult(String itemResult) {
	ItemResult = itemResult;
}
public String getValue() {
	return Value;
}
public void setValue(String value) {
	Value = value;
}
public String getUnit() {
	return Unit;
}
public void setUnit(String unit) {
	Unit = unit;
}
}
