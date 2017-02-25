package qQueryProcessor;

import java.util.ArrayList;
import java.util.List;

import ibmi.Result;
import ibmi.sqlcatalog.CatalogInfo;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import qJob.QJob;
import qPlugin.QPluginDAO;
import qPlugin.qAnnotations.PluginEvent;
 
import qPlugin.qInterface.QRunQueryPlugin;
import qutils.ResultSetToTable;
 

public class QQueryProcessor {
	
 
	private StringBuilder query=new StringBuilder();
	private ArrayList<Result> results=new ArrayList<Result>();
	

	public   void run(StringBuilder query, ArrayList<Result> result )
	{
		QQueryProcessor process = new QQueryProcessor();
		process.query = query;
		process.results = result;
		if(process.before())
		{
			if(process.runQuery())
			{
				process.after();
			}
		}
	}
	
	
 	
	private boolean before()
	{
		boolean returnValue = true;
		List<String> loginBeforePlugins =  QJob.getCurrentJob().getqPlugins().get(PluginEvent.RunQuery_Before);
		for(String plugin:loginBeforePlugins)
		{
			if(this.process(plugin) == false)
			{
				return false;
			}
		}
		
		 return returnValue;
	}
	
	private boolean runQuery()
	{
		boolean returnValue = false;
 
		String sql = this.query.toString();
    	if(sql.startsWith("@"))
    	{
    		 
    		CatalogInfo cinfo = new CatalogInfo();
    		if(sql.toUpperCase().startsWith("@ABOUT"))
    		{
    			 
    			String temp = sql.toUpperCase().replace("@ABOUT", "").trim();
    			if(temp.startsWith("TABLE")) cinfo.loadDetails(QJob.getCurrentJob(), "Table", temp.replaceFirst("TABLE", "").trim(),true);
    			if(temp.startsWith("PROCEDURE")) cinfo.loadDetails(QJob.getCurrentJob(), "Procedure", temp.replaceFirst("PROCEDURE", "").trim(),true);
    		}
    		this.results.addAll(cinfo.getResult());
    		
  
    	}
    	else{
    	
   
    	this.results.addAll(QJob.getCurrentJob().getRemoteJob().runQuery(sql));
    	
    	
    	}
		return returnValue;
	}
	
	
	private boolean after()
	{
		boolean returnValue = true;
		List<String> loginBeforePlugins = QJob.getCurrentJob().getqPlugins().get(PluginEvent.RunQuery_After);
		for(String plugin:loginBeforePlugins)
		{
			if(this.process(plugin) == false)
			{
				return false;
			}
		}
		
		 return returnValue;
	}
	
	
	
 
	
	private boolean process(String plugin)
	{
		boolean returnValue = true;
		
		try
		{
			QRunQueryPlugin runQuery =(QRunQueryPlugin) Class.forName(plugin).newInstance();
		  return runQuery.process(this.query,this.results);
		}
		catch(ClassNotFoundException e)
		{
			
		}
		catch(IllegalAccessException e)
		{
			
		}
		catch(InstantiationException e)
		{
			
		}
		return returnValue;
	}
	
	
	
}
