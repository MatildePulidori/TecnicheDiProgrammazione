package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Simulazione {
	
	//parametri
	private int DROPmiss = 0;
	private int PICKmiss = 0;
	private double k;
	private LocalDate date;
	private Model model;
	private PriorityQueue<Event> pqueue;
	private Map<Station, Integer> stationCount;
	
	
	
	private enum EventType {
		PICK, DROP;
	}
	
	
	//inizializzazione
	public Simulazione(LocalDate date, double k, Model model) {
		
		this.date = date;
		this.k = k;
		this.model = model;
		this.pqueue = new PriorityQueue<Event>();
		this.stationCount = new HashMap<Station, Integer>();
		
	}

	//run
	public void run() {
		List<Trip> trips = model.getTripsByDate(date);
		
		// aggiungere gli eventi alla priority queue
		for(Trip t : trips) {
			pqueue.add(new Event(EventType.PICK, t.getStartDate(), t));
		}
		
		// inizializzo il numero di biciclette per ogni stazione 
		// creando una mappa
		for (Station s : model.getStations()) {
			stationCount.put(s, (int)(s.getDockCount()*k));
		}
		
		// processare gli eventi
		while(!pqueue.isEmpty()) {
			
			Event e = pqueue.poll(); // Prende l'evento dalla coda e lo elimina
			
			
			
			switch(e.type) {
			
			case PICK:
				Station station = model.stationIdMap.get (e.trip.getStartStationID());
				int count = stationCount.get(station);
				
				if (count > 0) {
					// bicicletta disponibile
					count--;
					stationCount.put(station, count);
					
					// aggiungere evento di DROP
					pqueue.add(new Event(EventType.DROP, e.trip.getEndDate(), e.trip));
				}
				else if (count>0) {
					// nessuna bicicletta disponibile
					// l'utente non è riuscito a prendere la bicicletta
					this.PICKmiss++;
					
				}
				break;
				
			case DROP:
				station = model.stationIdMap.get (e.trip.getEndStationID());
				count = stationCount.get(station);
				
				if(count< station.getDockCount()) {
					// ci sono ancora posti disponibili
					count++;
					stationCount.put(station, count);
				}else {
					DROPmiss++;
				}
				break;
				
			}
		}
		
		// ritornare il numero di DROPmiss e PICKmiss
		
	}

	public SimulationResult getResults() {
		return new SimulationResult(this.PICKmiss, this.DROPmiss);
	}
	
	//classe Evento
	private class Event implements Comparable<Event> {
		
		EventType type;
		LocalDateTime date;
		Trip trip;
		
		
		public Event(EventType type, LocalDateTime date, Trip trip) {
			this.type = type;
			this.date = date;
			this.trip = trip;
		}


		public int compareTo(Event other) {
			return this.date.compareTo(other.date);
		}	
	}
	
	
	
}
