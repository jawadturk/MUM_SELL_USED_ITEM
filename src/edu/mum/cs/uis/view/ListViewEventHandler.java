package edu.mum.cs.uis.view;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import edu.mum.cs.uis.model.Item;

public class ListViewEventHandler implements EventHandler<MouseEvent> {

	private ListView<Item> listView;
	private String mode;
	
	public ListViewEventHandler(ListView<Item> aListView, String aMode){
		this.listView = aListView;
		this.mode = aMode;
	}

	@Override
	public void handle(MouseEvent event) {
		
		if (event.getClickCount() == 2) {
			
			Item tmpItem = listView.getSelectionModel().getSelectedItem();
			
			System.out.println("clicked on item: " + tmpItem.getTitle());
			Stage stage = (Stage) listView.getScene().getWindow();
			
			
			if("ITEM_VIEW".equals(mode)) {
				new GeneralItemView(stage, tmpItem);
			}else if("ITEM_EDIT".equals(mode))  {
				new EditItemStatusView((AdminHomeView)stage, tmpItem);
			}
			
			
			
		}

	}

}
