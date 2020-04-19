package it.polito.tdp.borders.model;

public class Border {

	private Country countryA;
	private Country countryB;
	private int year;
	private int borderType;
	
	public Border(Country countryA, Country countryB, int year, int borderType) {
		this.countryA = countryA;
		this.countryB = countryB;
		this.year = year;
		this.borderType = borderType;
	}

	public Country getCountryA() {
		return countryA;
	}

	public void setCountryA(Country countryA) {
		this.countryA = countryA;
	}

	public Country getCountryB() {
		return countryB;
	}

	public void setCountryB(Country countryB) {
		this.countryB = countryB;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getBorderType() {
		return borderType;
	}

	public void setBorderType(int borderType) {
		this.borderType = borderType;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + borderType;
		result = prime * result + ((countryA == null) ? 0 : countryA.hashCode());
		result = prime * result + ((countryB == null) ? 0 : countryB.hashCode());
		result = prime * result + year;
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		if (borderType != other.borderType)
			return false;
		if (countryA == null) {
			if (other.countryA != null)
				return false;
		} else if (!countryA.equals(other.countryA))
			return false;
		if (countryB == null) {
			if (other.countryB != null)
				return false;
		} else if (!countryB.equals(other.countryB))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	public String toString() {
		return countryA.getStateName()+", "+countryB.getStateName()+", "+year+"\n";
	}
	

}
