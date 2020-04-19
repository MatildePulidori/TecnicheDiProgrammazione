package it.polito.tdp.flight.model;


import java.time.LocalTime;

public class Event implements Comparable<Event>{

	enum EventType{
		ARRIVO;
	}
	
	
	private EventType type;
	private LocalTime time;
	private Airport airport;
	private Passeggero id;
	
	public Event(EventType type, LocalTime time, Airport airport, Passeggero id) {
		super();
		this.type = type;
		this.time = time;
		this.airport = airport;
		this.id=id;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public Airport getAirport() {
		return airport;
	}
	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	public Passeggero getPasseggero() {
		return this.id;
	}
	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}
	
	
	
}
