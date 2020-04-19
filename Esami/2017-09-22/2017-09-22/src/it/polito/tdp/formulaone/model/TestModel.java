package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		Season season = model.getSeasons().get((int)(Math.random()*model.getSeasons().size()));
		
		List<CoppiaRaces> coppiaRaces = model.getMaxArco(season);
		
		model.simula(coppiaRaces.get(0).getRace1(), 0.05	, 10);
		
		Simulatore simulatore = model.getSimulatore();
		
		Map<Integer, Driver> map = new HashMap<Integer,Driver	>(simulatore.getMigliorDriver());
		
		for (Integer integer : map.keySet()) {
			System.out.println("giro " +integer.toString() + " :  "+map.get(integer).toString());
		}
	}
}
