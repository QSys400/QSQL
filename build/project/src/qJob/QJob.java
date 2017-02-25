package qJob;

import java.util.ArrayList;
import java.util.LinkedList;

import qJob.qRemoteJob.QRemoteJob;
import qJob.qlocalJob.QLocalJob;
import qPlugin.QPluginDAO;
import qQueryProcessor.QQueryProcessor;
import qutils.IOOperation;

public class QJob {

	private QLocalJob localJob = new QLocalJob();
	private QRemoteJob remoteJob = new QRemoteJob();
	private IOOperation ioOperation = new IOOperation();
	private QPluginDAO qPlugins = new QPluginDAO();
	private QQueryProcessor queryProcessor = new QQueryProcessor();
	private ArrayList<String> queryList  = new ArrayList<String>();
	private int queryListPointer = 0;
	
	
	private QJob(){
		
		//qPlugins.loadPlugins();
	}




	private static QJob currentJob = null;
	
	private static QJob buildJob()
	{
		if(currentJob==null) 
			return new QJob();
		
		return currentJob;
	}
	
	public static QJob getCurrentJob()
	{
		if(currentJob == null ) 
		{
			currentJob = buildJob();
			currentJob.qPlugins.loadPlugins();
		}	
		return currentJob;
	}
	//----------------------------------------------------------------------------------------------------
	
	
	
	public QPluginDAO getqPlugins() {
		return qPlugins;
	}
 
	
	
 
	
	public ArrayList<String> getQueryList() {
		return queryList;
	}

	public String getLastQuery()
	{
		queryListPointer--;
		if(queryListPointer < 0 )  queryListPointer = this.getQueryList().size()-1 ;
		if(queryListPointer>=0)
		{
			return this.getQueryList().get(queryListPointer);
		}
		
		return  "";
	}
	public String getNextQuery()
	{
		queryListPointer++;
		if(queryListPointer >this.getQueryList().size()-1 )
			queryListPointer = this.getQueryList().size() - 1;
		
		if(queryListPointer< this.getQueryList().size() && queryListPointer>=0 )
		{
			return this.getQueryList().get(queryListPointer);
		}
		
		return  "";
	}
	
	public void setLastQuery(String qry)
	{
		 if(getQueryList().size()>0)
		 {
			 if(qry.equalsIgnoreCase(this.getQueryList().get(this.getQueryList().size()-1)))
				 return;
		 }
		
		this.getQueryList().add(qry);
		queryListPointer = this.getQueryList().size()-1;
	}
	
	public QQueryProcessor getQueryProcessor() {
		return queryProcessor;
	}

	public QLocalJob getLocalJob() {
		return localJob;
	}
	public IOOperation getIoOperation() {
		return ioOperation;
	}

//	public void setIoOperation(IOOperation ioOperation) {
//		this.ioOperation = ioOperation;
//	}

//	public void setLocalJob(QLocalJob localJob) {
//		this.localJob = localJob;
//	}
	public QRemoteJob getRemoteJob() {
		return remoteJob;
	}
//	public void setRemoteJob(QRemoteJob remoteJob) {
//		this.remoteJob = remoteJob;
//	}
	
	public void shutDown()
	{
		remoteJob.shutDown();
		localJob.shutDown();
	}
	
}
