package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.List;

public class CoppiaAirline {

	private Airline airline1;
	private Airline airline2;
	
	private List<Airport> airportsComuni;

	public CoppiaAirline(Airline airline1, Airline airline2) {
		super();
		this.airline1 = airline1;
		this.airline2 = airline2;
		this.airportsComuni = new ArrayList<>();
	}

	public Airline getAirline1() {
		return airline1;
	}

	public void setAirline1(Airline airline1) {
		this.airline1 = airline1;
	}

	public Airline getAirline2() {
		return airline2;
	}

	public void setAirline2(Airline airline2) {
		this.airline2 = airline2;
	}

	public List<Airport> getAirportsComuni() {
		return airportsComuni;
	}

	public void addAirportsComuni(Airport airportComune) {
		if (!this.airportsComuni.contains(airportComune)) {
			this.airportsComuni.add(airportComune);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airline1 == null) ? 0 : airline1.hashCode());
		result = prime * result + ((airline2 == null) ? 0 : airline2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppiaAirline other = (CoppiaAirline) obj;
		if (airline1 == null) {
			if (other.airline1 != null)
				return false;
		} else if (!airline1.equals(other.airline1))
			return false;
		if (airline2 == null) {
			if (other.airline2 != null)
				return false;
		} else if (!airline2.equals(other.airline2))
			return false;
		return true;
	}
	
	
	
	
	
	
}
