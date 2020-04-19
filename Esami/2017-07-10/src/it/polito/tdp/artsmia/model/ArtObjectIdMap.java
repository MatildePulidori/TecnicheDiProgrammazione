package it.polito.tdp.artsmia.model;

import java.util.HashMap;

@SuppressWarnings("serial")
public class ArtObjectIdMap extends HashMap<Integer, ArtObject>{

	
	public ArtObject getObj(ArtObject artObject) {
		ArtObject old = this.get(artObject.getId());
		if (old!=null) {
			return old;
		}
		this.put(artObject.getId(), artObject);
		return artObject;
	}
	
}
