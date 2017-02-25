package qPlugin.qAnnotations;

import qPlugin.qInterface.QRunQueryPlugin;
import qPlugin.qInterface.QServerConnectPlugin;

public enum PluginEvent {

	Connect_Before(QServerConnectPlugin.class),
	Connect_After_Pass(QServerConnectPlugin.class),
	Connect_After_Fail(QServerConnectPlugin.class),
	
	RunQuery_Before(QRunQueryPlugin.class),
	RunQuery_After(QRunQueryPlugin.class);
	 
	
	Class<?> myClass;
	
	PluginEvent(Class<?> myClass)
	{
		this.myClass=myClass;
	}
	
	public Class getClassName()
	{
		return this.myClass;
	}
	
	
}
