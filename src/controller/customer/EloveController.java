package controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EloveController {
@RequestMapping(value = "/elove/story", method = RequestMethod.GET)
public String story(){
	return "EloveViews/story";
}
@RequestMapping(value = "/elove/wedding", method = RequestMethod.GET)
public String wedding(){
	return "EloveViews/wedding";
}
@RequestMapping(value = "/elove/dress", method = RequestMethod.GET)
public String dress(){
	return "EloveViews/dress";
}
@RequestMapping(value = "/elove/record", method = RequestMethod.GET)
public String record(){
	return "EloveViews/record";
}
@RequestMapping(value = "/elove/wishmessage", method = RequestMethod.GET)
public String wishMessage(){
	return "EloveViews/wishMessage";
}
@RequestMapping(value = "/elove/joinmessage", method = RequestMethod.GET)
public String joinMessage(){
	return "EloveViews/joinMessage";
}
}
