package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.*;

import it.polito.tdp.babs.db.BabsDAO;

public class Model {
	
	private BabsDAO bdao;
	private  List<Station> stations;
	StationIdMap stationIdMap;
	
	
	public Model() {
		bdao = new BabsDAO();
		stationIdMap = new StationIdMap();
		stations = bdao.getAllStations( stationIdMap );
	}

	public List<Trip> getTripsByDate(LocalDate date){
		return bdao.getAllTrips(date);
	}
	
	public List<CountResult> getTripCounts(LocalDate date) {
		
		// Se non ci sono trips nella data selezionata stampa null ...
		if (getTripsByDate(date).size()==0) {
			return null;
		}
		// ... altrimenti stampa lista di risultati
		List<CountResult> results = new ArrayList<CountResult>();
		for (Station station : stations) {
			CountResult cres = new CountResult(station, 
					bdao.getArrivals(station, date), bdao.getDepartures(station, date));
			results.add(cres);
		}
		Collections.sort(results);
		return results;	
	
	}

	public List<Station> getStations(){
		return stations;
	}
	
 
	
	public SimulationResult simula(Double k, LocalDate date) {
		
		Simulazione sim = new Simulazione(date, k, this) ;
		sim.run();
		return sim.getResults();
		
	}
	
	

}
