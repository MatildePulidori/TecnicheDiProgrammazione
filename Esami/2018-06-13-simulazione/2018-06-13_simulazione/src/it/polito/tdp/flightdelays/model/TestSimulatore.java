package it.polito.tdp.flightdelays.model;

public class TestSimulatore {

	
	public static void main(String[] args) {
		
		Model m = new Model();
		Airline a = m.getAllAirlines().get(0);
		Simulatore s = new Simulatore(2,3, m, a);
		s.run();
		System.out.println(s.getRitardiAccumulati());

	}

}
