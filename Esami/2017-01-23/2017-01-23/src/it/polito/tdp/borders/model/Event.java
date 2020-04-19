package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{
	
	
	private int tempo;
	private Country provenienza;
	private int numMigranti;
	
	public Event( int tempo, Country provenienza, int numMigranti) {
		this.tempo=tempo;
		this.provenienza=provenienza;
		this.numMigranti=numMigranti;
	}


	public int getTempo() {
		return tempo;
	}




	public void setTempo(int tempo) {
		this.tempo = tempo;
	}




	public Country getProvenienza() {
		return provenienza;
	}




	public void setProvenienza(Country provenienza) {
		this.provenienza = provenienza;
	}



	public int getNumMigranti() {
		return numMigranti;
	}




	public void setNumMigranti(int numMigranti) {
		this.numMigranti = numMigranti;
	}




	@Override
	public int compareTo(Event o) {
		return Integer.compare(this.tempo, o.tempo);
	}
	 
	

}
