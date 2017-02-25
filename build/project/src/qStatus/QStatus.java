/*******************************************************************************
 * QSYS400.COM
 * Written by : Sumit goyal
 * 2016
 *******************************************************************************/
package qStatus;

 
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class QStatus {

	protected QStatus()
	{}
	
	
	private ActionStatus status = ActionStatus.NoStatus;
	private StringProperty message;
	private StringProperty longMessage;
	private String action;
	
	
	public ActionStatus getStatus() {
		return status;
	}
	public void setStatus(ActionStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return messageProperty().getValue();
	}
	public void setMessage(String message) {
		this.messageProperty().setValue(message);  
	}
	
	public StringProperty messageProperty() {
		if(message==null)
		{
			message = new SimpleStringProperty();
		}
	 return message;
	}
	
	
 
	
	
	
	public String getLongMessage() {
		return this.longMessageProperty().getValue();
	}
	public void setLongMessage(String longMessage) {
		this.longMessageProperty().setValue(longMessage);
	}
	
	public StringProperty longMessageProperty() {
		if(longMessage==null)
		{
			longMessage = new SimpleStringProperty();
		}
	 return longMessage;
	}
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public void clear()
	{
		this.setStatus(ActionStatus.NoStatus);
		this.setAction("");
		this.setMessage("");
		
		
	}
	public void update(ActionStatus status, String action, String message)
	{
		this.setStatus(status);
		this.setAction(action);
		this.setMessage(message);
	}
	
	public void update(ActionStatus status, String action, String message, boolean containsLongMessage)
	{
		String tempLongMessage= message;
		String tempMessage = message;
		if(containsLongMessage)
		{
			int lastIndex = message.indexOf(".");
			if(lastIndex > 0 )  tempMessage = message.substring(0,lastIndex+1);
		}
		this.update(status, action, tempMessage,tempLongMessage);
	}
	
	public void update(ActionStatus status, String action, String message, String longMessage)
	{
		 this.update(status, action, message);
		 this.setLongMessage(longMessage);
	}
	
}
