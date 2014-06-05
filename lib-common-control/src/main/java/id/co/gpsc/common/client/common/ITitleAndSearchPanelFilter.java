package id.co.gpsc.common.client.common;

import id.co.gpsc.common.client.control.SimpleSearchFilterHandler;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface ITitleAndSearchPanelFilter {
	public SimpleSearchFilterHandler getSearchFilterHandler();
	public void setSearchFilterHandler(
			SimpleSearchFilterHandler searchFilterHandler);

}
