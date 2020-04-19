package it.polito.tdp.formulaone.model;

public class CoppiaRaces implements Comparable<CoppiaRaces>{

	private Race race1;
	private Race race2;
	private int numPartecipantiComuni;
	public CoppiaRaces(Race race1, Race race2, int numPartecipantiComuni) {
		super();
		this.race1 = race1;
		this.race2 = race2;
		this.numPartecipantiComuni = numPartecipantiComuni;
	}
	public Race getRace1() {
		return race1;
	}
	public void setRace1(Race race1) {
		this.race1 = race1;
	}
	public Race getRace2() {
		return race2;
	}
	public void setRace2(Race race2) {
		this.race2 = race2;
	}
	public int getNumPartecipantiComuni() {
		return numPartecipantiComuni;
	}
	public void setNumPartecipantiComuni(int numPartecipantiComuni) {
		this.numPartecipantiComuni = numPartecipantiComuni;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((race1 == null) ? 0 : race1.hashCode());
		result = prime * result + ((race2 == null) ? 0 : race2.hashCode());
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
		CoppiaRaces other = (CoppiaRaces) obj;
		if (race1 == null) {
			if (other.race1 != null)
				return false;
		} else if (!race1.equals(other.race1))
			return false;
		if (race2 == null) {
			if (other.race2 != null)
				return false;
		} else if (!race2.equals(other.race2))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return  race1 + " - " + race2 + ", numPartecipantiComuni=" + numPartecipantiComuni;
	}
	 
	@Override
	public int compareTo(CoppiaRaces o) {
		return Integer.compare(this.numPartecipantiComuni, o.numPartecipantiComuni);
	}
	
	
}
