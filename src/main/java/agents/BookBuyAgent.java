package agents;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.tools.gui.ACLMessageNode;

public class BookBuyAgent extends GuiAgent{
     protected BookBuyGui gui;
	@Override
	protected void setup() {
		if(getArguments().length==1) {
			gui= (BookBuyGui)getArguments()[0];
			gui.bookbuyagent=this;
		}
		ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {

				ACLMessage aclMessage=receive();
				if(aclMessage!=null) {
					gui.logMessage(aclMessage);
					ACLMessage replay=aclMessage.createReply();
					replay.setPerformative(ACLMessage.INFORM);
					replay.setContent("Trying tobuye=>"+aclMessage.getContent());
					send(replay);
				}else {
					block();
				}
			}
		});
	}
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
