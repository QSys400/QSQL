package qPlugin;

 

 

 

import qPlugin.qAnnotations.PluginEvent;

 
public class QPlugin implements Comparable {
	
	 private int id;
	 private String pluginClass;
	 private PluginEvent pluginEvent;
	 private int pluginSeq;
	 private boolean editable;
	 private boolean active;
  
 
	
	public void copy(QPlugin copyFrom)
	{
		
		this.setPluginSeq(copyFrom.getPluginSeq());
		this.setactive(copyFrom.isactive());
		this.setEditable(copyFrom.isEditable());
	
	}
 


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getPluginClass() {
		return pluginClass;
	}



	public void setPluginClass(String pluginClass) {
		this.pluginClass = pluginClass;
	}



	public PluginEvent getPluginEvent() {
		return pluginEvent;
	}



	public void setPluginEvent(PluginEvent pluginEvent) {
		this.pluginEvent = pluginEvent;
	}



	public int getPluginSeq() {
		return pluginSeq;
	}



	public void setPluginSeq(int pluginSeq) {
		this.pluginSeq = pluginSeq;
	}

 


 

	public boolean isactive() {
		return active;
	}



	public void setactive(boolean active) {
		this.active = active;
	}



	public boolean isEditable() {
		return editable;
	}



	public void setEditable(boolean editable) {
		this.editable = editable;
	}



	@Override
	public int compareTo(Object o) {
		QPlugin q = (QPlugin)o;
		 return this.getPluginSeq() - q.getPluginSeq();
	}
 


 

 
}
