/*
 * TODO Warning: WARNING: The web application [/Test_cultuurhuis] appears to have started a thread named 
 * [Abandoned connection cleanup thread] but has failed to stop it.
 * Hieronder oplossing van stackoverflow met geregistreerd in web.xml, maar dan start de website niet op.
 * Hoe best oplossen?
 * */
package be.vdab.entities;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class ContextFinalizer implements ServletContextListener {

	private static final Logger LOGGER = Logger
			.getLogger(ContextFinalizer.class.getName());

	public void contextInitialized(ServletContextEvent sce) {
	}

	public void contextDestroyed(ServletContextEvent sce) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		Driver driver = null;
		while (drivers.hasMoreElements()) {
			try {
				driver = drivers.nextElement();
				DriverManager.deregisterDriver(driver);
				LOGGER.warning(String.format("Driver %s deregistered", driver));
			} catch (SQLException ex) {
				LOGGER.warning(String.format("Error deregistering driver %s",
						driver));
			}
		}
		try {
			AbandonedConnectionCleanupThread.shutdown();
		} catch (InterruptedException ex) {
			LOGGER.warning("SEVERE problem cleaning up: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
