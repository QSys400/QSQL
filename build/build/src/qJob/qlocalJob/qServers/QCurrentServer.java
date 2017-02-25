/*******************************************************************************
 * QSYS400.COM
 * Written by : Sumit goyal
 * 2016
 *******************************************************************************/
package qJob.qlocalJob.qServers;

import javafx.collections.ObservableList;

public class QCurrentServer {

	private static QServer server= buildServer();

	private static QServer buildServer() {

		 return new QServer();
 
	}

	public static QServer get() {
		if (server == null) {
			server = buildServer();

		}
		setLibListSize(server.getLibl());
		return server;
	}
 
	public static void copy(QServer newServer)
	{
		QServer currentServer = get();
		currentServer.setServerID(newServer.getServerID());
		currentServer.setServerName(newServer.getServerName());
		currentServer.setServerIp(newServer.getServerIp());
		currentServer.setUserName(newServer.getUserName());
		currentServer.setPassword(newServer.getPassword());
		currentServer.setNaming(newServer.getNaming());
		
		currentServer.getLibl().clear();
		currentServer.getLibl().addAll(newServer.getLibl());
		setLibListSize(currentServer.getLibl());
		
	}
	
	public static void clear()
	{
		copy(new QServer());
		
	}
	
	private static void setLibListSize(ObservableList<String> libl)
	{
		for(int i = libl.size(); i<20 ; i++)
		{
			libl.add("");
		}
	}

}
