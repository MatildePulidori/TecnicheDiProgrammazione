package it.polito.tdp.flight.model;

public class Airline {
	
	private int airlineId ;
	private String name ;

	
	public Airline(int airlineId, String name) {
		super();
		this.airlineId = airlineId;
		this.name = name;
		
	}

	public int getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + airlineId;
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
		Airline other = (Airline) obj;
		if (airlineId != other.airlineId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.name;
	}

	
	

}
