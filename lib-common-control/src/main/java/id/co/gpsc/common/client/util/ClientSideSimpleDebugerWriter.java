package id.co.gpsc.common.client.util;

import id.co.gpsc.common.util.SimpleDebugerWriter;
import id.co.sigma.common.util.NativeJsUtilities;

public class ClientSideSimpleDebugerWriter implements SimpleDebugerWriter{

	@Override
	public void debug(String message) {
		NativeJsUtilities.getInstance().writeToBrowserConsole(message);
		
	}

	@Override
	public void error(String message) {
		NativeJsUtilities.getInstance().writeToBrowserConsole(message);
		
	}

}
