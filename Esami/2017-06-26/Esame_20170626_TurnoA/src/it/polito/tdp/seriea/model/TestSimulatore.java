package it.polito.tdp.seriea.model;

public class TestSimulatore {

	public static void main(String[] args) {
	
		Model model = new Model();
		model.creaGrafo();
		model.simulaTifosi(model.getSeasons().get(0));
	}

}
