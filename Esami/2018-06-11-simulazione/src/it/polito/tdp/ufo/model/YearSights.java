package it.polito.tdp.ufo.model;

public class YearSights {

	private int year;
	private int numSights;
	
	public YearSights(int year, int numSights) {
		super();
		this.year = year;
		this.numSights = numSights;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getNumSights() {
		return numSights;
	}

	public void setNumSights(int numSights) {
		this.numSights = numSights;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numSights;
		result = prime * result + year;
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
		YearSights other = (YearSights) obj;
		if (numSights != other.numSights)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return year + " (" + numSights + " apparizioni)";
	}
	
	
	
}
