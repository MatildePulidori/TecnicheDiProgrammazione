package it.polito.tdp.gestionale.model;

import java.util.ArrayList;
import java.util.List;


public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
	
		List<FrequentazioneCorso> result = new ArrayList<FrequentazioneCorso>(model.statistiche());
		
		System.out.println(result);
		
		List<Corso> migliorcammino = model.cercaNumCorsiMinimo();
		
		System.out.println(migliorcammino);
	}

}
