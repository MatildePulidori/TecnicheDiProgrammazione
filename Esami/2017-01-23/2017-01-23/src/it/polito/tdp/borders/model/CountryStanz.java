package it.polito.tdp.borders.model;

public class CountryStanz implements Comparable<CountryStanz>{
	
	private Country country;
	private int numStanziali;
	public CountryStanz(Country country, int numStanziali) {
		super();
		this.country = country;
		this.numStanziali = numStanziali;
	}
	public Country getCountry() {
		return country;
	}
	public int getNumStanziali() {
		return numStanziali;
	}
	@Override
	public int compareTo(CountryStanz o) {
		return Integer.compare(this.numStanziali, o.numStanziali);
	}
	
	

}
