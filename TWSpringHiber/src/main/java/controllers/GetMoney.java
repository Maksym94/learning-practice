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
import dbModels.UserAccount;
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
@SessionAttributes({"currentUser"})
public class GetMoney {
	@Autowired
	private AccountInfo accoutData;
	@Autowired
	private LogInClass logIn;

	@RequestMapping(value="/get", method=RequestMethod.GET)
	public String getMoneyPage(
			@ModelAttribute("currentUser") UserAccount user,
			Model model){
			return "get";
	}
	
	@RequestMapping(value="/getact", method=RequestMethod.POST)
	public String getMoneyAct(
			@ModelAttribute("currentUser") UserAccount user,
			@ModelAttribute("currentget")String paramAmount,
			Model model){
		if (paramAmount.length() != 0) {
			BigDecimal amountPut = new BigDecimal(paramAmount);
			if (accoutData.removeMoney(user.getId(), amountPut)) {
				UserAccount useRefresh = logIn.findUserByUsername(user.getLogin());
				user.setPassword(user.getPassword());
				model.addAttribute("currentUser",useRefresh);
				return "successfullyget";
			}
		}
		
		return "/wrongget";
	}
	
	@ModelAttribute("currentUser")
	public UserAccount pass(){
		UserAccount account = new UserAccount();
		account.setLogin("");
		account.setPassword("");
		return account;
	}
}
