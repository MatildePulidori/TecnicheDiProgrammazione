package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {
	public static void main(String[] args) {
		
		
		Model model = new Model();
		
		// --- PUNTO 1 ----
		List<YearSights> yearsInUs = new ArrayList<YearSights>(model.getYearSights());
	
		for (YearSights s : yearsInUs) {
			model.creaGrafo(s);
			System.out.println(String.format("Anno %d, grafo creato (%d vertici e %d archi)",s.getYear(), model.getGrafo().vertexSet().size(), model.getGrafo().edgeSet().size()));
		}
		
	
		
		
	}
}
