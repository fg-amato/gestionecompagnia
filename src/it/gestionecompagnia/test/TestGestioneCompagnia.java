package it.gestionecompagnia.test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.connection.MyConnection;
import it.gestionecompagnia.dao.Constants;
import it.gestionecompagnia.dao.compagnia.CompagniaDAO;
import it.gestionecompagnia.dao.compagnia.CompagniaDAOImpl;
import it.gestionecompagnia.dao.impiegato.ImpiegatoDAO;
import it.gestionecompagnia.dao.impiegato.ImpiegatoDAOImpl;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public class TestGestioneCompagnia {

	public static void main(String[] args) {
		CompagniaDAO compagniaDAOInstance = null;
		ImpiegatoDAO impiegatoDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {

			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);

			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi");

			testInsertCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi");

			testFindById(compagniaDAOInstance);

			testDeleteCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi");

			testUpdateCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi");

			testFindByExample(compagniaDAOInstance);

			testFindAllByDataAssunzioneGreaterThan(compagniaDAOInstance, impiegatoDAOInstance);

			System.out.println("__________________________________________________________________________________");

			System.out.println("In tabella impiegato ci sono " + impiegatoDAOInstance.list().size() + " elementi");

			testInsertImpiegato(impiegatoDAOInstance, compagniaDAOInstance);
			System.out.println("In tabella impiegato ci sono " + impiegatoDAOInstance.list().size() + " elementi");

			testFindById(impiegatoDAOInstance);

			testDeleteImpiegato(impiegatoDAOInstance, compagniaDAOInstance);
			System.out.println("In tabella impiegato ci sono " + impiegatoDAOInstance.list().size() + " elementi");

			testUpdateImpiegato(impiegatoDAOInstance, compagniaDAOInstance);
			System.out.println("In tabella impiegato ci sono " + impiegatoDAOInstance.list().size() + " elementi");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testInsertCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testInsertCompagnia inizio.............");
		int quantiElementiInseriti = compagniaDAOInstance
				.insert(new Compagnia("Ammiocugino SRL", Long.parseLong("7000000"), new Date()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertCompagnia : FAILED");

		System.out.println(".......testInsertCompagnia fine: PASSED.............");
	}

	private static void testFindById(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindById inizio.............");
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testFindById : FAILED, non ci sono voci sul DB");

		Compagnia primoDellaLista = elencoVociPresenti.get(0);

		Compagnia elementoCheRicercoColDAO = compagniaDAOInstance.get(primoDellaLista.getId());

		if (elementoCheRicercoColDAO == null
				|| !elementoCheRicercoColDAO.getRagioneSociale().equals(primoDellaLista.getRagioneSociale()))
			throw new RuntimeException("testFindById : FAILED, i parametri non corrispondono");

		System.out.println(".......testFindById fine: PASSED.............");
	}

	private static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testDeleteCompagnia inizio.............");

		int quantiElementiInseriti = compagniaDAOInstance
				.insert(new Compagnia("Mediaworld SpA", Long.parseLong("50000000"), new Date()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, user da rimuovere non inserito");

		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		int numeroElementiPresentiPrimaDellaRimozione = elencoVociPresenti.size();
		if (numeroElementiPresentiPrimaDellaRimozione < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, non ci sono voci sul DB");

		Compagnia ultimoDellaLista = elencoVociPresenti.get(numeroElementiPresentiPrimaDellaRimozione - 1);
		compagniaDAOInstance.delete(ultimoDellaLista);

		// ricarico per vedere se sono scalati di una unità
		int numeroElementiPresentiDopoDellaRimozione = compagniaDAOInstance.list().size();
		if (numeroElementiPresentiDopoDellaRimozione != numeroElementiPresentiPrimaDellaRimozione - 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, la rimozione non è avvenuta");

		System.out.println(".......testDeleteCompagnia fine: PASSED.............");
	}

	private static void testUpdateCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("..........testUpdateCompagnia inizio............");

		Date dataFondazione = new SimpleDateFormat("dd-MM-yyyy").parse("15-01-1995");

		Compagnia compagniaDaAggiornare = new Compagnia("Da aggiornare RS", Long.parseLong("5000"), dataFondazione);

		if (compagniaDAOInstance.insert(compagniaDaAggiornare) != 1) {
			throw new RuntimeException("testUpdateCompagnia: FAILED, Compagnia non aggiunta");
		}
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		int numeroElementiPrimaDellAggiornamento = elencoCompagniePresenti.size();
		int elementiAggiornati = compagniaDAOInstance
				.update(new Compagnia(elencoCompagniePresenti.get(numeroElementiPrimaDellAggiornamento - 1).getId(),
						"Aggiornato RS", Long.parseLong("500000"), dataFondazione));
		if (elementiAggiornati != 1) {
			throw new RuntimeException("testUpdateCompagnia: FAILED, Aggiornamento non avvenuto correttamente");
		}

		System.out.println("..........testUpdateCompagnia fine: PASSED............");
	}

	private static void testInsertImpiegato(ImpiegatoDAO impiegatoDAOInstance, CompagniaDAO compagniaDAOInstance)
			throws Exception {
		System.out.println(".......testInsertCompagnia inizio.............");
		int quantiNegoziInseriti = compagniaDAOInstance.list().size();
		if (quantiNegoziInseriti < 1) {
			throw new RuntimeException("testInsertImpiegato: FAILED, non ci sono negozi nel DB");
		}
		Compagnia primoCompagniaLista = compagniaDAOInstance.list().get(0);
		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("02-12-1999");
		Date dataAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("10-02-2012");
		Impiegato flavioAmatoImpiegato = new Impiegato("Flavio", "Amato", "CodFisAmato", dataNascita, dataAssunzione,
				primoCompagniaLista);
		int quantiElementiInseriti = impiegatoDAOInstance.insert(flavioAmatoImpiegato);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertCompagnia : FAILED");

		System.out.println(".......testInsertCompagnia fine: PASSED.............");
	}

	private static void testFindById(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println(".......testFindById inizio.............");
		List<Impiegato> elencoVociPresenti = impiegatoDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testFindById : FAILED, non ci sono voci sul DB");

		Impiegato primoDellaLista = elencoVociPresenti.get(0);

		Impiegato elementoCheRicercoColDAO = impiegatoDAOInstance.get(primoDellaLista.getId());

		if (elementoCheRicercoColDAO == null || !elementoCheRicercoColDAO.getNome().equals(primoDellaLista.getNome())
				|| !elementoCheRicercoColDAO.getCognome().equals(primoDellaLista.getCognome())
				|| !elementoCheRicercoColDAO.getCodiceFiscale().equals(primoDellaLista.getCodiceFiscale()))
			throw new RuntimeException("testFindById : FAILED, i parametri non corrispondono");

		System.out.println(".......testFindById fine: PASSED.............");
	}

	private static void testDeleteImpiegato(ImpiegatoDAO impiegatoDAOInstance, CompagniaDAO compagniaDAOInstance)
			throws Exception {
		System.out.println(".......testDeleteImpiegato inizio.............");
		int quantiNegoziInseriti = compagniaDAOInstance.list().size();
		if (quantiNegoziInseriti < 1) {
			throw new RuntimeException("testDeleteImpiegato: FAILED, non ci sono negozi nel DB");
		}
		Compagnia primoCompagniaLista = compagniaDAOInstance.list().get(0);
		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("14-01-2000");
		Date dataAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("15-05-2018");
		Impiegato peppeRossiImpiegato = new Impiegato("Peppe", "Rossi", "CodFisRossi", dataNascita, dataAssunzione,
				primoCompagniaLista);
		int quantiElementiInseriti = impiegatoDAOInstance.insert(peppeRossiImpiegato);
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteImpiegato : FAILED, aggiunzione impiegato non avvenuta");

		int numeroElementiPresentiPrimaDellaRimozione = impiegatoDAOInstance.list().size();
		Impiegato ultimoImpiegatoLista = impiegatoDAOInstance.list().get(numeroElementiPresentiPrimaDellaRimozione - 1);
		impiegatoDAOInstance.delete(ultimoImpiegatoLista);

		int numeroElementiPresentiDopoDellaRimozione = impiegatoDAOInstance.list().size();
		if (numeroElementiPresentiDopoDellaRimozione != numeroElementiPresentiPrimaDellaRimozione - 1)
			throw new RuntimeException("testDeleteImpiegato : FAILED, la rimozione non è avvenuta");

		System.out.println(".......testDeleteImpiegato fine: PASSED.............");

		System.out.println(".......testDeleteImpiegato fine: PASSED.............");

	}

	private static void testUpdateImpiegato(ImpiegatoDAO impiegatoDAOInstance, CompagniaDAO compagniaDAOInstance)
			throws Exception {
		System.out.println("..........testUpdateImpiegato inizio............");

		if (compagniaDAOInstance.list().size() < 1) {
			throw new RuntimeException("testUpdateImpiegato: FAILED, non ci sono compagnie nel DB");
		}
		Compagnia primaCompagniaLista = compagniaDAOInstance.list().get(0);

		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("20-01-1998");
		Date dataAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("12-12-2016");

		Impiegato salvatoreAmatoImpiegato = new Impiegato("SalvatoreDaModificare", "AmatoDaModificare", "SLVTAMA",
				dataNascita, dataAssunzione, primaCompagniaLista);
		impiegatoDAOInstance.insert(salvatoreAmatoImpiegato);
		List<Impiegato> impiegatiPresentiInLista = impiegatoDAOInstance.list();
		int numeroElementiPrimaDellAggiornamento = impiegatiPresentiInLista.size();
		int elementiAggiornati = impiegatoDAOInstance.update(new Impiegato(
				impiegatiPresentiInLista.get(numeroElementiPrimaDellAggiornamento - 1).getId(), "SalvatoreModificato",
				"AmatoModificato", "SLVTAMAMOD", dataNascita, dataAssunzione, primaCompagniaLista));
		if (elementiAggiornati != 1) {
			throw new RuntimeException("testUpdateImpiegato: FAILED, Aggiornamento non avvenuto correttamente");
		}

		System.out.println("..........testUpdateImpiegato fine: PASSED............");
	}

	private static void testFindByExample(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("...........testFindByExample inizio...........");

		Compagnia ammio = new Compagnia("Pi", null, null);

		List<Compagnia> negoziConRagioneSociaeleCheIniziaPerAmmio = compagniaDAOInstance.findByExample(ammio);

		for (Compagnia utentiConNomenegoziConRagioneSociaeleCheIniziaPerAmmioItem : negoziConRagioneSociaeleCheIniziaPerAmmio) {
			if (!utentiConNomenegoziConRagioneSociaeleCheIniziaPerAmmioItem.getRagioneSociale().startsWith("Pi"))
				throw new RuntimeException(
						"testFindByExample : FAILED, la lista contiene ragioni sociali che non iniziano con Ammio");
		}

		System.out.println("...........testFindByExample fine: PASSED...........");
	}

	private static void testFindAllByDataAssunzioneGreaterThan(CompagniaDAO compagniaDAOInstance,
			ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println("...........testFindAllByDataAssunzioneGreaterThan inizio...........");

		List<Impiegato> impiegatiAttuali = impiegatoDAOInstance.list();

		if (impiegatiAttuali.size() < 1) {
			throw new RuntimeException("testFindAllByDataAssunzioneGreaterThan: FAILED, non ci sono impiegati nel DB");
		}

		Date dataAssunzione = new SimpleDateFormat("dd-MM-yyyy").parse("20-01-2018");

		List<Compagnia> compagnieDataAssunzioneGreaterThan = compagniaDAOInstance
				.findAllByDataAssunzioneGreaterThan(dataAssunzione);

		System.out.println("...........testFindAllByDataAssunzioneGreaterThan fine: PASSED...........");
	}
}
