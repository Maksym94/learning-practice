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
 * This controller return page for put money from account. If user try to go on this page 
 * directly, will be forward to login page. After input number of money representation, check
 * with database and if everything right redirect to successfullyput page, if not to wrongput 
 * page. After all depending on input user will see a difference or not. Uses 
 * POST and GET.
 */
@Controller
@SessionAttributes({"currentLogin","currentPassword"})
public class PutMoney {
	@Autowired
	private AccountInfo accoutData;
	@Autowired
	private LogInClass logIn;
	
	@RequestMapping(value="/put", method=RequestMethod.GET)
	public String putMoneyPage(@ModelAttribute("currentLogin")String login,
			@ModelAttribute("currentPassword")String password, Model model){
		if (logIn.checkLoginPassword(login, password)) {
			return "put";
		}
		return "/login";
	}
	
	@RequestMapping(value="/putact", method=RequestMethod.POST)
	public String putMoneyAct(@ModelAttribute("currentLogin")String login,
			@ModelAttribute("currentPassword")String password,
			@ModelAttribute("currentput")String paramAmount,
			Model model){
		if ( logIn.checkLoginPassword(login, password)
				&& paramAmount.length() != 0) {
			BigDecimal amountPut = new BigDecimal(paramAmount);
			if (accoutData.putMoney(login, amountPut)) {
				return "successfullyput";
			}
		}
		return "wrongput";
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