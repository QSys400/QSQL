package qPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import qJob.QJob;
import qJob.qlocalJob.qServers.QServer;
import qPlugin.qAnnotations.Plugin;
import qPlugin.qAnnotations.PluginEvent;
import qutils.StringCipher;
 

 
public class QPluginDAO { 
	
	private final String dirLocation = "plugins";
	private final String fileExtension = "plg";
	private int largestId =0;
	
	Map<PluginEvent , List<QPlugin>> plugins = new HashMap<PluginEvent , List<QPlugin>>();
	
	public void loadPlugins()
	{
		for(QPlugin plugin : getServerList()) this.add(plugin);
		this.sort();
		
	}
	
	public void add(QPlugin plugin)
	{
		if(plugins.containsKey(plugin.getPluginEvent()))
		{
			plugins.get(plugin.getPluginEvent()).add(plugin);
		}
		else
		{
			List<QPlugin> x = new ArrayList<QPlugin>();
			x.add(plugin);
			plugins.put(plugin.getPluginEvent(), x);
		}
	}
	
	public void add(QPlugin plugin , boolean sort)
	{
		this.add(plugin);
		if(sort) this.sort();
	}
	
	public void sort()
	{
		for( PluginEvent pe : plugins.keySet()) Collections.sort(plugins.get(pe));
	}
	
	public List<QPlugin> getServerList() {
		List<QPlugin> pluginList = new ArrayList<QPlugin>();
		for (Path serverFile : QJob.getCurrentJob().getIoOperation().getFilesList(this.dirLocation,this.fileExtension)) {
			try (BufferedReader br = new BufferedReader(new FileReader(serverFile.toFile()))) {
				String line;
				QPlugin plugin = new QPlugin();
				plugin.setPluginSeq(99);
				plugin.setEditable(false);
				plugin.setactive(true);

				mainloop: while ((line = br.readLine()) != null) {
					if (line.isEmpty())
						continue;

					// id:1
					// ServerName: RZKH.DE
					int divLocation = line.indexOf(":"); // if : not found
					if (divLocation < 0)	continue;
					

					String key = line.substring(0, divLocation);
					String value = line.substring(divLocation + 1);
					
					if(value==null || value.isEmpty()) continue mainloop;
					
					switch (key.toUpperCase().replaceAll(" ", "")) {
					case "ID": // Server ID
						if (!line.isEmpty()) {
							try {
								int id = Integer.parseInt(value.trim());
								plugin.setId(id);
							} catch (NumberFormatException exp) {
								break mainloop;
							}
						}
						break;
					case "CLASS": 
					case "PLUGINCLASS":		
						plugin.setPluginClass(value.trim());
						break;
					case "PLUGINEVENT": 
					case "EVENT": 
						plugin.setPluginEvent(PluginEvent.valueOf(value.trim()));
						break;
					case "PLUGINSEQ":  
					case "SEQ": 
						try {
							int id = Integer.parseInt(value.trim());
							plugin.setPluginSeq(id);
							} 
						catch (NumberFormatException exp) {}
						
						break;
					case "EDITABLE": 
						if(value.trim().equalsIgnoreCase("TRUE") ||  value.trim().equalsIgnoreCase("Y") ) plugin.setEditable(true);
						break;
					case "ACTIVE":  
						if(value.trim().equalsIgnoreCase("TRUE") ||  value.trim().equalsIgnoreCase("Y") ) plugin.setactive(true);
						else  plugin.setactive(false);
						break;
 
 

					}

				} // end of while loop

				if (this.validate(plugin)) pluginList.add(plugin);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return pluginList;
	}

	

	public   boolean validate(QPlugin  qPlugin)
	{
		boolean returnValue = false;
		if(qPlugin.getPluginClass() == null || qPlugin.getPluginClass().isEmpty()) return false;
 
			 
		Class<?> pluginClass= null;
		Object whatInstance = null;
		
		ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
		try{		pluginClass = myClassLoader.loadClass(qPlugin.getPluginClass());			}
		catch(ClassNotFoundException e)		{ e.printStackTrace();; return false; }
		
		
		try	{ whatInstance = pluginClass.newInstance();		}
		catch(IllegalAccessException e)	{ 	e.printStackTrace();return false; }
		catch(InstantiationException e)	{	e.printStackTrace(); return false; }
	 
		
	
		Plugin pluginAnnotation = (Plugin)pluginClass.getAnnotation(Plugin.class);
		
		if(pluginAnnotation == null){  	 return false;	}
		else{
			qPlugin.setEditable(pluginAnnotation.editable());
			qPlugin.setPluginEvent(pluginAnnotation.event());
			qPlugin.setPluginSeq(pluginAnnotation.seq());
			qPlugin.setactive(pluginAnnotation.active());
		}
		
		 
		if(qPlugin.getPluginEvent().getClassName().isInstance(whatInstance))	{	returnValue =  true;			}
		
		return returnValue;
	}
	
	
	public List<String> get(PluginEvent event)
	{
		List<String> list = new ArrayList<String>();
			if(this.plugins.containsKey(event))
				for(QPlugin plugin : this.plugins.get(event))
					list.add(plugin.getPluginClass());
		 
		return list;
	}
}
