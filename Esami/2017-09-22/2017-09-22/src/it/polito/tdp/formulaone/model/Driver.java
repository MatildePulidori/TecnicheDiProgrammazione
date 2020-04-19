package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Driver {
	
	private int driverId ;
	private  String forename;
	private String surname;
	
	private List<LapTime> giri;
	private Map<Integer, Integer> arrivi;
	private int i;
	private int milliSecTotali;
	
	
	public Driver(int driverId, String forename, String surname) {
		super();
		this.driverId = driverId;
		this.forename = forename;
		this.surname = surname;
		giri = new ArrayList<>();
		arrivi = new TreeMap<>();
		i=0;
		
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public String getSurname() {
		return surname;
	}
	public void Surname(String surname) {
		this.surname = surname;
	}
	
	public int getMilliSecTotali() {
		return milliSecTotali;
	}
	public void addMilliSec(int milliSec) {
		this.milliSecTotali += milliSec;
	}
	
	public List<LapTime> getGiri() {
		return giri;
	}
	public void setGiri(List<LapTime> giri) {
		this.giri = giri;
	}
	public Map<Integer, Integer> getArrivi(){
		return this.arrivi;
	}

	public void addArrivo(int milliseconds) {
		this.arrivi.put(i, milliseconds);
		i++;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + driverId;
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
		Driver other = (Driver) obj;
		if (driverId != other.driverId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Driver [driverId=" + driverId + ", forename=" + forename + ", surname=" + surname + "]";
	}
	
	
	
	

}
