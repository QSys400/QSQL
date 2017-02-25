/*******************************************************************************
 * QSYS400.COM
 * Written by : Sumit goyal
 * 2016
 *******************************************************************************/
package qJob.qlocalJob.qServers;

 

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 

public class QServer implements Cloneable{
	
	private SimpleIntegerProperty serverID = new SimpleIntegerProperty();
	private SimpleStringProperty serverName = new SimpleStringProperty();
	private SimpleStringProperty serverIp = new SimpleStringProperty();
	private SimpleStringProperty userName = new SimpleStringProperty();
	private SimpleStringProperty password = new SimpleStringProperty();
	private SimpleStringProperty naming = new SimpleStringProperty("system");
	private ObservableList<String> libl = FXCollections.<String>observableArrayList();
	private boolean changed = false;
	private boolean deleted = false;
	
	
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public SimpleIntegerProperty serverIDProperty()
	{
		return this.serverID;
	}
	
	public SimpleStringProperty serverNameProperty()
	{
		return this.serverName;
	}
	public SimpleStringProperty serverIpProperty()
	{
		return this.serverIp;
	}
	public SimpleStringProperty userNameProperty()
	{
		return this.userName;
	}
	public SimpleStringProperty passwordProperty()
	{
		return this.password;
	}
	public SimpleStringProperty namingProperty()
	{
		return this.naming;
	}
 
	public String getNaming() {
		return naming.get();
		
	}
	public void setNaming(String naming) {
		this.naming.set(naming);
	}
	public ObservableList<String> getLibl() {


		return libl;
	}
	public void setLibl(ObservableList<String> libl) {
		this.libl = libl;
	}
	public int getServerID() {
		return serverID.get();
	}
	public void setServerID(int serverID) {
		this.serverID.set(serverID);
	}
	public String getServerName() {
		return serverName.get()==null?"":serverName.get();
	}
	public void setServerName(String serverName) {
		this.serverName.set(serverName);
	}
	public String getServerIp() {
		return serverIp.get()==null?"":serverIp.get();
	}
	public void setServerIp(String serverIp) {
		this.serverIp.set(serverIp);
	}
	public String getUserName() {
		return userName.get()==null?"":userName.get();
	}
	public void setUserName(String userName) {
		this.userName.set(userName);
	}
	public String getPassword() {
		return password.get()==null?"":password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	
	@Override
	public String toString()
	{
		return (this.serverID + " :: " + this.serverName + " :: " + this.serverIp + " :: " + this.userName + " :: " + this.password);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof QServer)
		{
			QServer x = (QServer) obj;
			if(this.getServerID() == x.getServerID()) return true;
		}
		return false;
	}
	
	   @Override       
	   public Object clone()
	   {
		    QServer x = new QServer();
		    x.setServerID(this.getServerID());
		    x.setServerName(this.getServerName());
		    x.setServerIp(this.getServerIp());
		    x.setUserName(this.getUserName());
		    x.setPassword(this.getPassword());
		    x.setNaming(this.getNaming());
		    
		    x.getLibl().clear();
		    x.getLibl().addAll(this.getLibl());
		   
		    return x;
	   }
	   
 
}
