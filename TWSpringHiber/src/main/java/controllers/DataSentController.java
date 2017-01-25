package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import dbModels.CreateCaptcha;
import dbModels.LastUser;
import dbModels.LogInClass;

/**
 * @author Maximka
 * This controller return login page with the form to input login and password. Then login and 
 * password are checked in POST method and if everything right, save parameters login and 
 * password and redirect to approved page, if not, forward to the same login page. Uses 
 * POST and GET.
 *
 */
@Controller
@SessionAttributes({"isLogIn","currentLogin","currentPassword","someBean"})
public class DataSentController {
	@Autowired
	private LogInClass logClass;
	@Autowired
	private CreateCaptcha createCaptcha;
	
	@InitBinder
	public void init(){
		createCaptcha.createData();
	}
	
	@RequestMapping(value={"/login", "/index"}, 
			method=RequestMethod.GET)
	public String logInStart(@RequestParam(value="action",required=false) String action,	
			@ModelAttribute("currentLogin") String login,
			@ModelAttribute("currentPassword") String password,
			Model model, SessionStatus status){
		if(action!=null&&action.equals("logout")){
			status.setComplete();
			return "forward:/";
		}
		
		if (login != null && password != null
				&& action != null
				&&action.equals("approved")){
			return "forward:/approved";
		}
		if (login != null && password != null
				&&action != null
				&& action.equals("putmoney")){
			return "forward:/put";
		}
		if (login != null && password != null
				&&action != null
				&& action.equals("getmoney")){
			return "forward:/get";
		}
		if (login != null && password != null
				&& logClass.checkLoginPassword(login, password)){
			model.addAttribute("isLogIn", true);
			System.out.println("Get " +login+" "+password);
			return "index";
		}
		model.addAttribute("totalUsers", logClass.getTotalUsers());
		return "login";
	}
	
	@RequestMapping(value={"/index"},method=RequestMethod.POST)
	public String logIn(@ModelAttribute("someBean") LastUser lastUser,
			@ModelAttribute("login") String login,
			@ModelAttribute("password") String password, Model model){
		if(lastUser==null){
			return "forward:/";
		}
		lastUser.addLoginPass(login,"");
		if (logClass.checkLoginPassword(login,password)){
			model.addAttribute("isLogIn", true);
			model.addAttribute("currentLogin",login);
			model.addAttribute("currentPassword",password);
		}
		else {
			model.addAttribute("Wrong", "Wrong login or password");
			model.addAttribute("totalUsers", logClass.getTotalUsers());
			
			return "/login";
		}
		return "redirect:/";
	}
	@ModelAttribute("currentLogin")
	public String currentLogin(){
		return "";
	}
	@ModelAttribute("currentPassword")
	public String currentPassword(){
		return "";
	}
	@ModelAttribute("someBean")
	public LastUser someBean(){
		return new LastUser();
	}
}
