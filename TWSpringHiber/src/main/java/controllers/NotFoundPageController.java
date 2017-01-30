package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lib.NotFoundException;



/**
 * @author Maximka
 *This controller is used when no path is binded to another controllers and also controller used
 *for path "/"
 */
@Controller
public class NotFoundPageController {
	
	@ExceptionHandler(NotFoundException.class)
	@RequestMapping(method=RequestMethod.GET )
	public String pageNotFound(){
		return "redirect:/login";
	}

}
