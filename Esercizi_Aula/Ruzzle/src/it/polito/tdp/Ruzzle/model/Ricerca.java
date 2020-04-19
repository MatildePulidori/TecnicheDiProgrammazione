package it.polito.tdp.Ruzzle.model;

import java.util.*;

public class Ricerca {

	public List<Pos> trovaParola(String parola, Board board) {
		for (Pos p: board.getPositions()) {
			if (board.getCellValueProperty(p).get().charAt(0) == parola.charAt(0)) {
				// faccio ricorsione
				List<Pos> percorso = new ArrayList<>();
				percorso.add(p);
				if (cerca(parola, 1, percorso, board))
						return percorso;
			}
		}
		return null ;
	}
	
	private boolean cerca(String parola, int livello, List<Pos> percorso, Board board) {
		
		if (livello == parola.length()) {
			return true;
		}
		
		// trovare tutte le posizioni adiacenti all'ultima posizione usata
		Pos ultima = percorso.get(percorso.size()-1);
		List<Pos> adiacenti = board.getAdiacenti(ultima);
		
		// verificare che contengano la lettera corretta
		// e che non siano state ancora utilizzate
		for(Pos nuova : adiacenti){
			if (board.getCellValueProperty(nuova).get().charAt(0) == parola.charAt(livello) 
					&& !percorso.contains(nuova)) {
				//faccio ricorsione
				percorso.add(nuova);
				//uscita rapida in caso di soluzione
				if (cerca(parola, livello+1, percorso, board) == true)
					return true;
				percorso.remove(percorso.size()-1);
			}
		}
		
		return false;
	}
}
