package ibmi.sqlcatalog;

import java.util.ArrayList;

import ibmi.Result;
import qJob.QJob;

public interface SQLCatalogInterface {

	public ArrayList<Result> get();
	public ArrayList<Result> get(boolean detailed);
	public void initiate(String objectLib, String objectName,QJob qjob);
//	public void setJob(QJob qjob);
}
