package controller.store;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.google.gson.Gson;

import elove.EloveWizard;
import elove.Step1Info;
import elove.Step2Info;
import elove.Step3Info;
import elove.Step4Info;
import elove.Step5Info;
import elove.Step6Info;
import elove.dao.EloveWizardDAO;

/**
 * @Title: EloveWizardController
 * @Description: 控制elove创建向导
 * @Company: ZhongHe
 * @author ben
 * @date 2013年12月28日
 */
@Controller
@RequestMapping("/elove/wizard")
@SessionAttributes("eloveWizard")
public class EloveWizardController {
	@RequestMapping(value = "/initial/create", method = RequestMethod.GET)
	public String initialCreate(final Model model){
		EloveWizard eloveWizard = new EloveWizard();
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/wizardContainer";
	}
	
	@RequestMapping(value = "/initial/edit", method = RequestMethod.GET)
	public String initialEdit(@RequestParam(value = "eloveid", required = true) int eloveid, 
			final Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
		((ConfigurableApplicationContext)context).close();
		
		EloveWizard eloveWizard = eloveWizardDao.getElove(eloveid);
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/wizardContainer";
	}
	
	@RequestMapping(value = "/backstep1", method = RequestMethod.GET)
	public String backstep1(final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, Model model){
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step1";
	}
	
	@RequestMapping(value = "/step2", method = RequestMethod.POST)
	public String step2(@RequestBody String json, final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, 
			Model model){
		Gson gson = new Gson();
		Step1Info step1Info = gson.fromJson(json, Step1Info.class);
		eloveWizard.setTitle(step1Info.getTitle());
		eloveWizard.setPassword(step1Info.getPassword());
		eloveWizard.setCoverPic(step1Info.getCoverPic());
		eloveWizard.setCoverText(step1Info.getCoverText());
		eloveWizard.setShareTitle(step1Info.getShareTitle());
		eloveWizard.setShareContent(step1Info.getShareContent());
		eloveWizard.setMajorGroupPhoto(step1Info.getMajorGroupPhoto());
		eloveWizard.setThemeid(step1Info.getThemeid());
		eloveWizard.setMusic(step1Info.getMusic());
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step2";
	}
	
	@RequestMapping(value = "/backstep2", method = RequestMethod.GET)
	public String backstep2(final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, Model model){
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step2";
	}
	
	@RequestMapping(value = "/step3", method = RequestMethod.POST)
	public String step3(@RequestBody String json, final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, 
			Model model){
		Gson gson = new Gson();
		Step2Info step2Info = gson.fromJson(json, Step2Info.class);
		eloveWizard.setXinLang(step2Info.getXinLang());
		eloveWizard.setXinNiang(step2Info.getXinNiang());
		eloveWizard.setStoryImagePath(step2Info.getStoryImagePath());
		eloveWizard.setStoryTextImagePath(step2Info.getStoryTextImagePath());
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step3";
	}
	
	@RequestMapping(value = "/backstep3", method = RequestMethod.GET)
	public String backstep3(final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, Model model){
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step3";
	}
	
	@RequestMapping(value = "/step4", method = RequestMethod.POST)
	public String step4(@RequestBody String json, final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, 
			Model model){
		Gson gson = new Gson();
		Step3Info step3Info = gson.fromJson(json, Step3Info.class);
        eloveWizard.setDressImagePath(step3Info.getDressImagePath());
        eloveWizard.setDressVideoPath(step3Info.getDressVideoPath());
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step4";
	}
	
	@RequestMapping(value = "/backstep4", method = RequestMethod.GET)
	public String backstep4(final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, Model model){
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step4";
	}
	
	@RequestMapping(value = "/step5", method = RequestMethod.POST)
	public String step5(@RequestBody String json, final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, 
			Model model){
		Gson gson = new Gson();
		Step4Info step4Info = gson.fromJson(json, Step4Info.class);
        eloveWizard.setWeddingDate(step4Info.getWeddingDate());
        eloveWizard.setWeddingAddress(step4Info.getWeddingAddress());
        eloveWizard.setLng(step4Info.getLng());
        eloveWizard.setLat(step4Info.getLat());
        eloveWizard.setPhone(step4Info.getPhone());
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step5";
	}
	
	@RequestMapping(value = "/backstep5", method = RequestMethod.GET)
	public String backstep5(final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, Model model){
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step5";
	}
	
	@RequestMapping(value = "/step6/create", method = RequestMethod.POST)
	public String step6create(@RequestBody String json, final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, 
			Model model){
		Gson gson = new Gson();
		Step5Info step5Info = gson.fromJson(json, Step5Info.class);
        eloveWizard.setRecordImagePath(step5Info.getRecordImagePath());
        eloveWizard.setRecordVideoPath(step5Info.getRecordVideoPath());
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step6create";
	}
	
	@RequestMapping(value = "/step6/update", method = RequestMethod.POST)
	public String step6update(@RequestBody String json, final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, 
			Model model){
		Gson gson = new Gson();
		Step5Info step5Info = gson.fromJson(json, Step5Info.class);
        eloveWizard.setRecordImagePath(step5Info.getRecordImagePath());
        eloveWizard.setRecordVideoPath(step5Info.getRecordVideoPath());
		model.addAttribute("eloveWizard", eloveWizard);
		return "EloveViews/step6update";
	}
	
	@RequestMapping(value = "/finish/create", method = RequestMethod.POST)
	public String finishCreate(@RequestBody String json, final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, 
			 @CookieValue(value = "appid", required = false) String appid, Model model, final SessionStatus status){
		if (appid == null) {
			return "redirect:/store/account";     //异常
		}
		else {
			if (appid == "") {
				return "forward:/store/account";   //需先创建app
			}
			else {
				ApplicationContext context = 
						new ClassPathXmlApplicationContext("All-Modules.xml");
				EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
				((ConfigurableApplicationContext)context).close();
				
				Gson gson = new Gson();
				Step6Info step6Info = gson.fromJson(json, Step6Info.class);
				eloveWizard.setFooterText(step6Info.getFooterText());
				eloveWizard.setSideCorpInfo(step6Info.getSideCorpInfo());
				eloveWizard.setAppid(appid);
				
				InputStream inputStream = EloveWizardController.class.getResourceAsStream("/defaultValue.properties");
				Properties properties = new Properties();
				long lifeCycle = 0;
				try {
					properties.load(inputStream);
					lifeCycle = Long.parseLong((String)properties.get("defaultEloveLifeCycleByMonth"));
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				lifeCycle = lifeCycle * 30 * 24 * 60 * 60 * 1000;
				Timestamp createTime = new Timestamp(System.currentTimeMillis());
				Timestamp expiredTime = new Timestamp(createTime.getTime() + lifeCycle);
				eloveWizard.setCreateTime(createTime);
				eloveWizard.setExpiredTime(expiredTime);
				
				int eloveid = eloveWizardDao.insertElove(eloveWizard);
				status.setComplete();
				if (eloveid > 0) {
					return "forward:/store/elove/detail";
				}else {
					return "";
				}				
			}
		}
	}
	
	@RequestMapping(value = "/finish/update", method = RequestMethod.POST)
	public String finishUpdate(@RequestBody String json, final @ModelAttribute("eloveWizard") EloveWizard eloveWizard, 
			 Model model, final SessionStatus status){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		EloveWizardDAO eloveWizardDao = (EloveWizardDAO) context.getBean("EloveWizardDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		Step6Info step6Info = gson.fromJson(json, Step6Info.class);
		eloveWizard.setFooterText(step6Info.getFooterText());
		eloveWizard.setSideCorpInfo(step6Info.getSideCorpInfo());
		
		//删除图片、视频资源
		
		int effected = eloveWizardDao.updateElove(eloveWizard);
		status.setComplete();
		if (effected > 0) {
			return "forward:/store/elove/detail";
		}else {
			return "";
		}	
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String cancel(final SessionStatus status){
		status.setComplete();
		return "forward:/store/elove/detail";
	}
}
