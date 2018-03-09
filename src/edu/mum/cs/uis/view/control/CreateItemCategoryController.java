package edu.mum.cs.uis.view.control;

import edu.mum.cs.uis.factorymethods.OperationsFactory;
import edu.mum.cs.uis.ruleset.RuleException;
import edu.mum.cs.uis.view.CreateItemCategoryView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CreateItemCategoryController  implements EventHandler<ActionEvent> {

	private CreateItemCategoryView sourceStage;
	private String source ;
	
	public CreateItemCategoryController(CreateItemCategoryView aSourceStage, String aSource){
		this.sourceStage = aSourceStage;
		this.source = aSource;
	}
	
	@Override
	public void handle(ActionEvent event) {

		
		System.out.println("Button Pressed Source: " + source);
		if("ADD".equals(source)) {
			try {			
				
				System.out.println("Data: ");
				
				System.out.println("Item Category Name: " + sourceStage.getItemCategoryName());
				
				
				OperationsFactory.addCategory(sourceStage.getItemCategoryName());
				
				/*if(!status) {
					throw new RuleException("Could not Add Item Category !");
				}*/
				
				sourceStage.updateItemCategoriesList();				
				sourceStage.hide();
				
			} catch (RuleException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Add Item Category Error");
	//			alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}			
			
		}else if("CANCEL".equals(source)) {
			sourceStage.hide();
		}
		
		
	}

}
