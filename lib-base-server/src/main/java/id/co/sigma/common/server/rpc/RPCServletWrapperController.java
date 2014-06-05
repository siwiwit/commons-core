package id.co.sigma.common.server.rpc;




/**
 * wrapper servlet
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa(gede.sutarsa@gmail.com)</a>
 * @version $Id
 * @since 5-aug-2012
 **/
public interface RPCServletWrapperController/*  extends  Controller */{


	/**
	 * service path. url rpc ikut pada definisi interface. ini akan ikut dengan annotation
	 **/
	public String getRemoteServicePath() ; 
}
