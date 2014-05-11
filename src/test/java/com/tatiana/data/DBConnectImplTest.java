package com.tatiana.data;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import com.tatiana.init.WebAppConfig;
import com.tatiana.model.diverAlgorithms.City;

public class DBConnectImplTest {
	
	private DBConnectImpl dbConnectImpl;
	private WebAppConfig webAppConfig;
	private MockEnvironment environment;
	
	@Before
	public void setUp(){
		
		environment = new MockEnvironment();
		
		
		environment.setProperty(WebAppConfig.PROPERTY_NAME_DATABASE_DRIVER, "com.mysql.jdbc.Driver");
		environment.setProperty(WebAppConfig.PROPERTY_NAME_DATABASE_PASSWORD, "jdbc:mysql://localhost:3306/divervisual");
		environment.setProperty(WebAppConfig.PROPERTY_NAME_DATABASE_URL, "root");
		environment.setProperty(WebAppConfig.PROPERTY_NAME_DATABASE_USERNAME, "");
		
		
		webAppConfig = new WebAppConfig();
		webAppConfig.env = environment;
		DataSource datasource =webAppConfig.dataSource();
		dbConnectImpl = new DBConnectImpl(datasource);
		
	}
	
	//@Test
	public void testGenerateCities(){
		String country = "";
		int population = 1000;
		City[] cities = dbConnectImpl.generateCities(country, population);
		
		
	}

}
