package com.tatiana.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tatiana.model.diverAlgorithms.City;

@Repository
public class DBConnectImpl implements DBConnect {

	public final DataSource datasource;

	private static final Logger logger = LoggerFactory
			.getLogger(DBConnectImpl.class);

	@Autowired
	public DBConnectImpl(final DataSource datasource) {
		super();
		this.datasource = datasource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tatiana.data.DBConnect#getCountryNames()
	 */
	@Override
	public Map<String, String> getCountryNames() {
		Statement stmt = null;
		String query = "select country_code, country_name from country order by country_name asc";
		Map<String, String> countries = new LinkedHashMap<>();
		try {
			Connection conn = getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				countries.put(rs.getString("country_name").toUpperCase(),
						rs.getString("country_code"));
			}
			stmt.close();
			return countries;
		} catch (SQLException exception) {
			logger.error("Error running getCountryNames ", exception);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tatiana.data.DBConnect#generateCities(java.lang.String, int)
	 */
	@Override
	public City[] generateCities(final String country, final int population) {
		Statement stmt = null;
		String query = "select * from city where country_code = '"
				+ country + "' and population > " + population;
		System.out.println("The query is: " + query);
		try {
			Connection conn = getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// Obtains total number of records retrieved from the database |X|.
			int i = 0;
			while (rs.next()) {
				i++;
			}
			// Builds an array containing the |X| cities retrieved from the
			// database: X
			City[] citiesArray = new City[i];
			int j = 0;
			rs.beforeFirst();
			while (rs.next()) {
				City c = new City(rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getLong(5),
						rs.getBigDecimal(6), rs.getBigDecimal(7));
				citiesArray[j] = c;
				j++;
			}
			stmt.close();
			return citiesArray;
		} catch (SQLException exception) {
			logger.error("Error running generateCities ", exception);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tatiana.data.DBConnect#generateCities(float, float, float,
	 * float, int)
	 */
	@Override
	public City[] generateCities(final float nELat, final float nELng,
			final float sWLat, final float sWLng, final int population) {
		Statement stmt = null;
		String query = "select * from diverVis.city where latitude <= " + nELat
				+ " and latitude >= " + sWLat + " and longitude <= " + nELng
				+ " and longitude >= " + sWLng + " and population > "
				+ population;
		System.out.println("The query is: " + query);

		try {
			Connection conn = getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// Obtains total number of records retrieved from the database |X|.
			int i = 0;
			while (rs.next()) {
				i++;
			}
			// Builds an array containing the |X| cities retrieved from the
			// database: X
			City[] citiesArray = new City[i];
			int j = 0;
			rs.beforeFirst();
			while (rs.next()) {
				City c = new City(rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getLong(5),
						rs.getBigDecimal(6), rs.getBigDecimal(7));
				citiesArray[j] = c;
				j++;
			}
			stmt.close();
			return citiesArray;
		} catch (SQLException exception) {
			logger.error("Error running generateCities ", exception);
		}
		return null;
	}

	private Connection getConnection() throws SQLException {
		return datasource.getConnection();

	}
}
