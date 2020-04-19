package it.polito.tdp.seriea.model;


public class Event implements Comparable<Event> {

	private EventType type ;
	private Match match;
	
	enum EventType {
		STESSO_NUMTIFOSI, DIVERSO_NUMTIFOSI;
	}
	
	public Event(EventType type, Match match){
		this.type = type;
		this.match =match;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@Override
	public int compareTo(Event o) {
		return this.match.getDate().compareTo(o.match.getDate());
	}
	
	
	
}
