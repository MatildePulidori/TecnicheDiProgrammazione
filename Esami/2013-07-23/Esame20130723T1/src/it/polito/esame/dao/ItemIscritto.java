package it.polito.esame.dao;

public class ItemIscritto {
	
	private int matricola ;
	private String codins ;
	
	public ItemIscritto(int matricola, String codins) {
		super();
		this.matricola = matricola;
		this.codins = codins;
	}
	
	/**
	 * @return the matricola
	 */
	public int getMatricola() {
		return matricola;
	}
	/**
	 * @param matricola the matricola to set
	 */
	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}
	/**
	 * @return the codins
	 */
	public String getCodins() {
		return codins;
	}
	/**
	 * @param codins the codins to set
	 */
	public void setCodins(String codins) {
		this.codins = codins;
	}
	
	

}
