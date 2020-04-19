package it.polito.tdp.spellchecker.model;

import java.util.Comparator;

public class ComparatoreParoleOrdineAlfabetico implements Comparator<String>{

	@Override
	public int compare(String s1, String s2) {
		return s1.toLowerCase().compareTo(s2.toLowerCase());
	}

}
