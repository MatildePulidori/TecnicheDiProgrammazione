package it.polito.tdp.formulaone.model;

public class DriversPair {

	private Driver d1;
	private Driver d2;
	private int numRaceInsieme;
	
	
	public DriversPair(Driver d1, Driver d2, int numRaceInsieme) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.numRaceInsieme = numRaceInsieme;
	}
	public Driver getD1() {
		return d1;
	}
	public void setD1(Driver d1) {
		this.d1 = d1;
	}
	public Driver getD2() {
		return d2;
	}
	public void setD2(Driver d2) {
		this.d2 = d2;
	}
	public int getNumRaceInsieme() {
		return numRaceInsieme;
	}
	public void setNumRaceInsieme(int numRaceInsieme) {
		this.numRaceInsieme = numRaceInsieme;
	}
	
	
	
}
