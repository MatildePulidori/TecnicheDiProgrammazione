package it.polito.tdp.libretto;

import java.util.*;

public class TestLibretto {
	
	public static void main(String[] args) {
		
		Libretto lib = new Libretto();
		
		lib.add( new Voto("Analisi", 30));
		lib.add( new Voto("Chimica", 28));
		lib.add(new Voto("Tecniche di programmazione", 27));
		lib.add( new Voto ("Fisica", 18));
		
		/*System.out.println("Stampa libretto");
		System.out.println(lib.toString());
		
		System.out.println("\nVoti uguali a 25");
		lib.stampa25();
		
		String nomeCorso = "Tecniche di programmazione";
		System.out.print("\nVoto di un esame dato il nome del corso \n"+nomeCorso+" ");
		System.out.println(lib.getMarkFromClass(nomeCorso));
		
		System.out.println("\nAggiungo un altro voto per un esame gia' esistente");
		lib.add(new Voto ("Tecniche di programmazione", 27));*/
		
		System.out.println("Stampa libretto");
		System.out.println(lib.toString());
		
		
		Libretto newLib = new Libretto (lib.getVoti());
		
		System.out.println("Stampa nuovo libretto");
		System.out.println(newLib.toString());

		newLib.alzaVoti(); 
		
		System.out.println("Stampa libretto senza voti alzati");
		System.out.println(lib.toString());
		System.out.println("Stampa nuovo libretto con voti alzati");
		System.out.println(newLib.toString());
		
		Collections.sort(newLib.getVoti());
		
		System.out.println("Stampa nuovo libretto in ordine alfabetico");
		System.out.println(newLib.toString());
		
		Collections.sort(newLib.getVoti(), new Comparator<Voto>(){
			 public int compare(Voto v1, Voto v2) {
				 return -(v1.getVoto()-v2.getVoto());
			 }
		});
		
		System.out.println("Stampa nuovo libretto in ordine decrescente di voti");
		System.out.println(newLib.toString());
	}

}
