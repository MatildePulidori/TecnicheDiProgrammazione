package it.polito.tdp.toto;

public class TestSchedina {

	public static void main(String[] args) {
		Schedina s = new Schedina(13) ;
		
		s.add(Risultato.UNO);
		s.add(Risultato.UNO);
		s.add(Risultato.UNO);
		
		s.add(Risultato.DUE);
		s.add(Risultato.DUE);
		s.add(Risultato.DUE);

		s.add(Risultato.ICS);
		s.add(Risultato.ICS);
		s.add(Risultato.ICS);

		s.add(Risultato.UNO);
		s.add(Risultato.UNO);
		s.add(Risultato.UNO);

		s.add(Risultato.DUE);
		
		System.out.println(s.toString());
}

}
