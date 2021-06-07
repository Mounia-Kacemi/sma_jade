package agents;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookBuyGui extends Application{

	protected BookBuyAgent bookbuyagent;
	protected ListView<String> listviewmessages;
    protected ObservableList<String> observablelist;

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage arg0) throws Exception {
startContainer();	
arg0.setTitle("Book Buyer Gui");
BorderPane borderpane = new BorderPane();
VBox vbox = new VBox();
vbox.setPadding(new Insets(10));
vbox.setSpacing(10);
observablelist = FXCollections.observableArrayList();
listviewmessages = new ListView<String>(observablelist);
vbox.getChildren().add(listviewmessages);
borderpane.setCenter(vbox);
Scene scene = new Scene(borderpane,480,480);
arg0.setScene(scene);
arg0.show();
	}
	private void startContainer() throws Exception{

		Runtime runtime=Runtime.instance();
		ProfileImpl profile=new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		AgentContainer agentContainer=runtime.createAgentContainer(profile);
			AgentController agentController=agentContainer.createNewAgent(
					
					"BookBuyAgent", 
					BookBuyAgent.class.getName(), 
					new Object[] {this});
	agentController.start();
	}
	public void logMessage(ACLMessage aclMessage) {
		Platform.runLater(()->{
			observablelist.add(aclMessage.getContent()+" =>  "+aclMessage.getSender().getName());
			
					
		});
		
		
	}

}
