package it.polito.tdp.Ruzzle.model;

import java.util.*;
import it.polito.tdp.Ruzzle.db.DizionarioDAO;
import javafx.beans.property.*;


public class Model {
	private int SIZE = 4;
	private Board board ;
	private List<String> dizionario ;
	private StringProperty statusText ;

	
	public Model() {
		this(4);
	}
	public Model(int size) {
		this.statusText = new SimpleStringProperty() ;
		this.SIZE =  size ;
		this.board = new Board(SIZE);
		DizionarioDAO dao = new DizionarioDAO() ;
		this.dizionario = dao.listParola() ;
		statusText.set(String.format("%d parole lette", this.dizionario.size())) ;
	
	}
	
	
	public List<Pos> trovaParola(String parola){
		Ricerca ricerca = new Ricerca();
		return ricerca.trovaParola(parola, this.board);
	}
	
	public void reset() {
		this.board.reset() ;
		this.statusText.set("Board Reset");
	}

	public Board getBoard() {
		return this.board;
	}

	public final StringProperty statusTextProperty() {
		return this.statusText;
	}
	

	public final String getStatusText() {
		return this.statusTextProperty().get();
	}
	

	public final void setStatusText(final String statusText) {
		this.statusTextProperty().set(statusText);
	}


	public List<String> trovaTutte() {
		List<String> trovate = new ArrayList<String>();
		for(String parola: dizionario) {
			parola = parola.toUpperCase();
			if (parola.length()>1) {
				if (trovaParola(parola)!=null) {
					trovate.add(parola);
				}
			}
		}
			
		return trovate;
	}
	

}
