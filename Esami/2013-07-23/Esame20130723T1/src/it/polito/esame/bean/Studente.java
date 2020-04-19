package it.polito.esame.bean;

import java.util.HashSet;
import java.util.Set;

public class Studente implements Comparable<Studente> {

	private int matricola;
	private String cognome;
	private String nome;
	private String cds;

	private Set<Corso> caricodidattico;

	public Studente(int matricola, String cognome, String nome, String cds) {
		super();
		this.matricola = matricola;
		this.cognome = cognome;
		this.nome = nome;
		this.cds = cds;

		this.caricodidattico = new HashSet<Corso>();
	}

	public int getMatricola() {
		return matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCds() {
		return cds;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}

	public Set<Corso> getCaricodidattico() {
		return caricodidattico;
	}

	public void addCorso(Corso c) {
		this.caricodidattico.add(c);
	}

	public void setCaricodidattico(Set<Corso> caricodidattico) {
		this.caricodidattico = caricodidattico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matricola;
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
		Studente other = (Studente) obj;
		if (matricola != other.matricola)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s %s", cognome, nome);
	}

	@Override
	public int compareTo(Studente o) {
		return this.cognome.compareTo(o.cognome) ;
	}

}
