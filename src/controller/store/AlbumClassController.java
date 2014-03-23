package controller.store;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import message.ResponseMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import register.dao.AppInfoDAO;
import security.User;
import tools.CommonValidationTools;
import website.ViewLinkInfo;
import website.dao.WebsiteDAO;
import album.Album;
import album.AlbumClass;
import album.dao.AlbumDAO;

/**
 * @Title: AlbumClassController
 * @Description: 相册集管理
 * @Company: Tuka
 * @author ben
 * @date 2014年3月19日
 */
@Controller
public class AlbumClassController {
	/**
	 * @title getAlbumClassList
	 * @description 获取相册集信息列表
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/albumclass/list", method = RequestMethod.GET)
	public String getAlbumClassList(@CookieValue(value = "appid", required = false) String appid, 
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		if (appid == null) {       //异常
			return "redirect:/store/account";     
		}
		else {
			if (appid.equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			}
			else {	
				List<AlbumClass> classList = albumDao.getDetailedClassinfos(appid);
				model.addAttribute("classList", classList);
				
				Integer websiteid = websiteDao.getWebsiteidByAppid(appid);
				InputStream inputStream = AlbumClassController.class.getResourceAsStream("/environment.properties");
				Properties properties = new Properties();
				String appPath = null;
				try {
					properties.load(inputStream);
					appPath = (String)properties.get("applicationPath");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				ViewLinkInfo viewLinkInfo = new ViewLinkInfo();
				viewLinkInfo.setWebsiteid(websiteid);
				viewLinkInfo.setAppPath(appPath);
				model.addAttribute("viewLinkInfo", viewLinkInfo);
				return "AlbumViews/albumclassList";
			}
		}
	}
	
	/**
	 * @title addAlbumClass
	 * @description 创建新的相册集(第一步)
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/albumclass/add", method = RequestMethod.GET)
	public String addAlbumClass(@CookieValue(value = "appid", required = false) String appid, 
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		if (appid == null) {       //异常
			return "redirect:/store/account";     
		}
		else {
			if (appid.equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			} 
			else {
				List<Album> albumList = albumDao.getBasicAlbuminfos(appid);
				model.addAttribute("albumList", albumList);
				return "AlbumViews/addAlbumclassDialog";
			}
		}	
	}
	
	/**
	 * @title editAlbumClass
	 * @description 编辑已有的相册集(第一步)
	 * @param appid
	 * @param classid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/albumclass/edit", method = RequestMethod.GET)
	public String editAlbumClass(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "classid", required = true) Integer classid, Model model,
			HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		
		if (appid == null) {       //异常
			return "redirect:/store/account";     
		}
		else {
			if (appid.equals("") || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				request.setAttribute("message", "当前管理的公众账号无效，请先选择或关联微信公众账号!");
				request.setAttribute("jumpLink", "store/account");
				return "forward:/store/transfer";   
			} 
			else {
				List<Integer> selectedList = null;
				
				if (classid != null && classid > 0) {
					AlbumClass albumClass = albumDao.getClassContent(classid);
					if (albumClass != null) {
						selectedList = albumClass.getAlbumidList();
						model.addAttribute("albumclass", albumClass);
					}
				}
				
				List<Album> albumList = albumDao.getBasicAlbuminfos(appid);
				if (selectedList != null) {
					for (int i = 0; i < albumList.size(); i++) {
						Album album = albumList.get(i);
						if (selectedList.contains(album.getAlbumid())) {
							album.setSelected(true);
						}
					}
				}
				model.addAttribute("albumList", albumList);
				return "AlbumViews/editAlbumclassDialog";
			}
		}
	}
	
	/**
	 * @title createAlbumClass
	 * @description  创建新的相册集(第二步)
	 * @param appid
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/albumclass/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createAlbumClass(@CookieValue(value = "appid", required = false) String appid, 
		@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
	    
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
			
		if (appid == null) {                      //异常
			message.setStatus(false);
			message.setMessage("请重新登录！");
		}
		else {
			if (appid == "" || appInfoDao.checkAppExistsByUser(user.getSid(), 
					appid) == 0) {                                                          //需先创建app或appid无效
				message.setStatus(false);
				message.setMessage("当前管理的公众账号无效，请先选择或关联微信公众账号!");
			}
			else {
				AlbumClass albumClass = gson.fromJson(json, AlbumClass.class);
				Timestamp current = new Timestamp(System.currentTimeMillis());			
				albumClass.setAppid(appid);
				albumClass.setCreateTime(current);
				
				if (!CommonValidationTools.checkAlbumClass(albumClass)) {
					message.setStatus(false);
					message.setMessage("相册集信息不完整或有误！");
				} else {
					int result = albumDao.insertAlbumClass(albumClass);
					if (result > 0) {
						message.setStatus(true);
						message.setMessage("相册集创建成功！");
					} else {
						message.setStatus(false);
						message.setMessage("相册集创建失败！");
						System.out.println("Error: " + result);
					}
				}	
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateAlbumClass
	 * @description 编辑已有的相册集(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/albumclass/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateAlbumClass(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		AlbumClass albumClass = gson.fromJson(json, AlbumClass.class);
		
		if (!CommonValidationTools.checkAlbumClass(albumClass)) {
			message.setStatus(false);
			message.setMessage("相册集信息不完整或有误！");
		} else {
			int result = albumDao.updateAlbumClass(albumClass);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("相册集修改成功！");
			} else {
				message.setStatus(false);
				message.setMessage("相册集修改失败！");
				System.out.println("Error: " + result);
			}
		}	
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteAlbumClass
	 * @description 删除相册集
	 * @param classid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/albumclass/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAlbumClass(@RequestParam(value = "classid", required = true) 
	    int classid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = albumDao.deleteAlbumClass(classid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("删除成功！");
		}else {
			message.setStatus(false);
			message.setMessage("删除失败！");
		}
		
		String response = gson.toJson(message);
		return response;
	}
}
