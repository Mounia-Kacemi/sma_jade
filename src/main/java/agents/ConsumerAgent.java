package agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.introspection.AddedBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsumerAgent extends GuiAgent{
	protected ConsumerContainer consumerContainer;
    @Override
    protected void setup(){
        String bookname=null;
        if(this.getArguments().length==1){
        	consumerContainer=(ConsumerContainer)getArguments()[0];
        	consumerContainer.consumeragent=this;
        	//bookname=(String)this.getArguments()[0];
        }
        
        System.out.println("Initialisation de l'agent"+this.getAID().getName());
        System.out.println("I'm trying to buy the book " +bookname);
  
ParallelBehaviour paralleBehaviour=new ParallelBehaviour();
        addBehaviour(paralleBehaviour);
/*addBehaviour(new Behaviour(){
            private int counter=0;
        @Override
        public boolean done(){
            
            return (counter==10);
        }
        
        @Override
        public void action(){
         System.out.println("------------");
            System.out.println("Step"+counter);
         System.out.println("------------");

        ++counter;
        }
        });
    }
  */      
        paralleBehaviour.addSubBehaviour(new OneShotBehaviour(){
            @Override
            public void action() {
                System.out.println("One shot Behaviour");
            }
        
        });
       /* addBehaviour(new CyclicBehaviour(){
         private int counter=0;
            @Override
            public void action() {
                System.out.println("Counter =>"+counter);
                 ++counter;
            }
        
        }); }*/
   /*   addBehaviour(new TickerBehaviour(this,1000) {

			@Override
			protected void onTick() {
System.out.println("Ticc");	
System.out.println(myAgent.getAID().getLocalName());
			}
        	
        });}*/
       
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/mm/yyyy:hh:mm");
        Date date=null;
        try {
            date = dateFormat.parse("7/05/2021:7:23");
        } catch (ParseException ex) {
            Logger.getLogger(ConsumerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        paralleBehaviour.addSubBehaviour(new WakerBehaviour(this,date){
        @Override
        protected void onWake(){
            System.out.println("Weker Behaviour....");
        }
        });
 paralleBehaviour.addSubBehaviour(new CyclicBehaviour() {
        
@Override
public void action() {
System.out.println("Cyclic behavior");
    MessageTemplate messageTemplate=
            MessageTemplate.and(
                    MessageTemplate.MatchPerformative(ACLMessage.CFP),
                    MessageTemplate.MatchLanguage("fr"));
    
ACLMessage aclMessage=receive();
if(aclMessage!=null){
System.out.println("Sender :"+aclMessage.getSender().getName());
System.out.println("Content :"+aclMessage.getContent());
System.out.println("SpeechAct :"+ACLMessage.getPerformative(aclMessage.getPerformative()));
/*
ACLMessage reply=new ACLMessage(ACLMessage.CONFIRM);
reply.addReceiver(aclMessage.getSender());
reply.setContent("pice=900");
send(reply);*/
consumerContainer.logMessage(aclMessage);
}
else{
    System.out.println("Block.........");
block();
}} });
       
    }
    
        @Override
    protected void beforeMove(){
        try {
            System.out.println("Before Migration from"+
                    this.getContainerController().getContainerName());
        } catch (ControllerException ex) {
            ex.printStackTrace();
        }
        
    }
    @Override
    protected void afterMove(){
        try {
            System.out.println("After Migration to"+
                    this.getContainerController().getContainerName());
        } catch (ControllerException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    protected void takeDown(){
        System.out.println("I'm going to die....");
    }

	@Override
	protected void onGuiEvent(GuiEvent evt) {
		if(evt.getType()==1)			{
			String bookName = evt.getParameter(0).toString();
			System.out.println("agent =>"+getAID()+"=>"+bookName);
		ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
		aclMessage.setContent(bookName);
		aclMessage.addReceiver(new AID("BookBuyAgent",AID.ISLOCALNAME));
	    send(aclMessage);		
	}}

}
