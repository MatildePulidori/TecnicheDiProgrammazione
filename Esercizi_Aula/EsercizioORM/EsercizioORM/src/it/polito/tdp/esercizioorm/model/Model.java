package it.polito.tdp.esercizioorm.model;

import it.polito.tdp.esercizioorm.dao.CorsoDAO;
import it.polito.tdp.esercizioorm.dao.StudenteDAO;
import java.util.*;

public class Model {
	
	private CorsoDAO cdao;
	private StudenteDAO sdao;
	
	private List<Corso> corsi;
	private List<Studente> studenti;
	
	private CorsoIdMap corsomap;
	private StudenteIdMap studentemap;
	
	public Model(){
		
		cdao = new CorsoDAO();
		sdao = new StudenteDAO();
		
		corsomap= new CorsoIdMap();
		studentemap= new StudenteIdMap();
		
		corsi = cdao.getTuttiCorsi(corsomap);
		System.out.println(corsi.size());
		
		studenti = sdao.getTuttiStudenti(studentemap);
		System.out.println(studenti.size());
		
		for (Studente s: studenti) {
			cdao.getCorsiFromStudente(s, corsomap);
		}
		
		for (Corso c: corsi) {
			sdao.getStudentiFromCorso(c, studentemap);
		}
	}

	public List<Studente> getStudentiFromCorso(String codins){
		Corso c = corsomap.get(codins);
		
		if (c==null) {
			return new ArrayList<Studente>();
		}
		return c.getStudenti();
	}
	
	public List<Corso> getCorsiFromStudente(int matricola){
		Studente s = studentemap.get(matricola);
		
		if (s==null) {
			return new ArrayList<Corso>();
		}
		return s.getCorsi();
	}
	
	public int getTotCreditiFromStudente(int matricola) {
		
		int sum = 0;
		for (Studente s: studenti) {
			if(s.getMatricola()== matricola) {
				for (Corso c: s.getCorsi()) {
					sum += c.getCrediti();
				}
				return sum;
			}	
		}
		return -1;
	}
	
	public boolean iscriviStudenteACorso(int matricola, String codins) {
		// Uso le IdMap per trovare gli oggetti riferimnto a corso e studente
		Corso corso = corsomap.get(codins);
		Studente studente = studentemap.get(matricola);
	
		// Devo verificare se uno dei due metodi get della IdMap fallisce
		if (corso == null || studente== null) {
			// non posso iscrivere uno studente (che non esiste) a un corso (che non esiste)
			return false;
		}
		
		// 1- Aggiornare nel DataBase
		// (Così se ho degli errori nel database non faccio un aggiornamento nella memoria RAM)
		boolean result = sdao.iscriviStudenteACorso(studente, corso);
		
		if (result) {
			//aggiornamento db effettuato con successo
			
			// 2- Aggiornare i riferimenti in memoria
			// Controllando che non ci siano duplicati
			if (!studente.getCorsi().contains(corso)) {
				studente.getCorsi().add(corso);
			}
			if (!corso.getStudenti().contains(studente)) {
				corso.getStudenti().add(studente);
			}
			return true;
		}
		return false;
	}

}
