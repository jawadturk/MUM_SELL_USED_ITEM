package edu.mum.cs.uis.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class AddItemController implements EventHandler<ActionEvent> {

	private Stage sourceStage;
	public AddItemController(Stage aSourceStage){
		this.sourceStage = aSourceStage;
	}
	
	@Override
	public void handle(ActionEvent event) {

		new CreateItemView(sourceStage);
	}

}
