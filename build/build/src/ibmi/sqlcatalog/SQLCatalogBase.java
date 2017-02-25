package ibmi.sqlcatalog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ibmi.Result;
import qJob.QJob;
 
import qStatus.ActionStatus;
import qStatus.QLastStatus;

public class SQLCatalogBase implements SQLCatalogInterface {

	  QJob currentJob;
	  String objectName ="";
	  String objectLib = "";

	
	  ArrayList<String> shortSQLQuery = new  ArrayList<String>();
	  ArrayList<String> detailSQLQuery = new  ArrayList<String>();
	
	String findLibQuery ="";
	int findLibPlaceholderCounter = 0;
	
	String objectListQuery="";
	
	public void initiate(String objectLib, String objectName, QJob currentJob)
	{
		this.setObjectLib(objectLib);
		this.setObjectName(objectName);
		this.currentJob = currentJob;
	}
	
	public String getFindLibQuery() {
		return findLibQuery;
	}


	public void setFindLibQuery(String findLibQuery) {
		this.findLibQuery = findLibQuery;
	}


	public String getObjectName() {
		return objectName==null?"":objectName;
	}


	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}


	public String getObjectLib() {
		
		return objectLib==null?"":this.objectLib;
	}


	public void setObjectLib(String objectLib) {
		this.objectLib = objectLib;
	}


	public ArrayList<Result> get()
	{
		return this.loadData(this.shortSQLQuery);
	}
	
	
	public ArrayList<Result> get(boolean detailed)
	{
		 
		  return detailed?this.loadData(this.detailSQLQuery):this.loadData(this.shortSQLQuery);
	}
	
	ArrayList<Result> loadData(ArrayList<String> SQLQuery)
	{
		this.findObjectLib();
		if(this.getObjectLib()==null || this.getObjectLib().isEmpty()) return new  ArrayList<Result>();
		if(this.getObjectName() == null || this.getObjectName().isEmpty()) return loadObjectList();
		else return loadObjectDetails(SQLQuery);
	}
	
	
	ArrayList<Result> loadObjectList()
	{
	  ArrayList<String> SQLQuery =  new ArrayList<String>();
	  SQLQuery.add(this.objectListQuery);
	  return loadObjectDetails(SQLQuery);
	}
	
	ArrayList<Result> loadObjectDetails(ArrayList<String> SQLQuery)
	
	{
		ResultSet rs = null;
		Statement stmt = null;
		
		 ArrayList<Result> output = new  ArrayList<Result>();
		
		for(String query : SQLQuery)
		{
			String heading = query.substring(0, query.indexOf("@"));
			String finalQuery  = query.substring(query.indexOf("@")+1).replace("$#LIBNAME#$", "'"+this.getObjectLib()+"'").replace("$#OBJECTNAME#$",  "'"+this.getObjectName()+"'");

		 
			try {
				stmt =this.currentJob.getRemoteJob().getConnection().createStatement();
				rs = stmt.executeQuery(finalQuery);
				output.add( new Result(rs,stmt,heading));
				QLastStatus.get().clear();
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				QLastStatus.get().update(ActionStatus.Error, "SQLCatalog -> loadAbout", sqle.getMessage());
			}
			
		}
		
		
		return output;
		
	}
	
	/**
	 * 
	 * @return
	 */

	private void findObjectLib() {
		if (!(this.getObjectLib().isEmpty())) return;
		if(this.getFindLibQuery()==null ||  this.getFindLibQuery().isEmpty()) return;
	 
		String sql = this.getFindLibQuery().replace("$#OBJECTNAME#$", "'" + this.getObjectName()+"'");

		
		
		 
		
		try (PreparedStatement preSql = this.currentJob.getRemoteJob().getConnection().prepareStatement(sql.toString());) {
			for (String tempLib : this.currentJob.getRemoteJob().getConnectedServer().getLibl()) {
				
				
				
				if (tempLib.trim().isEmpty())
					continue;

 				for(int i = 1 ; i<= this.findLibPlaceholderCounter ; i++) 
					{ 
				 
						preSql.setString(i, tempLib.toUpperCase());
					}
				ResultSet rs = preSql.executeQuery();
				 
				if (rs.isBeforeFirst()) {
					
					this.setObjectLib(tempLib);
					break;
				}
			}
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
			this.setObjectLib("");
		}
	}
}
