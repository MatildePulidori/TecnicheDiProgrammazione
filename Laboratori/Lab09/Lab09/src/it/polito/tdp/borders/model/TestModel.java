package it.polito.tdp.borders.model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import java.util.*;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel");
		
		System.out.println();
		List<Country> countries = model.getCountries();
		System.out.format("Trovate %d nazioni\n", countries.size());
		
		System.out.println();
		System.out.println("Creo il grafo relativo al 2000 : ");
		Graph<Country,DefaultEdge> grafo = model.creaGrafo(2000);
		System.out.println(grafo.edgeSet().size()+" archi" );
		
		System.out.println();
		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
		System.out.println();
		Map<Country, Integer> stats = model.getCountryCounts();
		for (Country country : stats.keySet())
			System.out.format("%s %d\n", country.getStateName(), stats.get(country));		
		
	}

}
