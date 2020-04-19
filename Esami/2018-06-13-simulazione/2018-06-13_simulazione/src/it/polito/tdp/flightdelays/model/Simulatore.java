package it.polito.tdp.flightdelays.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.flightdelays.model.Evento.EventType;

public class Simulatore {

	private int K;
	private int V;
	private Airline airline;
	private Model model;
	private PriorityQueue<Evento> pq;
	private List<Passeggero> passeggeri;
	
	public Simulatore (int K, int V, Model model, Airline airline) {
		this.K = K;
		this.V = V;
		this.model=model;
		this.airline = airline;
		this.pq = new PriorityQueue<Evento>();
		this.passeggeri = new ArrayList<Passeggero>();
		this.init();
	}
	
	
	public void init(){
		List<Airport> aeroportiLineaAerea = new ArrayList<Airport>(model.getAirportsFromAirline(airline));
		for (int i=0; i<K; i++) {
			int indice = (int) (Math.random()* aeroportiLineaAerea.size());
			Passeggero p = new Passeggero(i,  aeroportiLineaAerea.get(indice), this.V);
			passeggeri.add(p);
			pq.add(new Evento(EventType.ARRIVO, LocalDateTime.of(2015, Month.JANUARY, 01, 0, 0 ,0 ) , p,  aeroportiLineaAerea.get(indice)));
		}
		
	}
	
	
	
	public List<Passeggero> getPasseggeri() {
		return passeggeri;
	}


	public void run() {
		Evento e;
		while( (e=pq.poll()) !=null) {
			this.processEvent(e);
		}
	}


	private void processEvent(Evento e) {
		Passeggero attuale = e.getP();
		if (attuale.getFlights() == null || attuale.getFlights().size()<this.V) {
			// prendiamo aeroporto in cui è il passeggero 
			Airport a = e.getAirport();
		
			//prendiamo i voli che ci sono da ora (@e.getDateTime()) in poi in aeroporto
			List<Flight> voliDaOraInPoi = new ArrayList<Flight>(model.flights(a, e.getDateTime(), airline));
			if (voliDaOraInPoi.size() == 0) {
				return;
			}
			//aggiungiamo alla lista dei flights del passegero il primo disponibile
			attuale.aggiungiFlight(voliDaOraInPoi.get(0));
			
			
			// aggiorniamo l'orario del prossimo volo nell'aeroporto successivo 
			LocalDateTime ldt = voliDaOraInPoi.get(0).getArrivalDate().plusMinutes( voliDaOraInPoi.get(0).getArrivalDelay());
			attuale.aumentaRitardoAccumulato(voliDaOraInPoi.get(0).getArrivalDelay());
			
			// aggiungiamo l'evento di arrivo e ricerca di un altro volo nell'aeroporto di arrivo all'orario aggiornato
			pq.add(new Evento(EventType.ARRIVO, ldt, attuale, voliDaOraInPoi.get(0).getDestinationAirport()));
			
		}
		else if (attuale.getFlights().size()==this.V) {
			return;
		}
	}
	
	public String getRitardiAccumulati() {
		StringBuilder sb = new StringBuilder();
		for (Passeggero p : this.passeggeri) {
			sb.append(p.toString()+"\n");
		}
	return sb.toString();
	}
}
