package qQueryProcessor;

import java.util.ArrayList;

import ibmi.Result;
import qPlugin.qAnnotations.Plugin;
import qPlugin.qAnnotations.PluginEvent;
import qPlugin.qInterface.QRunQueryPlugin;

@Plugin(event = PluginEvent.RunQuery_After , editable=false)
public class QQueryProcessor_After implements QRunQueryPlugin{

	@Override
	public boolean process(StringBuilder query, ArrayList<Result> result) {
		// TODO Auto-generated method stub
		
		return true;
	}

}
