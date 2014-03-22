package controller.store;

import java.sql.Timestamp;
import java.util.List;

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

import album.Album;
import album.AlbumClass;
import album.dao.AlbumDAO;
import register.dao.AppInfoDAO;
import security.User;
import tools.CommonValidationTools;

/**
 * @Title: AlbumController
 * @Description: 相册管理
 * @Company: Tuka
 * @author ben
 * @date 2014年3月19日
 */
@Controller
public class AlbumController {
	/**
	 * @title getAlbumList
	 * @description 根据相册集获取相册列表
	 * @param appid
	 * @param classid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/album/list", method = RequestMethod.GET)
	public String getAlbumList(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "classid", required = true) int classid, Model model, 
			HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
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
				List<Album> albumList = null;
				if (classid == 0) {
					albumList = albumDao.getDetailedAlbuminfos(appid);
				}else {
					if (classid > 0) {
						albumList = albumDao.getDetailedAlbuminfos(classid);
					}
				}
				model.addAttribute("albumList", albumList);
				
				List<AlbumClass> classList = albumDao.getBasicClassinfos(appid);
				AlbumClass allType = new AlbumClass();
				allType.setClassid(0);
				allType.setClassName("所有类别");
				classList.add(0, allType);
				for (int i = 0; i < classList.size(); i++) {
					if (classid == classList.get(i).getClassid()) {
						classList.get(i).setSelected(true);
						break;
					}
				}
				model.addAttribute("classList", classList);		
				return "AlbumViews/albumList";
			}
		}
	}
	
	/**
	 * @title addAlbum
	 * @description 创建新的相册(第一步)
	 * @param appid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/album/add", method = RequestMethod.GET)
	public String addAlbum(@CookieValue(value = "appid", required = false) String appid, 
			Model model, HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
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
				List<AlbumClass> classList = albumDao.getBasicClassinfos(appid);
				model.addAttribute("classList", classList);
			    return "AlbumViews/insertAlbum";
			}
		}
	}
	
	/**
	 * @title editAlbum
	 * @description 编辑已有的相册(第一步)
	 * @param appid
	 * @param albumid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/album/edit", method = RequestMethod.GET)
	public String editAlbum(@CookieValue(value = "appid", required = false) String appid, 
			@RequestParam(value = "albumid", required = true) Integer albumid, Model model, 
			HttpServletRequest request){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
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
				
				if (albumid != null && albumid > 0) {
					Album album = albumDao.getAlbumContent(albumid);
					if (album != null) {
						selectedList = album.getClassidList();
						model.addAttribute("album", album);
					}
				}
				
				List<AlbumClass> classList = albumDao.getBasicClassinfos(appid);
				if (selectedList != null) {
					for (int i = 0; i < classList.size(); i++) {
						AlbumClass albumClass = classList.get(i);
						if (selectedList.contains(albumClass.getClassid())) {
							albumClass.setSelected(true);
						}
					}
				}
				model.addAttribute("classList", classList);
			    return "AlbumViews/updateAlbum";
			}
		}
	}
	
	/**
	 * @title createAlbum
	 * @description 创建新的相册(第二步)
	 * @param appid
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/album/insert", method = RequestMethod.POST)
	@ResponseBody
	public String createAlbum(@CookieValue(value = "appid", required = false) String appid, 
		@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
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
				Album album = gson.fromJson(json, Album.class);
				Timestamp current = new Timestamp(System.currentTimeMillis());
				album.setAppid(appid);
				album.setCreateTime(current);
				
				if (!CommonValidationTools.checkAlbum(album)) {
					message.setStatus(false);
					message.setMessage("相册信息不完整或有误！");
				} else {
					int result = albumDao.insertAlbum(album);
					if (result > 0) {
						message.setStatus(true);
						message.setMessage("相册创建成功！");
					} else {
						message.setStatus(false);
						message.setMessage("相册创建失败！");
						System.out.println("Error: " + result);
					}
				}
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title updateAlbum
	 * @description 编辑已有的相册(第二步)
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/album/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateAlbum(@RequestBody String json, Model model) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		Album album = gson.fromJson(json, Album.class);
		
		if (!CommonValidationTools.checkAlbum(album)) {
			message.setStatus(false);
			message.setMessage("相册信息不完整或有误！");
		} else {
			int result = albumDao.updateAlbum(album);
			if (result > 0) {
				message.setStatus(true);
				message.setMessage("相册修改保存成功！");
			} else {
				message.setStatus(false);
				message.setMessage("相册修改保存失败！");
				System.out.println("Error: " + result);
			}
		}
		
		String response = gson.toJson(message);
		return response;
	}
	
	/**
	 * @title deleteAlbum
	 * @description 删除相册
	 * @param albumid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/album/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAlbum(@RequestParam(value="albumid", required = true) 
	    int albumid, Model model){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		((ConfigurableApplicationContext)context).close();
		
		Gson gson = new Gson();
		ResponseMessage message = new ResponseMessage();
		
		int result = albumDao.deleteAlbum(albumid);
		if (result > 0) {
			message.setStatus(true);
			message.setMessage("相册删除成功！");
		}else {
			message.setStatus(false);
			message.setMessage("相册删除失败！");
		}
		
		String response = gson.toJson(message);
		return response;
	}
}
