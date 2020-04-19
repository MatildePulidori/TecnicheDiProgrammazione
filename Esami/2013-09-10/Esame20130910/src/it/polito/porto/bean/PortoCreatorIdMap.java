package it.polito.porto.bean;

import java.util.HashMap;
import java.util.Map;

public class PortoCreatorIdMap {
	
	public Map<Integer, PortoCreator> map;
	
	public PortoCreatorIdMap() {
		map = new HashMap<Integer, PortoCreator>();
	}
	
	public PortoCreator getPortoCreator (int idCreator) {
		return map.get(idCreator);
	}

	public PortoCreator getPortoCreator (PortoCreator creator) {
		PortoCreator old = map.get(creator.getId());
		if (old == null) {
			map.put(creator.getId(), creator);
			return creator;
		}
		return old;
	}
	
}
