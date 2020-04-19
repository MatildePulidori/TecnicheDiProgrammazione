package it.polito.tdp.flight.model;

public class TestModel {

	public static void main(String[] args) {
		Model model = new Model();
		
		model.creaGrafo(800);

		Airport airport =model.getAeroportPiuLontano();
		
		System.out.println(airport.getName());
	}

}
