package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService{

	@Autowired
	private  AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userService;
	
	
	
	@Override
	public String findLoggedInUsername() {
		System.out.println("We are come into user details ");
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if(userDetails instanceof UserDetails){
			return ((UserDetails) userDetails).getUsername();
		}
		return null;
	}

	@Override
	public void autoLogin(String username, String password) {
	UserDetails userDetails = userService.loadUserByUsername(username);
	UsernamePasswordAuthenticationToken authenticationToken = 
			new UsernamePasswordAuthenticationToken(userDetails,password, 
					userDetails.getAuthorities());
	authenticationManager.authenticate(authenticationToken);
	if(authenticationToken.isAuthenticated()){
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
  }
}
