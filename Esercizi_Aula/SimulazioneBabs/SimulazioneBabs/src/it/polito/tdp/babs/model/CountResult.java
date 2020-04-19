package it.polito.tdp.babs.model;

public class CountResult implements Comparable<CountResult>{
	
	private Station station;
	private int numDepartures;
	private int numArrivals;
	

	public CountResult(Station station, int numDepartures, int numArrivals) {
		this.station = station;
		this.numDepartures = numDepartures;
		this.numArrivals = numArrivals;
	}
	
	@Override
	public String toString() {
		return String.format("%-50s %4d %4d\n", station.getName(), numArrivals, numDepartures);
	}

	
	public int compareTo(CountResult other) {
		return Double.compare(this.station.getLat(), other.station.getLat());
	}

	
	

}
