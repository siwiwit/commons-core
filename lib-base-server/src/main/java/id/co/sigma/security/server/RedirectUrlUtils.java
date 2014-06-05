package id.co.sigma.security.server;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * Class helper untuk mendapatkan resource URL dari spring container
 * @author I Gede Mahendra
 * @since Nov 28, 2012, 5:11:51 PM
 * @version $Id
 */
@Component
public class RedirectUrlUtils {
	
	@Resource(name="urlIisServer")
	private String urlIisServer;

	/**
	 * Get URL IIS Server
	 * @return String URL
	 */
	public String getUrlIisServer() {
		return urlIisServer;
	}
}