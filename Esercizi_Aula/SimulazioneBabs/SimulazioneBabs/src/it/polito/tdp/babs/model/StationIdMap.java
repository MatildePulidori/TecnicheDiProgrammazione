package it.polito.tdp.babs.model;

import java.util.*;

public class StationIdMap {

	private Map <Integer, Station> map = new HashMap<Integer, Station>();
	
	public Station get(int stationID) {
		return map.get(stationID);
	}
	
	public Station get(Station station) {
		Station old = map.get(station.getStationID());
		if(old!=null) {
			return old;
		}
		map.put(station.getStationID(), station);
		return station;
	}
}
