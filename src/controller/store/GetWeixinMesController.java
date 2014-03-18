package controller.store;

import message.ResponseMessage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import weixintools.WeixinMessageUtil;

import com.google.gson.Gson;

/**
 * @Title: WelcomeController
 * @Description: 控制欢迎页相关的信息配置
 * @Company: Tuka
 * @author ben
 * @date 2014年3月18日
 */
@Controller
public class GetWeixinMesController {
	/**
	 * @description: 插入欢迎页配置信息
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
	@ResponseBody
	public String insertWelcome(@RequestParam(value="appid", required=true) String appid, 
			@RequestParam(value="secret", required=true) String secret, Model model){      
		String accessTokenString = WeixinMessageUtil.getAccessToken(appid, secret);
		ResponseMessage message = new ResponseMessage();

			if (accessTokenString != "") {
				message.setStatus(true);
				message.setMessage(accessTokenString);
			}else{
				message.setStatus(false);
				message.setMessage("向微信服务器请求数据失败，请检查网络或稍后再试");
			}			
		Gson gson = new Gson();
		String response = gson.toJson(message);				
        return response;
	}
	
}
