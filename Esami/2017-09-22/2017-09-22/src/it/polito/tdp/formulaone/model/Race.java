package it.polito.tdp.formulaone.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Race {
	
	
	private int raceId ;
	private int year ;
	private String name ;
	private LocalDate date ;
	
	private List<Driver> partecipanti;
	
	public Race(int raceId, int year, String name, LocalDate date) {
		super();
		this.raceId = raceId;
		this.year = year;
		this.name = name;
		this.date = date;
		this.partecipanti = new ArrayList<>();
	}
	
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	public List<Driver> getPartecipanti() {
		return partecipanti;
	}

	public void setPartecipanti(List<Driver> partecipanti) {
		this.partecipanti = partecipanti;
	}

	@Override
	public String toString() {
		return name;
	}

}
