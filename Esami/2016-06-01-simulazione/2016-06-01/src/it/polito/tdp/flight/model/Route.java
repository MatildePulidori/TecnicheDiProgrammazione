package it.polito.tdp.flight.model;

public class Route {
	
	private Airline airline ;
	private Airport sourceAirport ;
	private Airport destinationAirport ;
	
	public Route(Airline airline , Airport sourceAirport,Airport destinationAirport) {
		super();
		this.airline = airline;
		this.sourceAirport = sourceAirport;
		this.destinationAirport = destinationAirport;
	}
	
	public Airline getAirline() {
		return airline;
	}
	
	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	
	public Airport getSourceAirport() {
		return sourceAirport;
	}
	
	public void setSourceAirport(Airport sourceAirport) {
		this.sourceAirport = sourceAirport;
	}
	
	public Airport getDestinationAirport() {
		return destinationAirport;
	}
	
	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	

}
