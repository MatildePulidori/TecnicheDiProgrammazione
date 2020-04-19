package it.polito.tdp.flight.model;

public class Airport {

	private int airportId;
	private String name;
	private double latitude;
	private double longitude;

	private int numPasseggeri;

	public Airport(int airportId, String name, double latitude, double longitude) {
		super();
		this.airportId = airportId;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.numPasseggeri=0;
	}

	public int getAirportId() {
		return airportId;
	}

	public void setAirportId(int airportId) {
		this.airportId = airportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + airportId;
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
		Airport other = (Airport) obj;
		if (airportId != other.airportId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getNumPasseggeri() {
		return numPasseggeri;
	}

	public void addNumPasseggeri(int numPasseggeri) {
		this.numPasseggeri += numPasseggeri;
	}
	
	public void removeNumPasseggeri(int numPasseggeri) {
		this.numPasseggeri -= numPasseggeri;
	}
}
