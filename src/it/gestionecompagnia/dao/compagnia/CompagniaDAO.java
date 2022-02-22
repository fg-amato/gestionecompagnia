package it.gestionecompagnia.dao.compagnia;

import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.IBaseDAO;
import it.gestionecompagnia.model.Compagnia;

public interface CompagniaDAO extends IBaseDAO<Compagnia> {

	public List<Compagnia> findAllByDataAssunzioneGreaterThan(Date dateInput) throws Exception;

	public List<Compagnia> findAllByRagioneSocialeContiene(String input) throws Exception;

	public List<Compagnia> findAllByCodFisContiene(String caratteriInizialiInput) throws Exception;

}
