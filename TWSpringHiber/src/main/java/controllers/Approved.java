package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import dbModels.AccountInfo;
import dbModels.LogInClass;

/**
 * @author Maximka
 * This controller return approved page when login and password is right. If user try to go on 
 * this page directly, he will be forward to login page. Only GET method is used. 
 *
 */
@Controller
@SessionAttributes({"currentLogin","currentPassword"})
public class Approved {
	@Autowired
	private AccountInfo accoutData;
	@Autowired
	private LogInClass logIn;
	
	@RequestMapping(value="/approved", method=RequestMethod.GET)
	public String approvedPage(@ModelAttribute("currentLogin") String login,
			@ModelAttribute("currentPassword") String password, Model model){
		if ( logIn.checkLoginPassword(login, password)) {
			model.addAttribute("info", accoutData.getAccountInfo(login));
			return "Approved";
		}
		return "/login";
	}
	@ModelAttribute("currentLogin")
	public String log(){
		return "";
	}
	@ModelAttribute("currentPassword")
	public String pass(){
		return "";
	}
	
}
