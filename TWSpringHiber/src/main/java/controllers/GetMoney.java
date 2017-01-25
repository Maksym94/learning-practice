package controllers;

import java.math.BigDecimal;

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
 * This controller return page for get money from account. If user try to go on this page 
 * directly, will be forward to login page. After input number of money representation, check
 * with database and if everything right redirect to successfullyget page, if not to wrongget 
 * page. After all depending on input and balance user will see a difference or not. Uses 
 * POST and GET.
 *
 */
@Controller
@SessionAttributes({"currentLogin","currentPassword"})
public class GetMoney {
	@Autowired
	private AccountInfo accoutData;
	@Autowired
	private LogInClass logIn;

	@RequestMapping(value="/get", method=RequestMethod.GET)
	public String getMoneyPage(@ModelAttribute("currentLogin")String login,
			@ModelAttribute("currentPassword")String password, Model model){
		if (logIn.checkLoginPassword(login, password)) {
			return "get";
		}
		return "/login";
	}
	
	@RequestMapping(value="/getact", method=RequestMethod.POST)
	public String getMoneyAct(@ModelAttribute("currentLogin")String login,
			@ModelAttribute("currentPassword")String password,
			@ModelAttribute("currentget")String paramAmount,
			Model model){
		if (logIn.checkLoginPassword(login, password)
				&& paramAmount.length() != 0) {
			BigDecimal amountPut = new BigDecimal(paramAmount);
			if (accoutData.removeMoney(login, amountPut)) {
				return "successfullyget";
			}
		}
		
		return "/wrongget";
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
