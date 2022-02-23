package it.gestionecompagnia.dao.impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.AbstractMySQLDAO;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {

	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Impiegato> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from impiegato")) {

			while (rs.next()) {
				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("Nome"));
				impiegatoTemp.setCognome(rs.getString("Cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("CodiceFiscale"));
				impiegatoTemp.setId(rs.getLong("ID"));
				impiegatoTemp.setDataAssunzione(rs.getDate("DataAssunzione"));
				impiegatoTemp.setDataDiNascita(rs.getDate("DataNascita"));
				result.add(impiegatoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Impiegato get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Impiegato result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from Impiegato where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Impiegato();
					result.setNome(rs.getString("Nome"));
					result.setCognome(rs.getString("Cognome"));
					result.setCodiceFiscale(rs.getString("CodiceFiscale"));
					result.setDataAssunzione(rs.getDate("DataAssunzione"));
					result.setDataDiNascita(rs.getDate("DataNascita"));
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
	public int update(Impiegato impiegatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (impiegatoInput == null || impiegatoInput.getId() == null || impiegatoInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE impiegato SET nome=?, cognome=?, codicefiscale=?, datanascita = ?, dataassunzione = ?, compagnia_id = ? where id=?;")) {
			ps.setString(1, impiegatoInput.getNome());
			ps.setString(2, impiegatoInput.getCognome());
			ps.setString(3, impiegatoInput.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(impiegatoInput.getDataDiNascita().getTime()));
			ps.setDate(5, new java.sql.Date(impiegatoInput.getDataAssunzione().getTime()));
			ps.setLong(6, impiegatoInput.getCompagniaPerCuiLavora().getId());
			ps.setLong(7, impiegatoInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Impiegato impiegatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (impiegatoInput == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO impiegato (nome , cognome , codicefiscale, dataassunzione, datanascita, compagnia_id) VALUES (?, ?, ?, ?, ?, ?);")) {
			ps.setString(1, impiegatoInput.getNome());
			ps.setString(2, impiegatoInput.getCognome());
			ps.setString(3, impiegatoInput.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(impiegatoInput.getDataAssunzione().getTime()));
			ps.setDate(5, new java.sql.Date(impiegatoInput.getDataDiNascita().getTime()));
			ps.setLong(6, impiegatoInput.getCompagniaPerCuiLavora().getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Impiegato impiegatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (impiegatoInput == null || impiegatoInput.getId() == null || impiegatoInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM impiegato WHERE ID=?")) {
			ps.setLong(1, impiegatoInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findByExample(Impiegato example) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (example == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;

		String query = "select * from impiegato where 1=1 ";
		if (example.getNome() != null && !example.getNome().isEmpty()) {
			query += " and nome like '" + example.getNome() + "%' ";
		}
		if (example.getCognome() != null && !example.getCognome().isEmpty()) {
			query += " and cognome like '" + example.getCognome() + "%' ";
		}

		if (example.getCodiceFiscale() != null && !example.getCodiceFiscale().isEmpty()) {
			query += " and codicefiscale like '" + example.getCodiceFiscale() + "%' ";
		}

		if (example.getDataDiNascita() != null) {
			query += " and datanascita>='" + new java.sql.Date(example.getDataDiNascita().getTime()) + "' ";
		}

		if (example.getDataAssunzione() != null) {
			query += " and dataassunzione >='" + new java.sql.Date(example.getDataAssunzione().getTime()) + "' ";
		}

		if (example.getCompagniaPerCuiLavora() != null) {
			query += " and compagnia_id >='" + example.getCompagniaPerCuiLavora().getId() + "' ";
		}

		try (Statement ps = connection.createStatement()) {

			ResultSet rs = ps.executeQuery(query);
			while (rs.next()) {
				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("Nome"));
				impiegatoTemp.setCognome(rs.getString("Cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("CodiceFiscale"));
				impiegatoTemp.setDataAssunzione(rs.getDate("DataAssunzione"));
				impiegatoTemp.setDataDiNascita(rs.getDate("DataNascita"));
				impiegatoTemp.setId(rs.getLong("ID"));
				result.add(impiegatoTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;

		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato i where i.compagnia_id = ?")) {
			ps.setLong(1, compagniaInput.getId());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					impiegatoTemp = new Impiegato();
					impiegatoTemp.setNome(rs.getString("Nome"));
					impiegatoTemp.setCognome(rs.getString("Cognome"));
					impiegatoTemp.setCodiceFiscale(rs.getString("CodiceFiscale"));
					impiegatoTemp.setDataAssunzione(rs.getDate("DataAssunzione"));
					impiegatoTemp.setDataDiNascita(rs.getDate("DataNascita"));
					impiegatoTemp.setId(rs.getLong("ID"));
					impiegatoTemp.setCompagniaPerCuiLavora(compagniaInput);
					result.add(impiegatoTemp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int countByDataFondazioneCompagniaGreaterThan(Date dataInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (dataInput == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) AS total from impiegato i inner join compagnia c on i.compagnia_id = c.id where c.datafondazione>= ?")) {
			ps.setDate(1, new java.sql.Date(dataInput.getTime()));
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = rs.getInt("total");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(Long fatturatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (fatturatoInput == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from impiegato i inner join compagnia c on i.compagnia_id = c.id where c.fatturatoannuo >= ?")) {
			ps.setLong(1, fatturatoInput);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					impiegatoTemp = new Impiegato();
					impiegatoTemp.setNome(rs.getString("Nome"));
					impiegatoTemp.setCognome(rs.getString("Cognome"));
					impiegatoTemp.setCodiceFiscale(rs.getString("CodiceFiscale"));
					impiegatoTemp.setDataAssunzione(rs.getDate("DataAssunzione"));
					impiegatoTemp.setDataDiNascita(rs.getDate("DataNascita"));
					impiegatoTemp.setId(rs.getLong("ID"));
					result.add(impiegatoTemp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findAllErroriAssunzioni() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
