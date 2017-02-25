package qQueryProcessor;

import java.util.ArrayList;

import ibmi.Result;
import qPlugin.qAnnotations.Plugin;
import qPlugin.qAnnotations.PluginEvent;
import qPlugin.qInterface.QRunQueryPlugin;

@Plugin(event = PluginEvent.RunQuery_Before , editable=false)
public class QQueryProcessor_Before implements QRunQueryPlugin{

	@Override
	public boolean process(StringBuilder query, ArrayList<Result> result) {
		// TODO Auto-generated method stub
		
		return true;
	}

}
