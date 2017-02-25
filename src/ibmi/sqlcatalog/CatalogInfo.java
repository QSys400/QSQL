package ibmi.sqlcatalog;

import java.util.ArrayList;

import ibmi.Result;
import qJob.QJob;
 

public class CatalogInfo {

	private static ArrayList<Result> result =null; ;
	
	
	
	public   ArrayList<Result> getResult() {
		return result==null?new ArrayList<Result>(): result;
	}





	public   void setResult(ArrayList<Result> result) {
		CatalogInfo.result = result;
	}

	public void loadDetails(QJob currentJob,String objectType, String objectString,boolean detailed)
	{
		if((objectType==null || objectType.isEmpty() ) || (objectString == null || objectString.isEmpty())) return;
		
		String objectLib="";
		String objectName="";
		String[] values ;
		if(objectString.contains("."))
		{
			values= objectString.split("\\.");
		}
		else if(objectString.contains("/")){
			values= objectString.split("/");
		}
		else 
		{
			values= objectString.split(" ");
		}
		
		switch(values.length)
		{
			case 1:
				objectName = values[0];
				break;
			case 2:	
				objectLib = values[0];
				objectName = values[1];
				break;
				
		
		}
		
		this.loadDetails(currentJob, objectType, objectLib, objectName, detailed);
	
	}
	
	public   void loadDetails(QJob currentJob, String objectType, String objectLib,String objectName,  boolean detailed)
	{
		setResult(getDetails(currentJob,objectType,objectLib,objectName,detailed));
	}


	private   ArrayList<Result> getDetails( QJob currentJob,String objectType, String objectLib,String objectName,  boolean detailed)
	{
		
		
		
		String className = "ibmi.sqlcatalog."+objectType;
		SQLCatalogInterface one = instantiate(className,SQLCatalogInterface.class);
		
		one.initiate(objectLib.toUpperCase(), objectName.toUpperCase(),currentJob);
		return one.get(detailed);
	}
	
	

	
	
	private   <T> T instantiate(final String className, final Class<T> type){
	    try{
	        return type.cast(Class.forName(className).newInstance());
	    } catch(final InstantiationException e){
	    	e.printStackTrace();
	        throw new IllegalStateException(e);
	    } catch(final IllegalAccessException e){
	    	e.printStackTrace();
	        throw new IllegalStateException(e);
	    } catch(final ClassNotFoundException e){
	    	e.printStackTrace();
	        throw new IllegalStateException(e);
	    }
	}
	
}
