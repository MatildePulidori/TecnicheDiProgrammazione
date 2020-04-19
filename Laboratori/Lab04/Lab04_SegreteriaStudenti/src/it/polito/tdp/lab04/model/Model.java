package it.polito.tdp.lab04.model;

import java.util.*;

import it.polito.tdp.lab04.DAO.*;

public class Model {

	private StudenteDAO studenteDao;
	private CorsoDAO corsoDao;

	public Model() {
		studenteDao = new StudenteDAO();
		corsoDao = new CorsoDAO();
	}
	
	/**
	 * Metodo che ritorna tutti i corsi del DB
	 * @return
	 */
	public List<Corso> getTuttiICorsi() {
		return corsoDao.getTuttiICorsi();
	}
	
	/**
	 * Metodo che ritorna lo Studente data la matricola (@matricola)
	 * @param matricola
	 * @return
	 */
	public Studente getStudente(int matricola) {
		return studenteDao.getStudente(matricola);
	}

	
	/**
	 * Metodo che ritorna gli studenti iscritti al Corso (@corso)
	 * @param corso
	 * @return
	 */
	public List<Studente> studentiIscrittiAlCorso(Corso corso) {
		return corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	/**
	 * Metodo che ritorna i corsi a cui è iscritto lo studente (@studente)
	 * @param studente
	 * @return
	 */
	public List<Corso> corsiACuiIscrittoStudente(Studente studente){
		return studenteDao.getCorsiACuiIscrittoStudente(studente);
	}
	
	
	/**
	 * Metodo che ritorna vero se lo studente (@studente) è iscritto al corso (@corso),
	 * altrimenti falso
	 * @param corso
	 * @param studente
	 * @return
	 */
	public boolean studenteIscrittoACorso(Corso corso, Studente studente) {
		return studenteDao.isStudenteIscrittoACorso(studente, corso);
	}
	
	
	/**
	 * Metodo che ritorna vero se l'iscrizione dello studente (@studente) al corso (@corso)
	 * è andata a buon fine, altrimenti falso
	 * @param corso
	 * @param studente
	 * @return
	 */
	public boolean inscrizioneStudenteACorso (Corso corso, Studente studente) {
		if (this.studenteIscrittoACorso(corso, studente)==false  && 
				!this.studentiIscrittiAlCorso(corso).contains(studente)) {
			corsoDao.iscriviStudenteACorso(studente, corso);
			return true;
		}
		else {
			return false;
		}
	}
}
