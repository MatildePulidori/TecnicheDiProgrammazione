package it.polito.tdp.formulaone.model;

import java.util.HashMap;

public class RaceIdMap {
	
	private HashMap<Integer, Race> map;
	
	public RaceIdMap() {
		this.map = new HashMap<Integer, Race>();
	} 
	
	public Race get(int raceId) {
		return this.map.get(raceId);
	}

	public Race get(Race race) {
		Race old = this.map.get(race.getRaceId());
		if (old == null) {
			this.map.put(race.getRaceId(), race);
			return race;
		}
		return old;
	}
	
}
