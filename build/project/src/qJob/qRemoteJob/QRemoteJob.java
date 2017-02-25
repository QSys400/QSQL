package qJob.qRemoteJob;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ibmi.Result;
import qJob.qlocalJob.qServers.QServer;
import qStatus.ActionStatus;
import qStatus.QLastStatus;

public class QRemoteJob {
 
	

	private ArrayList<String> queryList = new ArrayList<String>();
	private int queryListPointer = 0;
	
	private QServer connectedServer;
	private Connection connection;
	private QRemoteConnection remoteConnection = new QRemoteConnection();
	 
	private String seperator=".";
	private LocalDateTime connectionTime = LocalDateTime.now();     // The current date and time
	private int connectionTimeout = 1800 ; // 30*60 seconds
	
	public ArrayList<String> getQueryList() {
		return queryList;
	}
//	public void setQueryList(ArrayList<String> queryList) {
//		this.queryList = queryList;
//	}
	
	
	public int getQueryListPointer() {
		return queryListPointer;
	}
	public void setQueryListPointer(int queryListPointer) {
		this.queryListPointer = queryListPointer;
	}
	public QServer getConnectedServer() {
		return connectedServer;
	}
	
	
	public void setConnectedServer(QServer connectedServer) {
		this.connectedServer = connectedServer;
		if(this.connectedServer!= null && this.connectedServer.getNaming().equalsIgnoreCase("SYSTEM")) this.setSeperator("/");
		else this.setSeperator(".");
		this.closeConnection();
		remoteConnection.setServer(connectedServer);
		this.setConnection(remoteConnection.testConnection());
	}
	
	
	public Connection getConnection() {
		if(this.connection==null ||this.connectedServer ==null) return null;
		LocalDateTime tempConnectionTime2 = LocalDateTime.now(); 
		boolean resetConnection = false;
		Duration d = Duration.between( connectionTime , tempConnectionTime2 );
		try
		{
			if(d.getSeconds() > this.connectionTimeout || connection.isClosed())
			{
				this.connection.close();
				resetConnection= true;
			}
		}
		catch(Exception e)
		{
			resetConnection= true;
			try{ this.connection.close();}
			catch(Exception e2){}
		}

		if(resetConnection) this.setConnection(remoteConnection.makeConnection());
		return connection;
	}
	
	public void setConnection(Connection connection) {
		this.connectionTime = LocalDateTime.now();  
		this.connection = connection;
	}
 
	public String getSeperator() {
		return seperator;
	}
	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}
	
	
	public void shutDown()
	{
		this.connectedServer =null;
		this.closeConnection();
		
			
	}
	
	private  void closeConnection()
	{
		if(this.getConnection()!=null)
		{
			try{
				this.getConnection().close();
				this.setConnection(null);
				}
				catch(SQLException sqle){}
		}
	}
	
	
	
	public   List<Result> runQuery(String sql)
	{
		List<Result> results = new ArrayList<Result>();
		QLastStatus.get().clear();
		QLastStatus.get().setMessage("Running query....");
		Statement stmt = null;
		 
		try
		{
		 stmt = this.getConnection().createStatement();
		 if(stmt.execute(sql))
		 {
			 boolean moreResult = true;
			 
			 while(moreResult)
			 {
				 ResultSet  rs = stmt.getResultSet();
				if(rs!=null) results.add(new Result(rs,stmt));
				//moreResult = stmt.getMoreResults();
				moreResult = false;
			 }
			 QLastStatus.get().setMessage("Query completed.");
			 
			 if(results.size()==0)
			 {
				 QLastStatus.get().setMessage("Query completed. " + stmt.getUpdateCount() + " record(s) processed.");
			 }
		 }
 
		 
		}catch(SQLException sqle)
		{
			sqle.printStackTrace();
	
			QLastStatus.get().update(ActionStatus.Error, "SelectSatement -> runQuery", sqle.getMessage(),true);
		}
		
		return results;
		
	}
	
	
}
