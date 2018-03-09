package edu.mum.cs.uis.view.control;

import edu.mum.cs.uis.factorymethods.OperationsFactory;
import edu.mum.cs.uis.ruleset.RuleException;
import edu.mum.cs.uis.view.CreateItemView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CreateItemController implements EventHandler<ActionEvent> {

	private CreateItemView sourceStage;
	private String source ;
	
	public CreateItemController(CreateItemView aSourceStage, String aSource){
		this.sourceStage = aSourceStage;
		this.source = aSource;
	}
	
	@Override
	public void handle(ActionEvent event) {

		
		System.out.println("Button Pressed Source: " + source);
		if("ADD".equals(source)) {
			try {			
				
				/*System.out.println("Data: ");
				
				System.out.println("Item Name: " + sourceStage.getItemName());
				System.out.println("Item Desc: " + sourceStage.getItemDesc());
				System.out.println("Item Price: " + sourceStage.getItemPrice()); 
				System.out.println("Item Image: " + sourceStage.getUploadedImage().getPath()); 
				System.out.println("Item Category: " + sourceStage.getSelectedCategory());
				System.out.println("Item User ID: " + sourceStage.getUserId());*/
				
				
				boolean status = OperationsFactory.addItem(
							sourceStage.getItemName(), 
							sourceStage.getItemDesc(), 
							sourceStage.getItemPrice(), 
							sourceStage.getUploadedImage(), 
							sourceStage.getSelectedCategory(), 
							sourceStage.getUserId());
				
				if(!status) {
					throw new RuleException("Could not Add Item !");
				}
				
				sourceStage.updateUserItemsList();				
				sourceStage.hide();
				
			} catch (RuleException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Add Item Error");
	//			alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}			
			
		}else if("CANCEL".equals(source)) {
			sourceStage.hide();
		}
		
		
	}

}
