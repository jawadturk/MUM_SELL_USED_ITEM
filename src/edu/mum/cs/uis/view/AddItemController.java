package edu.mum.cs.uis.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class AddItemController implements EventHandler<ActionEvent> {

	private UserHomeView sourceStage;
	public AddItemController(UserHomeView aSourceStage){
		this.sourceStage = aSourceStage;
	}
	
	@Override
	public void handle(ActionEvent event) {

		new CreateItemView(sourceStage);
	}

}
