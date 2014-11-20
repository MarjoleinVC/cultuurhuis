package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Voorstelling;

/*
 voorstellingen uit database halen volgens genre en primarykey (=id)
 */
public class VoorstellingenDAO extends AbstractDAO {

	private static final String FIND_BY_GENRE = "select v.id as voorstellingID, titel, uitvoerders, datum, g.id as genreID, g.naam as genreNaam, prijs, vrijeplaatsen from voorstellingen v, genres g where v.genreid=g.id and g.naam=? and datum>=NOW() order by datum";
	// vinden op primary key (PK = voorstellingen.id)
	private static final String FIND_BY_PK = "select v.id as voorstellingId, titel, uitvoerders, datum, g.id as genreID, g.naam as genreNaam, prijs, vrijeplaatsen from voorstellingen v, genres g where v.genreid=g.id and v.id=? order by datum";

	private final static Logger logger = Logger
			.getLogger(VoorstellingenDAO.class.getName());

	private Voorstelling resultSetRowToVoorstelling(ResultSet resultSet)
			throws SQLException {
		try {
			Voorstelling voorstelling = new Voorstelling(
					resultSet.getLong("voorstellingId"),
					resultSet.getString("titel"),
					resultSet.getString("uitvoerders"),
					resultSet.getTimestamp("datum"),
					resultSet.getLong("genreid"), resultSet.getDouble("prijs"),
					resultSet.getLong("vrijeplaatsen"));
			return voorstelling;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new DAOException("Probleem met database cultuurhuis", ex);
		}
	}

	public ArrayList<Voorstelling> findByGenre(String selectedGenre) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(FIND_BY_GENRE)) {
			statement.setString(1, selectedGenre);
			try (ResultSet resultSet = statement.executeQuery();) {
				ArrayList<Voorstelling> voorstellingen = new ArrayList<>();
				while (resultSet.next()) {
					voorstellingen.add(resultSetRowToVoorstelling(resultSet));
				}
				return voorstellingen;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new DAOException("Probleem met database cultuurhuis", ex);
		}
	}

	public Voorstelling findByPK(long voorstellingId) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(FIND_BY_PK)) {
			statement.setLong(1, voorstellingId);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				Voorstelling voorstelling = resultSetRowToVoorstelling(resultSet);
				return voorstelling;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new DAOException("Probleem met database cultuurhuis", ex);
		}
	}
}
