package it.gestionecompagnia.test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.connection.MyConnection;
import it.gestionecompagnia.dao.Constants;
import it.gestionecompagnia.dao.compagnia.CompagniaDAO;
import it.gestionecompagnia.dao.compagnia.CompagniaDAOImpl;
import it.gestionecompagnia.dao.impiegato.ImpiegatoDAO;
import it.gestionecompagnia.dao.impiegato.ImpiegatoDAOImpl;
import it.gestionecompagnia.model.Compagnia;

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
}
