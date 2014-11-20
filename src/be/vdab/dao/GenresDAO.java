package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Genre;

/*
 vinden van alle genres in database
 */
public class GenresDAO extends AbstractDAO {

	private static final String FIND_ALL_GENRES = ("select id as genreID, naam from genres order by naam");
	private static final String READ_GENRES = FIND_ALL_GENRES + "where id=?";

	private final static Logger logger = Logger.getLogger(GenresDAO.class
			.getName());

	private Genre resultSetRowToGenre(ResultSet resultSet) throws SQLException {
		return new Genre(resultSet.getLong("genreID"),
				resultSet.getString("naam"));
	}

	public Iterable<Genre> findAll() {
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(FIND_ALL_GENRES)) {
			/*
			 * TreeSet --> geen dubbels en natural ordening!
			 */
			TreeSet<Genre> genres = new TreeSet<>();
			while (resultSet.next()) {
				genres.add(resultSetRowToGenre(resultSet));
			}
			return genres;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Problemen met database cultuurhuis", ex);
			throw new DAOException("Probleem met database cultuurhuis", ex);
		}
	}

	public Genre read(long genreId) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(READ_GENRES)) {
			statement.setLong(1, genreId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSetRowToGenre(resultSet);
				}
				return null;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Problemen met database cultuurhuis", ex);
			throw new DAOException("Probleem met database cultuurhuis", ex);
		}
	}
}