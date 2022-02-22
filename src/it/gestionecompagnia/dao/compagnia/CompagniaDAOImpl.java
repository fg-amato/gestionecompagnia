package it.gestionecompagnia.dao.compagnia;

import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.AbstractMySQLDAO;
import it.gestionecompagnia.model.Compagnia;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	@Override
	public List<Compagnia> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Compagnia get(Long idInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Compagnia input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Compagnia input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Compagnia input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Compagnia> findByExample(Compagnia input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compagnia> findAllByDataAssunzioneGreaterThan(Date dateInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compagnia> findAllByRagioneSocialeContiene(String input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compagnia> findAllByCodFisContiene(String caratteriInizialiInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
