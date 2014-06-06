package id.co.gpsc.common.server.util;

import id.co.gpsc.common.util.SimpleDebugerWriter;
import id.co.gpsc.common.util.SimpleDebugerWriterManager;

public class ServerSideSimpleDebugWriterManager implements SimpleDebugerWriterManager{

	@Override
	public SimpleDebugerWriter getDebugWriter(String debuggerName) {
		return   new ServerSideSimpleDebugWriter(debuggerName);
	}
}
