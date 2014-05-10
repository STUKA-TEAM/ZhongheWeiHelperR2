package controller.store;

import message.ResponseMessage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import weixintools.CreateWeiXinButtonMes;
import weixintools.WeixinMessageUtil;

import com.google.gson.Gson;

/**
 * @Title: WelcomeController
 * @Description: 响应有关需要访问微信的请求
 * @Company: Tuka
 * @author ben
 * @date 2014年3月18日
 */
@Controller
public class GetWeixinMesController {

	@RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
	@ResponseBody
	public String getAccessToken(@RequestParam(value="appid", required=true) String appid, 
			@RequestParam(value="secret", required=true) String secret, Model model){      
		String accessTokenString = WeixinMessageUtil.getAccessToken(appid, secret);
		ResponseMessage message = new ResponseMessage();

			if (accessTokenString != "") {
				message.setStatus(true);
				message.setMessage(accessTokenString);
			}else{
				message.setStatus(false);
				message.setMessage("向微信服务器请求数据失败，请检查AppId和AppSecret是否填写正确");
			}			
		Gson gson = new Gson();
		String response = gson.toJson(message);				
        return response;
	}
	@RequestMapping(value = "/createButton", method = RequestMethod.GET)
	@ResponseBody
	public String createButton(@RequestParam(value="accesstoken", required=true) String accesstoken, 
			@RequestParam(value="jsonData", required=true) String jsonData, Model model){      
		CreateWeiXinButtonMes createWeiXinButtonMes = WeixinMessageUtil.createButton(accesstoken, jsonData);
		if(createWeiXinButtonMes.getErrcode()==0){
			return "y";
		}else {
			System.out.println("CODE:"+createWeiXinButtonMes.getErrcode()+"MES:"+createWeiXinButtonMes.getErrmsg());
			return "n";
		}
	}
}
