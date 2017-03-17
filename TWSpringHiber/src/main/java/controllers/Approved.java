package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import dbModels.UserAccount;

/**
 * @author Maximka
 * This controller return approved page when login and password is right. If user try to go on 
 * this page directly, he will be forward to login page. Only GET method is used. 
 *
 */
@Controller
@SessionAttributes({"currentUser"})
public class Approved {
	
	/*@Autowired
	private LogInClass logIn;*/
	
	@RequestMapping(value="/approved", method=RequestMethod.GET)
	public String approvedPage(
			@ModelAttribute("currentUser") UserAccount user,
			Model model){
			model.addAttribute("info", user.getAccountOperations());
					/*accoutData.getAccountInfo(user.getId())*/
			return "Approved";
		
	
	}
	
	@ModelAttribute("currentUser")
	public UserAccount pass(){
		UserAccount account = new UserAccount();
		account.setLogin("");
		account.setPassword("");
		return account;
	}
	
}
