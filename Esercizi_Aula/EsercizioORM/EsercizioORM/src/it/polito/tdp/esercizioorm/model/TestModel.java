package it.polito.tdp.esercizioorm.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		int matricola = 146101;
		int result = model.getTotCreditiFromStudente(matricola);
		System.out.println("Tot crediti: "+result);
		
		//List<Studente> listaStudenti = model.getStudentiFromCorso("01NBAPG");
		//for (Studente s: listaStudenti) {
		//	System.out.println(s);
		//}
		
		List<Corso> listaCorsi = model.getCorsiFromStudente(matricola);
		for (Corso c: listaCorsi) {
			System.out.println(c);
		}
	}

}

