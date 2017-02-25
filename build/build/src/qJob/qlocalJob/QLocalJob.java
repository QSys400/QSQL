package qJob.qlocalJob;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import qJob.qlocalJob.qServers.QServer;

public class QLocalJob {

	private QServerDB qLocalServerDB = new QServerDB();
 
	
	public QServerDB getqLocalServerDB() {
		return qLocalServerDB;
	}


	public void setqLocalServerDB(QServerDB qLocalServerDB) {
		this.qLocalServerDB = qLocalServerDB;
	}


	public boolean saveServer(QServer server)
	{
		return qLocalServerDB.saveServer(server);
	}
	
	
	public void shutDown()
	{}
	
	
	
}
