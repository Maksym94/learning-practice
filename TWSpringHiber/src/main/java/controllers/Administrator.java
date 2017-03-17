package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dbModels.Account;
import dbModels.AdminDataBase;
import dbModels.UserAccount;
/**
 * @author Maximka
 * 
 * This controller is used for control users and their operations. If there is any problem in system
 * and user don't act get or put money, admin can delete it, edit balance on any operation. Also 
 * admin can edit user login or password. All passwords are encrypted. 
 *
 */
@Controller
public class Administrator {

	@Autowired
	private AdminDataBase adminDatabase;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminMenu(Model model) {
		List<UserAccount> userList = adminDatabase.getAllUsers();
		model.addAttribute("user", new UserAccount());
		model.addAttribute("users", userList);
		return "admin";

	}

	@RequestMapping(value = "/admin/edit/{id}")
	public String tryToEdit(@PathVariable("id") int id, Model model) {
		model.addAttribute("user", adminDatabase.getUserById(id));
		List<UserAccount> userList = adminDatabase.getAllUsers();
		model.addAttribute("users", userList);
		return "admin";
	}

	@RequestMapping(value = "/admin/edit/", method = RequestMethod.POST)
	public String submitEdition(@ModelAttribute("user") UserAccount user) {
		UserAccount edittedUser = adminDatabase.getUserById(user.getId());
		edittedUser.setLogin(user.getLogin());
		edittedUser.setPassword(user.getPassword());
		adminDatabase.editUser(edittedUser);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/admin/delete/{id}")
	public String delete(@PathVariable("id") int id) {
		adminDatabase.deleteUser(adminDatabase.getUserById(id));
		return "redirect:/admin";
	}

	@RequestMapping(value = "/admin/about/{id}")
	
	public String getAboutUserOperations(@PathVariable("id") int id,
			Model model) {
		model.addAttribute("userdetails", adminDatabase.getUserById(id));
		model.addAttribute("account", new Account());
		return "about";
	}

	@RequestMapping(value = "/admin/about/delete/{id}")
	public String deleteOperation(@PathVariable("id") int id, 
			@RequestParam(value="idUser") int idUser, 
			Model model) {
		adminDatabase.deleteOperation(id);
		return "redirect:/admin/about/"+idUser;
	}
	
	@RequestMapping(value = "/admin/about/edit/{id}")
	public String editOperation(@PathVariable("id") int id, 
			@RequestParam(value="idUser") int idUser, 
			Model model) {
		model.addAttribute("userdetails", adminDatabase.getUserById(idUser));
		model.addAttribute("account",adminDatabase.findOperatonById(id));
		return "about";
	}
	@RequestMapping(value = "/admin/about/edit/", method=RequestMethod.POST)
	public String submitDeleteOperation(@RequestParam(value="idUser") int idUser,
			@ModelAttribute("account") Account account){
		adminDatabase.editOperaton(account);
		return "redirect:/admin/about/"+idUser;
	}
}
