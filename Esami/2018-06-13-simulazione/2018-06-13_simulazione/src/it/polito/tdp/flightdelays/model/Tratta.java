package it.polito.tdp.flightdelays.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Tratta implements Comparable <Tratta> {

	private Airport origin;
	private Airport destination;
	private double avgDelay;
	private double distanza;
	
	public Tratta(Airport origin, Airport destination, double avgDelay) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.avgDelay = avgDelay;
		LatLng point1 = new LatLng(origin.getLatitude(), origin.getLongitude());
		LatLng point2 = new LatLng(destination.getLatitude(), destination.getLongitude());
		this.distanza =  LatLngTool.distance(point1, point2, LengthUnit.KILOMETER);
	}
	
	

	public Airport getOrigin() {
		return origin;
	}

	public Airport getDestination() {
		return destination;
	}

	public double getAvgDelay() {
		return avgDelay;
	}

	public double getDistanza() {
		return distanza;
	}



	@Override
	public String toString() {
		return String.format("%s, %s, %.5f km ", origin, destination, distanza);
	}



	@Override
	public int compareTo(Tratta altra) {
		
		Double peso1 = (this.getAvgDelay()/this.getDistanza());
		Double peso2 = (altra.getAvgDelay()/altra.getDistanza());
		
		return -peso1.compareTo(peso2);
	}
	
	
	
}
