package it.gestionecompagnia.dao.compagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.AbstractMySQLDAO;
import it.gestionecompagnia.model.Compagnia;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Compagnia> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturatoAnnuo"));
				compagniaTemp.setRagioneSociale(rs.getString("ragioneSociale"));
				compagniaTemp.setDataFondazione(rs.getDate("dataFondazione"));
				compagniaTemp.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Compagnia get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setFatturatoAnnuo(rs.getLong("fatturatoAnnuo"));
					result.setRagioneSociale(rs.getString("ragioneSociale"));
					result.setDataFondazione(rs.getDate("dataFondazione"));
					result.setId(rs.getLong("ID"));
				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null || compagniaInput.getId() == null || compagniaInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE compagnia SET ragionesociale=?, fatturatoannuo=?, datafondazione=? where id=?;")) {
			ps.setString(1, compagniaInput.getRagioneSociale());
			ps.setLong(2, compagniaInput.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(compagniaInput.getDataFondazione().getTime()));
			ps.setLong(4, compagniaInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO compagnia (ragionesociale, fatturatoannuo, datafondazione) VALUES (?, ?, ?);")) {
			ps.setString(1, compagniaInput.getRagioneSociale());
			ps.setLong(2, compagniaInput.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(compagniaInput.getDataFondazione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null || compagniaInput.getId() == null || compagniaInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM compagnia WHERE ID=?")) {
			ps.setLong(1, compagniaInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findByExample(Compagnia example) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (example == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		String query = "select * from compagnia where 1=1 ";
		if (example.getRagioneSociale() != null && !example.getRagioneSociale().isEmpty()) {
			query += " and ragionesociale like '" + example.getRagioneSociale() + "%' ";
		}
		if (example.getFatturatoAnnuo() != null) {
			query += " and fatturatoannuo like '" + example.getFatturatoAnnuo() + "%' ";
		}

		if (example.getDataFondazione() != null) {
			query += " and datafondazione>='" + new java.sql.Date(example.getDataFondazione().getTime()) + "' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturatoannuo"));
				compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
				compagniaTemp.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByDataAssunzioneGreaterThan(Date dateInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection
				.prepareStatement("select distinct(c.id),c.ragionesociale, c.fatturatoannuo, c.datafondazione"
						+ " from compagnia c inner join impiegato i on c.id = i.compagnia_id where i.dataassunzione > ?")) {
			ps.setDate(1, new java.sql.Date(dateInput.getTime()));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					compagniaTemp = new Compagnia();
					compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturatoAnnuo"));
					compagniaTemp.setRagioneSociale(rs.getString("ragioneSociale"));
					compagniaTemp.setDataFondazione(rs.getDate("dataFondazione"));
					compagniaTemp.setId(rs.getLong("ID"));
					result.add(compagniaTemp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByRagioneSocialeContiene(String input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input.isBlank() || input == null)
			throw new Exception("Input non valido");
		Compagnia compagniaTemp = null;
		List<Compagnia> result = new ArrayList<Compagnia>();

		try (PreparedStatement ps = connection
				.prepareStatement("select * from compagnia where ragionesociale like ?")) {

			ps.setString(1, "%" + input + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					compagniaTemp = new Compagnia();
					compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturatoAnnuo"));
					compagniaTemp.setRagioneSociale(rs.getString("ragioneSociale"));
					compagniaTemp.setDataFondazione(rs.getDate("dataFondazione"));
					compagniaTemp.setId(rs.getLong("ID"));
					result.add(compagniaTemp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByCodFisContiene(String caratteriInizialiInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
