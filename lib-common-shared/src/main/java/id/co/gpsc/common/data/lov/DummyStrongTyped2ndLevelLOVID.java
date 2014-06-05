package id.co.gpsc.common.data.lov;

import id.co.gpsc.common.util.json.ParsedJSONContainer;

/**
 *
 * 
 * initial date : Feb 25, 2013
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 *@version $Id
 *
 */
public enum DummyStrongTyped2ndLevelLOVID implements StrongTyped2ndLevelLOVID<DummyStrongTyped2ndLevelLOVID> {
	;

	@Override
	public String getId() {
		
		return "dummy";
	}

	@Override
	public String getModulGroupId() {
		return "dummy-one";
	}

	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainerData) {
		
		
	}

	@Override
	public DummyStrongTyped2ndLevelLOVID instantiateFromJSON(
			ParsedJSONContainer jsonContainer) {
		
		return null;
	}

}
