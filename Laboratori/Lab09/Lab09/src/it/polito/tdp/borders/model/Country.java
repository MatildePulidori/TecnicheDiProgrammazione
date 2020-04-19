package it.polito.tdp.borders.model;

public class Country {
	

	private String stateAbb ;
	private int codeCC;
	private String stateName;
	
	
	public Country(String stateAbb, int codeCC, String stateName) {
		this.stateAbb = stateAbb;
		this.codeCC = codeCC;
		this.stateName = stateName;
	}


	public String getStateAbb() {
		return stateAbb;
	}


	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}


	public int getCodeCC() {
		return codeCC;
	}


	public void setCodeCC(int codeCC) {
		this.codeCC = codeCC;
	}


	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codeCC;
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
		Country other = (Country) obj;
		if (codeCC != other.codeCC)
			return false;
		return true;
	}

	public String toString() {
		return stateName +" (" + stateAbb + ", " + codeCC + ")";
	}


	
	

}
