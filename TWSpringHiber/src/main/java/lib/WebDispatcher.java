package lib;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Maximka
 * Custom web dispatcher, which avoid to use web.xml and all request is intercepted by this dispatcher
 *
 */
public class WebDispatcher extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class<?>[]{WebConfig.class};
	}
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
@Override
protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
	DispatcherServlet ds = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
	ds.setThrowExceptionIfNoHandlerFound(true);
	
	return ds;
}

}
