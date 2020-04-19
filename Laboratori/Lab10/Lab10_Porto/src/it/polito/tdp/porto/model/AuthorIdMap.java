package it.polito.tdp.porto.model;

import java.util.*;

public class AuthorIdMap {

	
	private Map<Integer, Author> map;
	
	public AuthorIdMap() {
		map = new HashMap<Integer, Author>();
	}

	public Author get(int idAutore) {
		return map.get(idAutore);
	}
	
	public Author get(Author autore) {
		Author old = this.get(autore.getId());
		if (old==null) {
			map.put(autore.getId(), autore);
			return autore;
		}
		return old;
	}
	
	
	public void put (Author autore) {
		map.put(autore.getId(), autore);
	}
	
}
