package controller.customer;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import register.dao.AppInfoDAO;
import website.ShareMessage;
import website.Website;
import website.dao.WebsiteDAO;
import album.Album;
import album.dao.AlbumDAO;

/**
 * @Title: AlbumController
 * @Description: 相册管理
 * @Company: Tuka
 * @author ben
 * @date 2014年3月26日
 */
@Controller
public class AlbumController {
	/**
	 * @title getAlbum
	 * @description 根据albumid获取相册信息
	 * @param model
	 * @param albumid
	 * @param websiteid
	 * @return
	 */
	@RequestMapping(value = "/album", method = RequestMethod.GET)
	public String getAlbum(Model model, @RequestParam(value = "albumid", required = true) int albumid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		AppInfoDAO appInfoDao = (AppInfoDAO) context.getBean("AppInfoDAO");
		((ConfigurableApplicationContext)context).close();
		
		Album album = albumDao.getAlbumForCustomer(albumid);
		String viewName = "WebsiteViews/";
		
		if (album != null) {
			Integer websiteid = albumDao.getWebsiteidByAlbumid(albumid);
			if (websiteid != null) {
				Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);		
		        model.addAttribute("website", website);
			}
			
			ShareMessage message = new ShareMessage();
			message.setWechatNumber(appInfoDao.getWechatNumberByAlbum(albumid));
			message.setAppLink(message.getAppLink() + "customer/album?albumid=" + albumid);
			if (album.getCoverPic() != null && !album.getCoverPic().equals("")) {
				message.setImageLink(message.getImageLink() + album.getCoverPic() + "_original.jpg");
			} else {
				message.setImageLink("");
			}
			message.setShareTitle(album.getAlbumName());
			message.setShareContent("");
			model.addAttribute("message", message);
			
			model.addAttribute("album", album);
			viewName = viewName + "album";	
		} else {
			viewName = viewName + "exception";
		}
		return viewName;
	}
	
	/**
	 * @title getAlbumClass
	 * @description 根据classid获取相册集下相册信息
	 * @param model
	 * @param classid
	 * @param websiteid
	 * @return
	 */
	@RequestMapping(value = "/albumclass", method = RequestMethod.GET)
	public String getAlbumClass(Model model, @RequestParam(value = "classid", required = true) int classid){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("All-Modules.xml");
		WebsiteDAO websiteDao = (WebsiteDAO) context.getBean("WebsiteDAO");
		AlbumDAO albumDao = (AlbumDAO) context.getBean("AlbumDAO");
		((ConfigurableApplicationContext)context).close();
		
		List<Album> albumList = albumDao.getAlbumClassForCustomer(classid);
		model.addAttribute("albumList", albumList);
		
		Integer websiteid = albumDao.getWebsiteidByClassid(classid);
		if (websiteid != null) {
			Website website = websiteDao.getWebsiteInfoForCustomer(websiteid);	
	        model.addAttribute("website", website);
		}
		return "WebsiteViews/albumclass";
	}
}
