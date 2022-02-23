package it.gestionecompagnia.dao.impiegato;

import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.IBaseDAO;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public interface ImpiegatoDAO extends IBaseDAO<Impiegato> {

	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception;

	public int countByDataFondazioneCompagniaGreaterThan(Date dataInput) throws Exception;

	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(Long fatturatoInput) throws Exception;

	public List<Impiegato> findAllErroriAssunzioni() throws Exception;
}
