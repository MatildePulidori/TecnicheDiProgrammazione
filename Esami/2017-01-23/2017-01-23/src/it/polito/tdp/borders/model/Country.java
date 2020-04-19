package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;

public class Country implements Comparable<Country>{

	private int cCode ; 
	private String stateAbb ; 
	private String stateName ; 
	private List<Country> adiacenti;
	private int stanziali;
	private int nonStanziali;

	public Country(int cCode, String stateAbb, String stateName) {
		super();
		this.cCode = cCode;
		this.stateAbb = stateAbb;
		this.stateName = stateName;
		this.adiacenti=new ArrayList<>();
		this.stanziali =0;
		this.nonStanziali=0;
	}

	
	public int getcCode() {
		return cCode;
	}

	
	public void setcCode(int cCode) {
		this.cCode = cCode;
	}

	
	public String getStateAbb() {
		return stateAbb;
	}

	
	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	
	public String getStateName() {
		return stateName;
	}

	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	
	
	public List<Country> getAdiacenti() {
		return adiacenti;
	}


	public void addAdiacenti(Country adiacente) {
		if (!this.adiacenti.contains(adiacente))
			this.adiacenti.add(adiacente);
	}


	public int getStanziali() {
		return stanziali;
	}


	public void addStanziali(int stanziali) {
		this.stanziali += stanziali;
	}


	public int getNonStanziali() {
		return nonStanziali;
	}


	public void addNonStanziali(int nonStanziali) {
		this.nonStanziali += nonStanziali;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cCode;
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
		if (cCode != other.cCode)
			return false;
		return true;
	}

	
	
	@Override
	public String toString() {
		return String.format("[%s=%s]", stateAbb, stateName);
	}


	@Override
	public int compareTo(Country o) {
		return Integer.compare(this.adiacenti.size(), o.adiacenti.size());
	}
	
	
	
}
