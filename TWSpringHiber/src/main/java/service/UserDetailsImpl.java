package service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import dbModels.LogInClass;
import dbModels.Role;
import dbModels.UserAccount;

public class UserDetailsImpl implements UserDetailsService{
    private static final String NOT_FOUND_USER = "anonymous";
	@Autowired
	private LogInClass logIn;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount user = logIn.findUserByUsername(username);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		if(user.getRoles()!=null){
		for (Role role : user.getRoles()) {
			if(role!=null){
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			
			}
		}
		}
		else{
			grantedAuthorities.add(new SimpleGrantedAuthority(NOT_FOUND_USER));
			user.setLogin(NOT_FOUND_USER);
			user.setPassword(NOT_FOUND_USER);
		}
		
		return new User(user.getLogin(),user.getPassword(),grantedAuthorities);
	}

}
