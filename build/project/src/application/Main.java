/*******************************************************************************
 * QSYS400.COM
 * Written by : Sumit goyal
 * 2016
 *******************************************************************************/
package application;
 
 
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import qJob.QJob;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
 	
	
	@Override
	public void start(Stage primaryStage) {
		final MainController mainController;
		
		 

		
		try {
			FXMLLoader loader = new FXMLLoader( getClass().getResource("Main.fxml"));
	 
			BorderPane root = (BorderPane)loader.load();
			//  mainController =loader.<MainController>getController();
			
			  EventHandler<WindowEvent> stageEvent =   new EventHandler<WindowEvent>() {
															public void handle(WindowEvent we) { 
														 
																	QJob.getCurrentJob().shutDown();
															 
															} };
				primaryStage.setOnCloseRequest(stageEvent); 
				primaryStage.setOnHidden(stageEvent);
				
				 
			  
			  
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("[Q]Sql");
			primaryStage.getIcons().add(new Image("file:resources/q.png"));
			primaryStage.setMaximized(true);
			primaryStage.show();
			
	
			
			
		} catch(Exception e) {
			e.printStackTrace();
			 
			primaryStage.close();
			QJob.getCurrentJob().shutDown();
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}


