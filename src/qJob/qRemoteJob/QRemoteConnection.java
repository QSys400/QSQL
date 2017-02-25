package qJob.qRemoteJob;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import qJob.qlocalJob.qServers.QServer;
import qStatus.ActionStatus;
import qStatus.QLastStatus;

public class QRemoteConnection {

	private QServer server = null;

	public QServer getServer() {
		return server;
	}

	public void setServer(QServer server) {
		this.server = server;
	}

	QRemoteConnection() {
		try {
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public Connection makeConnection() {
		 
		StringBuilder url = new StringBuilder(
				"jdbc:as400://" + server.getServerIp() + ";prompt=false;errors=full;naming=" + server.getNaming());
		if (server.getLibl().size() > 0)
			url = url.append(";libraries=");
		int count = 0;
		for (String lib : server.getLibl())
			if (lib.trim().length() > 0) {
				if (count++ > 0)
					url.append(",");
				url.append(lib.toUpperCase());
			}

		Connection newConnection = null;
		try {
			 
			newConnection = DriverManager.getConnection(url.toString(), server.getUserName(), server.getPassword());

		} catch (SQLException sqle) {
			QLastStatus.get().update(ActionStatus.Error, "Remote.MakeConnectoin", sqle.getMessage());

		}

		return newConnection;
	}
/*
 * ------------------------------------------------------------------------------------------------------------------
 */
	public Connection testConnection() {
 
		Connection output = null;
		String url = "jdbc:as400://" + server.getServerIp() + ";prompt=false";

		try (Connection con = DriverManager.getConnection(url, server.getUserName(), server.getPassword());) {

			String invalidLibs = validateLibList(con, server);
			if (!invalidLibs.isEmpty())
				QLastStatus.get().update(ActionStatus.Error, "Remote.validateLibl",
						"Lib(s) not found : " + invalidLibs);
			else
				output = makeConnection( );
			

		} catch (SQLException sqle) {
			QLastStatus.get().update(ActionStatus.Error, "Remote.testConnectoin", sqle.getMessage());

		}

		return output;
	}
/*
 * ------------------------------------------------------------------------------------------------------------------------
 */
	public   String validateLibList(Connection con, QServer server) {
		StringBuilder libs = new StringBuilder();

		for (String lib : server.getLibl())
			if (lib.trim().length() > 0 && !libExits(con, lib.toUpperCase()))
				libs.append(lib + " ");
		return libs.toString().trim();
	}
/*
 * -------------------------------------------------------------------------------------------------------------------------
 */
	public   boolean libExits(Connection con, String libName) {
		boolean output = true;
		try (Statement sql = con.createStatement();
				ResultSet rs = sql
						.executeQuery("SELECT 1 FROM QSYS2.SYSSCHEMAS WHERE SCHEMA_NAME = '" + libName.trim() + "'")) {
			if (!rs.isBeforeFirst())
				output = false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			output = false;
		}

		return output;
	}

}
