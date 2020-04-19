package it.polito.tdp.formulaone.model;

public class LapTime {

	private Race race ; // refers to {@link Race}
	private Driver driver ; // referst to {@link Driver}
	private int lap ;
	private int miliseconds ; // numerical version, sutable for computations
	
	
	public LapTime(Race race, Driver driver, int lap,int miliseconds) {
		super();
		this.race = race;
		this.driver = driver;
		this.lap = lap;
		this.miliseconds = miliseconds;
	}
	public Race getRace() {
		return race;
	}
	public void setRace(Race race) {
		this.race = race;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public int getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}
	public int getMiliseconds() {
		return miliseconds;
	}
	public void setMiliseconds(int miliseconds) {
		this.miliseconds = miliseconds;
	}



}
