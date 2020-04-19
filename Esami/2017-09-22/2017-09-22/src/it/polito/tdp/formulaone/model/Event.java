package it.polito.tdp.formulaone.model;


public class Event implements Comparable<Event> {
	
	public enum EventType {
		GIRO;
	}
	
	
	private EventType eventType;
	private Driver driver;
	private LapTime lapTime;
	private  int millisecsStart;
	
	public Event(EventType type, Driver driver, LapTime lapTime, int millisecsStart ) {
		this.eventType =type;
		this.driver = driver;
		this.lapTime =lapTime;
		this.millisecsStart = millisecsStart;
	}

	@Override
	public int compareTo(Event o) {
		return Integer.compare(this.millisecsStart, o.millisecsStart);
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public LapTime getLapTime() {
		return lapTime;
	}

	public void setLapTime(LapTime lapTime) {
		this.lapTime = lapTime;
	}

	public int getMillisecsStart() {
		return millisecsStart;
	}

	public void setMillisecsStart(int millisecsStart) {
		this.millisecsStart = millisecsStart;
	}
	
	
	

}
