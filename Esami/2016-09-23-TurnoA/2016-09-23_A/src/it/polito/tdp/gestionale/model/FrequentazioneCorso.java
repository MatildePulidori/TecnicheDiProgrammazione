package it.polito.tdp.gestionale.model;

public class FrequentazioneCorso implements Comparable<FrequentazioneCorso>{
	
	private int numCorsi;
	private int numStudenti;
	
	public FrequentazioneCorso(int numCorsi) {
		super();
		this.numCorsi = numCorsi;
		this.numStudenti = 0;
	}



	public int getNumCorsi() {
		return numCorsi;
	}



	public void setNumCorsi(int numCorsi) {
		this.numCorsi = numCorsi;
	}



	public int getNumStudenti() {
		return numStudenti;
	}



	public void addNumStudenti(int numStudenti) {
		this.numStudenti += numStudenti;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numCorsi;
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
		FrequentazioneCorso other = (FrequentazioneCorso) obj;
		if (numCorsi != other.numCorsi)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Ci sono " + numStudenti + " studenti che seguono " + numCorsi+" corsi.\n";
	}



	@Override
	public int compareTo(FrequentazioneCorso o) {
		return this.numCorsi-o.numCorsi;
	}

}
