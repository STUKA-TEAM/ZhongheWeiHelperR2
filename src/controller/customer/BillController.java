package controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Title: BillController
 * @Description: 账单详情查看管理
 * @Company: ZhongHe
 * @author ben
 * @date 2014年2月23日
 */
@Controller
public class BillController {
	/**
	 * @description: 查询账单详情
	 * @param model
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/elove/bill", method = RequestMethod.GET)
	public String getEloveDetail(Model model, @RequestParam(value = "key", required = true) String key){
		
		return "";
	}
}
