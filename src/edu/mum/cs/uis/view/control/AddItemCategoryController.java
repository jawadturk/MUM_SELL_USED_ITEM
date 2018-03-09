package edu.mum.cs.uis.view.control;

import edu.mum.cs.uis.view.AdminHomeView;
import edu.mum.cs.uis.view.CreateItemCategoryView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AddItemCategoryController implements EventHandler<ActionEvent> {

	private AdminHomeView sourceStage;
	public AddItemCategoryController(AdminHomeView aSourceStage){
		this.sourceStage = aSourceStage;
	}
	
	@Override
	public void handle(ActionEvent event) {

		new CreateItemCategoryView(sourceStage);
	}


}
