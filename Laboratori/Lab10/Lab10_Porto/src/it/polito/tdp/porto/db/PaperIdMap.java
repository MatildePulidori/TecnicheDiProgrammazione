package it.polito.tdp.porto.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.porto.model.Paper;

public class PaperIdMap {
	
	
	private Map<Integer, Paper> map;
	
	public PaperIdMap() {
		map = new HashMap<Integer, Paper>();
	}

	public Paper get(int idpubblicazione) {
		return map.get(idpubblicazione);
	}
	
	public Paper get(Paper pubblicazione) {
		Paper old = this.get(pubblicazione.getEprintid());
		if (old==null) {
			map.put(pubblicazione.getEprintid(), pubblicazione);
			return pubblicazione;
		}
		return old;
	}
	
	
	public void put (Paper pubblicazione) {
		map.put(pubblicazione.getEprintid(), pubblicazione);
	}
	

}
