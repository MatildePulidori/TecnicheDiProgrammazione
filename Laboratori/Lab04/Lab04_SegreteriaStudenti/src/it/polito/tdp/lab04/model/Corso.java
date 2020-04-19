package it.polito.tdp.lab04.model;

public class Corso {

	private String codiceInsegnamento;
	private int crediti;
	private String nomeCorso;
	private int periodoDidattico;
	
	public Corso(String codiceInsegnamento, int crediti, String nomeCorso, int periodoDidattico) {
		this.codiceInsegnamento = codiceInsegnamento;
		this.crediti = crediti;
		this.nomeCorso = nomeCorso;
		this.periodoDidattico = periodoDidattico;
	}

	public String getCodiceInsegnamento() {
		return codiceInsegnamento;
	}

	public void setCodiceInsegnamento(String codiceInsegnamento) {
		this.codiceInsegnamento = codiceInsegnamento;
	}

	public int getCrediti() {
		return crediti;
	}

	public void setCrediti(int crediti) {
		this.crediti = crediti;
	}

	public String getNomeCorso() {
		return nomeCorso;
	}

	public void setNomeCorso(String nomeCorso) {
		this.nomeCorso = nomeCorso;
	}

	public int getPeriodoDidattico() {
		return periodoDidattico;
	}

	public void setPeriodoDidattico(int periodoDidattico) {
		this.periodoDidattico = periodoDidattico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceInsegnamento == null) ? 0 : codiceInsegnamento.hashCode());
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
		Corso other = (Corso) obj;
		if (codiceInsegnamento == null) {
			if (other.codiceInsegnamento != null)
				return false;
		} else if (!codiceInsegnamento.equals(other.codiceInsegnamento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nomeCorso;
	}
	
	
	
	 
}
