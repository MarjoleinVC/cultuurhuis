package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.vdab.entities.Reserveringen;

/*
 reservaties wegschrijven in database: vrije plaatsen aanpassen in tabel voorstellingen (verminderen met aantal tickets)
 + record toevoegen aan tabel reservaties (long id (primary), long klantid, long voorstellingsid, long plaatsen)
 + aangeven gelukte en mislukte reserveringen (als vrije plaatsen < gevraagde tickets
 + reservatiemandje leegmaken
 */
public class ReserverenBevestigenDAO extends AbstractDAO {
	private static final String INSERT_RESERVATIE = "insert into reservaties (klantid,voorstellingsid, plaatsen) values (?,?,?)";
	private static final String SELECT_VRIJEPLAATSEN = "select vrijeplaatsen from voorstellingen where id=?";
	private static final String UPDATE_VRIJEPLAATSEN = "update voorstellingen set vrijeplaatsen=vrijeplaatsen-? where id=?";

	public Reserveringen confirmReserveringen(Reserveringen reserveringen,
			long klantid) throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			try (PreparedStatement statementSelect = connection
					.prepareStatement(SELECT_VRIJEPLAATSEN)) {
				statementSelect.setLong(1, reserveringen.getVoorstelling()
						.getVoorstellingId());
				try (ResultSet resulSet = statementSelect.executeQuery()) {
					if (resulSet.next()) {
						long vrijePlaatsen = resulSet.getLong("vrijeplaatsen");
						if (vrijePlaatsen >= reserveringen.getAantalPlaatsen()) {
							try (PreparedStatement statementUpdate = connection
									.prepareStatement(UPDATE_VRIJEPLAATSEN)) {
								statementUpdate.setLong(1,
										reserveringen.getAantalPlaatsen());
								statementUpdate.setLong(2, reserveringen
										.getVoorstelling().getVoorstellingId());
								statementUpdate.executeUpdate();
								try (PreparedStatement statementInsert = connection
										.prepareStatement(INSERT_RESERVATIE)) {
									statementInsert.setLong(1, klantid);
									statementInsert.setLong(2, reserveringen
											.getVoorstelling()
											.getVoorstellingId());
									statementInsert.setLong(3,
											reserveringen.getAantalPlaatsen());
									statementInsert.executeUpdate();
									reserveringen.setSuccesReservering(true);
								} catch (SQLException ex) {
									throw new DAOException(
											"Problemen met toevoegen van de reservatie in de database");
								}
							} catch (SQLException ex) {
								throw new DAOException(
										"Problemen met het updaten van de vrije plaatsen in de database");
							}
						}
						reserveringen.setSuccesReservering(false);
					}
				}
				/*
				 * Hier wordt alles gecommit als alle statements gelukt zijn.
				 * Als vrijePlaatsen < reserveringen.getAantalPlaatsen() wordt
				 * reserveringen.setSuccesReservering() op false gezet bij de
				 * rollback!
				 */
				connection.commit();
				reserveringen.setSuccesReservering(true);
				return reserveringen;
			} catch (SQLException ex) {
				throw new DAOException(
						"Problemen met het ophalen van de beschikbaare vrije plaatsen uit de database");
			}
		} catch (SQLException ex) {
			try {
				dataSource.getConnection().rollback();
			} catch (SQLException exRollback) {
				throw new DAOException("Problemen met rollback van connection",
						exRollback);
			}
			reserveringen.setSuccesReservering(false);
			return reserveringen;
		} finally {
			if (dataSource.getConnection() != null) {
				try {
					dataSource.getConnection().close();
				} catch (SQLException ex) {
					throw new DAOException(
							"Problemen met sluiten van connection", ex);
				}
			}
		}
	}
}
