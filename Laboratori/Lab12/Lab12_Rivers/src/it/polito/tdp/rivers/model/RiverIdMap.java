package it.polito.tdp.rivers.model;

import java.util.HashMap;
import java.util.Map;


public class RiverIdMap {
	
	private Map<Integer, River> map ;
	
	public RiverIdMap() {
		map = new HashMap<Integer, River>();
	}

	public River get(int idRiver) {
		return map.get(idRiver);
	}
	
	public River get(River river) {
		River old = this.get(river.getId());
		if (old==null) {
			map.put(river.getId(), river);
			return river;
		}
		return old;
	}
	
	
	public void put (River river) {
		map.put(river.getId(), river);
	}
	
}
