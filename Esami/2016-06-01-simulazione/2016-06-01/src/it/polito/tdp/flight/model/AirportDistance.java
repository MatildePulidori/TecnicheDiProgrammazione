package it.polito.tdp.flight.model;

public class AirportDistance implements Comparable<AirportDistance>{
	
	private Airport airport;
	private double km;
	int numAirportTraversed;
	
	public AirportDistance(Airport airport, int numAirportTraversed, double km) {
		super();
		this.airport = airport;
		this.km = km;
		this.numAirportTraversed = numAirportTraversed;
	}
	public Airport getAirport() {
		return airport;
	}
	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	public double getKm() {
		return km;
	}
	public void setKm(double km) {
		this.km = km;
	}
	
	
	public int getNumAirportTraversed() {
		return numAirportTraversed;
	}
	public void setNumAirportTraversed(int numAirportTraversed) {
		this.numAirportTraversed = numAirportTraversed;
	}
	@Override
	public int compareTo(AirportDistance o) {
		return Double.compare(this.km, o.km);
	}
	@Override
	public String toString() {
		return String.format("%s : %.3f km e %d aeroporti attraversati", this.airport, this.km, this.numAirportTraversed);
	}

	
}
