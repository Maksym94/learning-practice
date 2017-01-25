package lib;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * This class is used for throwing exception when page not found
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Page not found")
public class NotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
