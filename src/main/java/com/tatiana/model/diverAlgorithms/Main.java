package com.tatiana.model.diverAlgorithms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

	public static void main(final String[] args) throws Exception {
		// Accessing driver from the JAR FILE
		Class.forName("com.mysql.jdbc.Driver");

		// Creating a variable for the connection called "con"
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/divervis", "root", "");

		// Creating a query
		PreparedStatement statement = con
				.prepareStatement("select * from city where country_code ='ec'");

		// Creating a variable to execute query
		ResultSet result = statement.executeQuery();

		// ResultSetMetaData rsmd = result.getMetaData();

		/*
		 * //Prints column names for (int j = 1; j <= rsmd.getColumnCount();
		 * j++){ System.out.print(rsmd.getColumnName(j) + "\t");//Quitar el
		 * ultimo tabulador. } System.out.println();
		 */

		/*
		 * //Prints column type in the database. for (int j = 1; j <=
		 * rsmd.getColumnCount(); j++){
		 * System.out.print(rsmd.getColumnTypeName(j) + "\t");//Quitar el ultimo
		 * tabulador. } System.out.println();
		 */

		/*
		 * //Prints usable java class or data type for (int j = 1; j <=
		 * rsmd.getColumnCount(); j++){
		 * System.out.print(rsmd.getColumnClassName(j) + "\t");//Quitar el
		 * ultimo tabulador. } System.out.println();
		 */

		// Obtains total number of records retrieved from the database |X|.
		int i = 0;
		while (result.next()) {
			i++;
		}
		System.out.println(i);

		// Builds an array containing the |X| cities retrieved from the
		// database: X
		City[] citiesArray = new City[i];
		int j = 0;
		result.beforeFirst();
		while (result.next()) {
			City c = new City(result.getString(1), result.getString(2),
					result.getString(3), result.getString(4),
					result.getLong(5), result.getBigDecimal(6),
					result.getBigDecimal(7));
			citiesArray[j] = c;
			j++;
		}

		/*
		 * for (int k = 0; k < citiesArray.length; k++){
		 * System.out.println(citiesArray[k]); }
		 */

		long startTime = System.nanoTime();
		MaxSum maxSum = new MaxSum();
		City[] diversifiedCities = maxSum.kSetCities(citiesArray, 20);
		System.out.println("City,Latitude,Longitude");
		for (i = 0; i < diversifiedCities.length; i++) {
			System.out.println(diversifiedCities[i]);
		}

		long estimatedTime = System.nanoTime() - startTime;

		System.out.println("Elapsed time of max sum with treemap: "
				+ estimatedTime / 1000000000.0);

		/*
		 * System.out.println(i + "\t" + result.getString(1) + "\t" +
		 * result.getString(2) + "\t" + result.getString(3) + "\t" +
		 * result.getString(4) + "\t" + result.getLong(5) + "\t" +
		 * result.getBigDecimal(6) + "\t" + result.getBigDecimal(7) + "\t");
		 */
	}

}
