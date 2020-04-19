package it.polito.tdp.flight.model;

import java.util.HashSet;
import java.util.Set;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		Airline airline= model.getAirlines().get((int) (Math.random()* model.getAirlines().size()));

		System.out.println(airline);
		
		Set<Airport> set = new HashSet<>(model.getServiti(airline));
		
		System.out.println(set.toString());
	}

}
