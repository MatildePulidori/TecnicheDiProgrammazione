package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		Team team = model.getSquadre().get(6);
		
		// PUNTO 1b
		List<TeamGoalsSeason> list = new ArrayList<>(model.calcolaPuntiInClassifica(team));
		System.out.println(list);
		
		// PUNTO 1c
		TeamGoalsSeason s = model.trovaAnnataOro(team);
		System.out.println(s+ ", con punti rispetto agli altri: "+model.getMaxPunteggio());
		
		// PUNTO 2
		System.out.println(model.camminoVirtuoso(team));
	}

}
