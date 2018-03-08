package edu.mum.cs.uis.view;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ListViewEventHandler implements EventHandler<MouseEvent> {

	private ListView<String> listView;
	
	public ListViewEventHandler(ListView<String> aListView){
		this.listView = aListView;
	}

	@Override
	public void handle(MouseEvent event) {
		
		if (event.getClickCount() == 2) {
			System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
			Stage stage = (Stage) listView.getScene().getWindow();
				new GeneralItemView(stage);
		}

	}

}
