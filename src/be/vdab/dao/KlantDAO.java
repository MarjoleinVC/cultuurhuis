package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Adres;
import be.vdab.entities.Klant;

/*
 klanten toevoegen aan database:
 per nieuwe klant een regel in tabel klanten (long id (=primary), String voornaam, String familienaam, String straat, String huisnr, String postcode, String gemeente, String gebruikersnaam, String wachtwoord  

 alle velden zijn verplicht! op OK klikken op toe te voegen aan database
 mogelijke fouten: lege velden, paswoord != herhaal paswoord, gebruikersnaam bestaat al
 -> ieder fout oplijsten onder formulier
 ->geslaagde aanmaak nieuwe klant = terugkeren naar reserverenBevestigen
 */

public class KlantDAO extends AbstractDAO {

	private static final String CREATE_KLANT = "insert into klanten (voornaam, familienaam, straat, huisnr, postcode, gemeente, gebruikersnaam, paswoord) values (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String FIND_KLANT = "select id, voornaam, familienaam, straat, huisnr, postcode, gemeente from klanten where gebruikersnaam=? and paswoord=?";
	private static final String FIND_EXISTING_GEBRUIKERSNAAM = "select id, voornaam, familienaam, straat, huisnr, postcode, gemeente, gebruikersnaam, paswoord from klanten where gebruikersnaam=?";

	private final static Logger logger = Logger.getLogger(KlantDAO.class
			.getName());

	private Klant resultSetRowToKlant(ResultSet resultSet) throws SQLException {
		try {
			Adres adres = new Adres(resultSet.getString("straat"),
					resultSet.getString("huisnr"),
					resultSet.getString("postcode"),
					resultSet.getString("gemeente"));
			Long id = resultSet.getLong("id");
			String voornaam = resultSet.getString("voornaam");
			String familienaam = resultSet.getString("familienaam");
			/* TODO Column 'gebruikersnaam' not found. */
			String gebruikersnaam = resultSet.getString("gebruikersnaam");//gebruikersnaam
			String paswoord = resultSet.getString("paswoord");
			Klant klant = new Klant(id, voornaam, familienaam, adres,
					gebruikersnaam, paswoord);
			return klant;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database klanten", ex);
			throw new DAOException("Probleem met database klanten", ex);
		}
	}

	public boolean gebruikersnaamBestaat(String gebruikersnaam) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(FIND_EXISTING_GEBRUIKERSNAAM)) {
			boolean bestaat = false;
			statement.setString(1, gebruikersnaam);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					bestaat = true;
				}
				return bestaat;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE,
					"Problemen met het zoeken van de klant in de database", ex);
			throw new DAOException(
					"Problemen met het zoeken van de klant in de database", ex);
		}
	}

	public Klant findKlant(String gebruikersnaam, String paswoord) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(FIND_KLANT)) {
			statement.setString(1, gebruikersnaam);
			statement.setString(2, paswoord);
			try (ResultSet resultSet = statement.executeQuery();) {
				Klant klant = null;
				if (resultSet.next())
					klant = resultSetRowToKlant(resultSet);
				return klant;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE,
					"Kan de gebruiker niet vinden in de database", ex);
			throw new DAOException(
					"Kan de gebruiker niet vinden in de database", ex);
		}
	}

	/* Nieuwe klant wordt niet toegevoegd in MySQL. Webpagina refreshed gewoon */

	public void createKlant(Klant klant) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						CREATE_KLANT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, klant.getVnaam());
			statement.setString(2, klant.getFnaam());
			statement.setString(3, klant.getAdres().getStraat());
			statement.setString(4, klant.getAdres().getHuisNr());
			statement.setString(5, klant.getAdres().getPostcode());
			statement.setString(6, klant.getAdres().getGemeente());
			statement.setString(7, klant.getGebruikersnaam());
			statement.setString(8, klant.getPaswoord());
			statement.executeUpdate();
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				resultSet.next();
				klant.setKlantId(resultSet.getLong(1));
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE,
					"Kan de klant niet toevoegen aan de database", ex);
			throw new DAOException(
					"Kan de klant niet toevoegen aan de database", ex);
		}
	}
}