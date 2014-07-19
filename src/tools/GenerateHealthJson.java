package tools;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import health.BandingResult;
import health.BandingState;
import health.DepartmentReport;
import health.QueueResult;
import health.QueueResultBanding;
import health.QueueResultTry;
import health.Report;
import health.ReportListItem;
import health.ResultItem;

public class GenerateHealthJson {
public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
public static String getAPI_1_1_false(){
	BandingResult bandingResult = new BandingResult();
	bandingResult.setBandingResult(false);
	bandingResult.setArchivesNum(null);	
	return gson.toJson(bandingResult);
}
public static String getAPI_1_1_true(){
	BandingResult bandingResult = new BandingResult();
	bandingResult.setBandingResult(true);
	bandingResult.setArchivesNum("73082677");	
	return gson.toJson(bandingResult);
}

public static String getAPI_1_2_false(){
	BandingState bandingState = new BandingState();
	bandingState.setBandingState(false);
	bandingState.setArchivesNum(null);	
	return gson.toJson(bandingState);
}

public static String getAPI_1_2_true(){
	BandingState bandingState = new BandingState();
	bandingState.setBandingState(true);
	bandingState.setArchivesNum("73082677");	
	return gson.toJson(bandingState);
}

public static String getAPI_2_1(){
	ReportListItem reportListItem1 = new ReportListItem();
	reportListItem1.setArchivesNum("73082677");
	reportListItem1.setCustomerRegID("20000004026752");
	reportListItem1.setName("陈正英");
	reportListItem1.setCheckDate("2013-9-7");
	reportListItem1.setItemSuitIDName("自费2(女)");
	reportListItem1.setAddTime("2013-9-20");
	
	ReportListItem reportListItem2 = new ReportListItem();
	reportListItem2.setArchivesNum("73082688");
	reportListItem2.setCustomerRegID("20000004026700");
	reportListItem2.setName("刘力");
	reportListItem2.setCheckDate("2013-9-7");
	reportListItem2.setItemSuitIDName("自费2(男)");
	reportListItem2.setAddTime("2013-9-20");
	
	ArrayList<ReportListItem> reportListItems = new ArrayList<>();
	reportListItems.add(reportListItem1);
	reportListItems.add(reportListItem2);
	return gson.toJson(reportListItems);
}

public static String getAPI_2_2(){
	Report report = new Report();
	report.setArchivesNum("73082677");
	report.setName("陈正英");
	report.setSex("男");
	report.setAge("48");
	report.setCheckDate("2013-8-29");
	report.setCharacterSummary("* 内科: 未见异常 * 妇科: 宫颈:轻炎，后位子宫;阴道分泌物:脓性中等;TCT宫颈液基薄片:未见上皮内病变及恶性细胞（NILM）良性反应细胞改变 * 放射科: 心肺未见异常。 * 心电图: 正常 * 黑白B超: 肝 胆 胰 脾 双肾 子宫附件未见异常 * 检验科: 尿素氮(BUN)偏低(1.68↓mmol/L) 总胆固醇(CHOL)偏低(2.64↓mmol/L) α-岩藻糖苷酶:偏低(14.19↓U/L) 淋巴细胞(LYMph#)偏低(0.7↓10^9/L) 淋巴细胞(Lymph%)偏低(17.7↓%) 中性粒细胞百分比(Gran%)偏高(73.8↑%) 白细胞(白带):4-6 上皮细胞(白带):+ 乙肝表面抗体阳性 * 功能检查: 骨密度结果:骨质减少 收缩压正常 舒张压正常");
	report.setAdvice("* 尿素氮(BUN)偏低: 结合临床参考。 * 乙肝表面抗体阳性: 病毒已被清除，无传染性，且有免疫力；注射乙肝疫苗后已获得免疫力；也可见于假阳性。 * 骨质减少: 注意多做户外运动，增加日照。多食富含蛋白质、含钙及维生素的饮食：奶制品、豆制品、海产品及绿色蔬菜等。排除继发因素导致骨质减少，必要时专科就诊。");
	
	DepartmentReport departmentReport1 = new DepartmentReport();
	departmentReport1.setDepartmentID("1");
	departmentReport1.setDepartmentName("内科");
	departmentReport1.setCharacterSummary("未见异常");
	departmentReport1.setCheckDate("2013-8-29 8:18:00");
	departmentReport1.setEmployeeName("张王生");
	ResultItem resultItem1 = new ResultItem();
	resultItem1.setItemID("1");
	resultItem1.setItemName("胸部");
	resultItem1.setItemResult("正常");
	resultItem1.setValue("");
	resultItem1.setUnit("");
	ResultItem resultItem2 = new ResultItem();
	resultItem2.setItemID("2");
	resultItem2.setItemName("肺部");
	resultItem2.setItemResult("未见异常");
	resultItem2.setValue("");
	resultItem2.setUnit("");
	ArrayList<ResultItem> resultItems1 = new ArrayList<>();
	resultItems1.add(resultItem1);
	resultItems1.add(resultItem2);
	departmentReport1.setView2(resultItems1);

	DepartmentReport departmentReport2 = new DepartmentReport();
	departmentReport2.setDepartmentID("2");
	departmentReport2.setDepartmentName("检验科");
	departmentReport2.setCharacterSummary("尿素氮(BUN)偏低(1.68↓mmol/L)；总胆固醇(CHOL)偏低(2.64↓mmol/L)；α-岩藻糖苷酶:偏低(14.19↓U/L)；淋巴细胞(LYMph#)偏低(0.7↓10^9/L)；淋巴细胞(Lymph%)偏低(17.7↓%)；中性粒细胞百分比(Gran%)偏高(73.8↑%)；白细胞(白带):4-6；上皮细胞(白带):+；乙肝表面抗体阳性");
	departmentReport2.setCheckDate("2013-8-29 11:20:00");
	departmentReport2.setEmployeeName("");
	ResultItem resultItem3 = new ResultItem();
	resultItem3.setItemID("3");
	resultItem3.setItemName("平均红细胞血红蛋白含量(MCH)");
	resultItem3.setItemResult("28.3");
	resultItem3.setValue("27~34");
	resultItem3.setUnit("Pg");
	ResultItem resultItem4 = new ResultItem();
	resultItem4.setItemID("4");
	resultItem4.setItemName("血小板数目(PLT)");
	resultItem4.setItemResult("212");
	resultItem4.setValue("100~300");
	resultItem4.setUnit("10^9/L");
	ArrayList<ResultItem> resultItems2 = new ArrayList<>();
	resultItems2.add(resultItem3);
	resultItems2.add(resultItem4);
	departmentReport2.setView2(resultItems2);
	
	ArrayList<DepartmentReport> departmentReports = new ArrayList<>();
	departmentReports.add(departmentReport1);
	departmentReports.add(departmentReport2);
	report.setView1(departmentReports);
		
	return gson.toJson(report);
}

public static String getAPI_1_3_false(){
	QueueResultBanding queueResultBanding = new QueueResultBanding();
	queueResultBanding.setBandingResult(false);
	queueResultBanding.setTJ_Code(null);
	queueResultBanding.setName(null);
	queueResultBanding.setPD_Info(null);
	return gson.toJson(queueResultBanding);
}
public static String getAPI_1_3_true(){
	QueueResultBanding queueResultBanding = new QueueResultBanding();
	queueResultBanding.setBandingResult(true);
	queueResultBanding.setTJ_Code("73082677");
	queueResultBanding.setName("陈正英");
	QueueResult queueResult = new QueueResult();
	queueResult.setWX_NowInfo("内科");
	queueResult.setWX_NeedDepartment(Arrays.asList(new String[]{"外科","神经科","脑科"}));
	queueResult.setWX_KFDepartment(Arrays.asList(new String[]{"消化科","泌尿科"}));
	queueResultBanding.setPD_Info(queueResult);
	return gson.toJson(queueResultBanding);
}

public static String getAPI_3_1_false(){
	QueueResultTry queueResultTry = new QueueResultTry();
	queueResultTry.setBandingState(false);
	queueResultTry.setTJ_Code(null);
	queueResultTry.setName(null);
	queueResultTry.setPD_Info(null);
	return gson.toJson(queueResultTry);
}
public static String getAPI_3_1_true(){
	QueueResultTry queueResultTry = new QueueResultTry();
	queueResultTry.setBandingState(true);
	queueResultTry.setTJ_Code("73082677");
	queueResultTry.setName("陈正英");
	QueueResult queueResult = new QueueResult();
	queueResult.setWX_NowInfo("内科");
	queueResult.setWX_NeedDepartment(Arrays.asList(new String[]{"外科","神经科","脑科"}));
	queueResult.setWX_KFDepartment(Arrays.asList(new String[]{"消化科","泌尿科"}));
	queueResultTry.setPD_Info(queueResult);
	return gson.toJson(queueResultTry);
}
public static void main(String[] arg){
	/*System.out.println("API-1.1-false:"+"\n"+getAPI_1_1_false()+"\n");
	System.out.println("API-1.1-true:"+"\n"+getAPI_1_1_true()+"\n");*/
	/*System.out.println("API-1.2-false:"+"\n"+getAPI_1_2_false()+"\n");
	System.out.println("API-1.2-true:"+"\n"+getAPI_1_2_true()+"\n");
	System.out.println("API-2.1:"+"\n"+getAPI_2_1()+"\n");
	System.out.println("API-2.2:"+"\n"+getAPI_2_2()+"\n");
	
	System.out.println("API-1.3-false:"+"\n"+getAPI_1_3_false()+"\n");
	System.out.println("API-1.3-true:"+"\n"+getAPI_1_3_true()+"\n");
	System.out.println("API-3.1-false:"+"\n"+getAPI_3_1_false()+"\n");
	System.out.println("API-3.1-true:"+"\n"+getAPI_3_1_true()+"\n");*/
}
}
