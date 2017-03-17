package controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import dbModels.CreateTablesData;
import dbModels.LogInClass;
import dbModels.UserAccount;

/**
 * @author Maximka
 * This controller return login page with the form to input login and password. Then login and 
 * password are checked in POST method and if everything right, save parameters login and 
 * password and redirect to approved page, if not, forward to the same login page. Uses 
 * POST and GET.
 *
 */
@Controller
@SessionAttributes({"autenticatedLogin","currentUser"})
public class DataSentController {
	
	@Autowired
	private LogInClass logClass;
	
	@Autowired
	public DataSentController(CreateTablesData createCaptcha) {
		createCaptcha.createData();
	}
	
	@RequestMapping(value={"/", "/index"}, 
			method=RequestMethod.GET)
	public String logInStart(@RequestParam(value="action",required=false) String action,
			@ModelAttribute(value="autenticatedLogin") String login,
			HttpServletRequest request,
			@ModelAttribute("currentUser") UserAccount user,
			Model model){
		
		if(user.getLogin()==null||user.getLogin().equals("")){
			model.addAttribute("currentUser", logClass.findUserByUsername(login));
		}
		if ( action != null
				&&action.equals("approved")){
			
			return "forward:/approved";
		}
		if (action != null
				&& action.equals("putmoney")){
			return "forward:/put";
		}
		if (action != null
				&& action.equals("getmoney")){
			return "forward:/get";
		}
			return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, @RequestParam(value="action",required=false) String action) {
		 if (action!=null&& action.equals("error")) {
			 model.addAttribute("Wrong", "Wrong login or password");
	     }
		      
		model.addAttribute("totalUsers", logClass.getTotalUsers());
        return "login";
}
	
	@ModelAttribute("autenticatedLogin")
	public String loginAttribute(){
		return "";
	}
	@ModelAttribute("currentUser")
	public UserAccount pass(){
		UserAccount account= new UserAccount();
		account.setLogin("");
		account.setPassword("");
		return account;
	}
}
