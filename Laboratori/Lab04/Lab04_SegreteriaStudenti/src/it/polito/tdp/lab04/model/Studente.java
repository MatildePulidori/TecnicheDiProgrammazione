package it.polito.tdp.lab04.model;

public class Studente {
	
	private int  matricola;
	private String nome;
	private String cognome;
	
	public Studente(int  matricola, String nome, String cognome) {
		this.matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
	}

	public int  getMatricola() {
		return matricola;
	}

	public void setMatricola(int  matricola) {
		this.matricola = matricola;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	
	
}
