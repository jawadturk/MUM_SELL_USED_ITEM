package edu.mum.cs.uis.view;

import edu.mum.cs.uis.factorymethods.OperationsFactory;
import edu.mum.cs.uis.ruleset.RuleException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class EditItemStatusController implements EventHandler<ActionEvent> {

	private EditItemStatusView sourceStage;
	private String source ;
	
	public EditItemStatusController(EditItemStatusView aSourceStage, String aSource){
		this.sourceStage = aSourceStage;
		this.source = aSource;
	}
	
	@Override
	public void handle(ActionEvent event) {

		
		System.out.println("Button Pressed Source: " + source);
		
			try {			
				
				System.out.println("Data: ");
				
				System.out.println("Item ID: " + sourceStage.getItemId());
				
				boolean status = false;
				 
				
				if("APPROVE".equals(source)) {
					status = OperationsFactory.approveItem(sourceStage.getItemId());
				}else if("REJECT".equals(source)) {
					status = OperationsFactory.diApproveItem(sourceStage.getItemId());
				}
						
				
				if(!status) {
					throw new RuleException("Could not Add Item !");
				}
				
				sourceStage.updatePendingItemsList();				
				sourceStage.hide();
				
			} catch (RuleException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Edit Item Status Error");
	//			alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}			
			
		}
		
}
