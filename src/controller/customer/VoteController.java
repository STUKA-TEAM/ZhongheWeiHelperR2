package controller.customer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import register.dao.AppInfoDAO;
import tools.CommonValidationTools;
import vote.Vote;
import vote.VoteContent;
import vote.dao.VoteDAO;
import website.ShareMessage;
import website.Website;
import website.dao.WebsiteDAO;

/**
 * @Title: VoteController
 * @Description: 投票管理
 * @Company: Tuka
 * @author ben
 * @date 2014年3月26日
 */
@Controller
public class VoteController {
	/**
	 * @title getVote
	 * @description 根据voteid获取投票信息
	 * @param model
	 * @param voteid
	 * @return
	 */
	@RequestMapping(value = "/vote", method = RequestMethod.GET)
	public String getVote(Model model, @RequestParam(value = "voteid", required = true) int voteid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		VoteDAO voteDao = (VoteDAO) context.getBean("VoteDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Vote vote = voteDao.getVoteForCustomer(voteid);
		String viewName = "VoteViews/";
		
		if (vote != null) {
			Integer websiteid = voteDao.getWebsiteidByVoteid(voteid);
			if (websiteid != null) {
				Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);		
		        model.addAttribute("website", website);
			}
			
			ShareMessage message = new ShareMessage();
			message.setWechatNumber(appInfoDao.getWechatNumberByVote(voteid));
			message.setAppLink(message.getAppLink() + "customer/vote?voteid=" + voteid);
			if (vote.getCoverPic() != null && !vote.getCoverPic().equals("")) {
				message.setImageLink(message.getImageLink() + vote.getCoverPic() + "_original.jpg");
			} else {
				message.setImageLink("");
			}
			message.setShareTitle(vote.getVoteName());
			message.setShareContent(vote.getVoteDesc());
			model.addAttribute("message", message);
			
			model.addAttribute("vote", vote);
			viewName = viewName + "vote";	
		} else {
			viewName = viewName + "exception";
		}
		return viewName;
	}
	
	/**
	 * @title getVoteResult
	 * @description 插入投票信息并返回投票结果
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/voteresult", method = RequestMethod.POST)
	public String getVoteResult(@RequestBody String json, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		VoteDAO voteDao = (VoteDAO) context.getBean("VoteDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		VoteContent content = gson.fromJson(json, VoteContent.class);
		
		if (CommonValidationTools.checkVoteContent(content)) {
			voteDao.insertVoteContent(content);
		}
		
		Vote vote = voteDao.getVoteResult(content.getVoteid());
		String viewName = "VoteViews/";
		
		if (vote != null) {
			Integer websiteid = voteDao.getWebsiteidByVoteid(content.getVoteid());
			if (websiteid != null) {
				Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);		
		        model.addAttribute("website", website);
			}
			
			ShareMessage message = new ShareMessage();
			message.setWechatNumber(appInfoDao.getWechatNumberByVote(content.getVoteid()));
			message.setAppLink(message.getAppLink() + "customer/vote?voteid=" + content.getVoteid());
			if (vote.getCoverPic() != null && !vote.getCoverPic().equals("")) {
				message.setImageLink(message.getImageLink() + vote.getCoverPic() + "_original.jpg");
			} else {
				message.setImageLink("");
			}
			message.setShareTitle(vote.getVoteName());
			message.setShareContent(vote.getVoteDesc());
			model.addAttribute("message", message);
			
			model.addAttribute("vote", vote);
			viewName = viewName + "voteresult";	
		} else {
			viewName = viewName + "exception";
		}
		return viewName;
	}
}
