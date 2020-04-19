package it.polito.tdp.flightdelays.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{

	
	private EventType type;
	private LocalDateTime dateTime;
	private Airport airport;
	private Passeggero p;
	
	
	public enum EventType{
		ARRIVO;
	}
	
	public Evento(EventType type, LocalDateTime dateTime, Passeggero p, Airport airport) {
		this.type=type;
		this.dateTime=dateTime;
		this.p=p;
		this.airport=airport;
	}
	
	

	public EventType getType() {
		return type;
	}



	public LocalDateTime getDateTime() {
		return dateTime;
	}



	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}



	public Airport getAirport() {
		return airport;
	}



	public Passeggero getP() {
		return p;
	}



	@Override
	public int compareTo(Evento altro) {
		return dateTime.compareTo(altro.dateTime);
	}
	
	
}
