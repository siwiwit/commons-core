package id.co.gpsc.common.client.util;

import id.co.gpsc.common.util.SimpleDebugerWriter;
import id.co.gpsc.common.util.SimpleDebugerWriterManager;

public class ClientSideSimpleDebugerWriterManager implements SimpleDebugerWriterManager{

	@Override
	public SimpleDebugerWriter getDebugWriter(String debuggerName) {
		return   new ClientSideSimpleDebugerWriter();
	}

}
