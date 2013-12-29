package controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EloveController {
@RequestMapping(value = "/elove/elove", method = RequestMethod.GET)
public String eloveIndex(){
	return "EloveViews/elove";
}	
@RequestMapping(value = "/elove/story", method = RequestMethod.GET)
public String story(){
	return "EloveViews/story";
}
@RequestMapping(value = "/elove/info", method = RequestMethod.GET)
public String wedding(){
	return "EloveViews/info";
}
@RequestMapping(value = "/elove/dress", method = RequestMethod.GET)
public String dress(){
	return "EloveViews/dress";
}
@RequestMapping(value = "/elove/record", method = RequestMethod.GET)
public String record(){
	return "EloveViews/record";
}
@RequestMapping(value = "/elove/intro", method = RequestMethod.GET)
public String intro(){
	return "EloveViews/intro";
}
@RequestMapping(value = "/elove/wish", method = RequestMethod.GET)
public String wishMessage(){
	return "EloveViews/wish";
}
@RequestMapping(value = "/elove/join", method = RequestMethod.GET)
public String joinMessage(){
	return "EloveViews/join";
}
}
