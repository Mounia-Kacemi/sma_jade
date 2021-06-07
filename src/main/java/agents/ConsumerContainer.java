package agents;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
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


public class ConsumerContainer extends Application{
	protected ConsumerAgent consumeragent ;
	protected ObservableList<String> observablelist ;

	public static void main(String[] args) throws Exception {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Consumer Container");
		startContainer();
		BorderPane borderpane = new BorderPane();
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(10);
		Label labelBookname = new Label("Book Name:");
		TextField textfieldBookname = new TextField();
		Button buttonOk = new Button("Ok");
		hbox.getChildren().addAll(labelBookname , textfieldBookname,buttonOk);
		borderpane.setTop(hbox);
	observablelist = FXCollections.observableArrayList();
		ListView<String> listviewmessages = new ListView<String>(observablelist);
        VBox hbox2=new VBox(); 
		hbox2.setPadding(new Insets(10));
		hbox2.setSpacing(10);
		hbox2.getChildren().add(listviewmessages);
		borderpane.setCenter(hbox2);

		buttonOk.setOnAction(evt->{
			String bookName=textfieldBookname.getText();
			//observablelist.add(bookName);	
			GuiEvent event = new GuiEvent(this,1);
			event.addParameter(bookName);
			consumeragent.onGuiEvent(event);
		});
		Scene scene = new Scene(borderpane,480,480);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	public void startContainer() throws Exception {
	Runtime runtime = Runtime.instance();
		ProfileImpl profileImpl = new ProfileImpl();
		profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
		AgentContainer container = runtime.createAgentContainer(profileImpl);
		AgentController agentcontroller = container
				.createNewAgent("Consumer","agents.ConsumerAgent", new Object[] {this});
		container.start();
		agentcontroller.start();
	}


	public void logMessage(ACLMessage aclMessage) {
		Platform.runLater(()->{
			observablelist.add(aclMessage.getContent()+" => "+aclMessage.getSender().getName());
			
					
		});
		
		
	}
	
	
}