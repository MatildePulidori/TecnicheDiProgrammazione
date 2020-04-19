package it.polito.esame.dao;

import java.util.List;

import it.polito.esame.bean.Parola;

public class TestDAO {

	public static void main(String[] args) {
		
		ParoleDAO pdao = new ParoleDAO();
		
		List<Parola> parole = pdao.getAllParola();
		System.out.println("Importa random delle parole dal DB");
		System.out.println(parole.size());
		System.out.println();
		
		System.out.println("Importa le parole con lunghezza 4 dal DB");
		List<Parola> paroleSize = pdao.getAllParolaWithLength(4);
		System.out.println(paroleSize.size());
		
	}

}
