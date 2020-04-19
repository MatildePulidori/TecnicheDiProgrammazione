package it.polito.tdp.ufo.model;

import java.time.LocalDateTime;

public class Sighting {
	private int id ;
	private LocalDateTime datetime ;
	private State state ;
	private String country ;
	private String duration_min;
	
	
	public Sighting(int id, LocalDateTime datetime, State state, String country, String duration_min) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.state = state;
		this.country = country;
		this.duration_min = duration_min;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public LocalDateTime getDatetime() {
		return datetime;
	}


	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}


	public State getState() {
		return state;
	}


	public void setState(State state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getDuration_min() {
		return duration_min;
	}


	public void setDuration_min(String duration_min) {
		this.duration_min = duration_min;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Sighting other = (Sighting) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return datetime + ", " + state;
	}
	


}
