package it.gestionecompagnia.dao.impiegato;

import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.IBaseDAO;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public interface ImpiegatoDAO extends IBaseDAO<Impiegato> {

	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput);

	public int countByDataFondazioneCompagniaGreaterThan(Date dataInput);

	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(Long fatturatoInput);

	public List<Impiegato> findAllErroriAssunzioni();
}
