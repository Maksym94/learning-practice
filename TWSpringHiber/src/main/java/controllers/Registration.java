package controllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import dbModels.LogInClass;
/**
 * @author Maximka
 * This controller is used for registration new accounts. First return registration page. Then 
 * after all filled pages will redirect to successfullyregistered page and then to login page
 *  if everything right, if not forward to wrongregistration page and then to the same 
 *  registration page. Uses GET and POST.
 *
 */
@Controller
@SessionAttributes({"picturenumber"})
public class Registration {

	@Autowired
	private LogInClass dataBaseOfUsers;
	private final Random randomgenerator;

	public Registration() {
		randomgenerator = new Random();

	}
    
	@RequestMapping(value={"/register", "/registration"}, method=RequestMethod.GET)
	public String register(Model model){
		model.addAttribute( "picturenumber", randomgenerator.nextInt(6) + 1);
		return "registration";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registration(@ModelAttribute("login") String login,
			@ModelAttribute("password") String password,
			@ModelAttribute("confirmpassword")String confirmPassword,
			@ModelAttribute("picturenumber")int pictureNumber,
			@ModelAttribute("permissioncode")String permissionCode) {
		if(dataBaseOfUsers.addNewUser(login, password, confirmPassword, pictureNumber,
				permissionCode)){
			return "successfullyregistered";
		}
		return "wrongregistration";
	}
}
