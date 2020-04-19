package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.List;

public class Passeggero {
	
	private int id;
	private Airport airportOrigin ;
	private int numVoli;
	private List<Flight> flights;

	private int ritardoAccumulato;

	public Passeggero(int id, Airport airportOrigin, int numVoli) {
		this.id=id;
		this.airportOrigin = airportOrigin;
		this.numVoli =  numVoli;
		this.flights = new ArrayList<Flight>();
	}

	public int getId() {
		return id;
	}

	public Airport getAirportOrigin() {
		return airportOrigin;
	}

	public void setAirportOrigin(Airport airportOrigin) {
		this.airportOrigin = airportOrigin;
	}

	public int getNumVoli() {
		return numVoli;
	}

	public void setNumVoli(int numVoli) {
		this.numVoli = numVoli;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void aggiungiFlight(Flight flight) {
		this.flights.add(flight);
	}

	public int getRitardoAccumulato() {
		return ritardoAccumulato;
	}

	public void aumentaRitardoAccumulato(int ritardoInPiu) {
		this.ritardoAccumulato += ritardoInPiu;
	}

	@Override
	public String toString() {
		return "Passeggero " + (id+1) + ", ritardo accumulato =" + ritardoAccumulato;
	}
	
	
	
	
	
}
