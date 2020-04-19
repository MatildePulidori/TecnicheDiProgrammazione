package it.polito.tdp.flight.model;

import java.util.*;

public class TestModel {

	public static void main(String[] args) {
		
	
		Model m = new Model(); 
		
		System.out.println("Importo areoporti, linee aeree, rotte; ne stampo la dimensione."); 
		System.out.println();
		System.out.println("Primo areoporto, con le sue rotte.");
		Airport a = m.getAirports().get(0);
		System.out.println(a);
		System.out.println(a.getRoutes());
		
		
		System.out.println();
		System.out.println("Creo un grafo");
		m.createGraph();
		
		System.out.println();
		System.out.println("Calcolo le componenti connesse di un grafo ...");
		m.printStats();
		
		System.out.println();
		System.out.println("... e trovo la più grande componente connessa (stampando la sua dimensione)");
		Set<Airport> biggestSCC = m.getBiggestSCC();
		System.out.println(biggestSCC.size());
		
		
		System.out.println();
		System.out.println("Cerco il cammino minimo da areoporto1 ad areoporto2.");
		
		List<Airport> airportList = new ArrayList<Airport>(biggestSCC);
		int id1 = airportList.get(0).getAirportId();
		int id2 = airportList.get(15).getAirportId();
		
		System.out.println(m.getShortestPath(id1, id2));
		
	}

}
