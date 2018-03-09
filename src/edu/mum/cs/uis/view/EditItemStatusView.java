package edu.mum.cs.uis.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import edu.mum.cs.uis.factorymethods.OperationsFactory;
import edu.mum.cs.uis.model.Category;
import edu.mum.cs.uis.model.Item;
import edu.mum.cs.uis.view.control.EditItemStatusController;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditItemStatusView extends Stage{
	
	AdminHomeView previousStage;
	Item selectedItem;
	private final Image IMAGE_DEFAULT = new Image(getClass().getResource("default_item_image.png").toExternalForm());
	
	List<Category> allCategories;
	ComboBox<String> categoriesCombo;
	TextField itemNameInput;
	TextArea itemDescInput;
	TextField itemPriceInput;
	
	ImageView myImageView;
	Label imagePathVal;
	
    public Stage getPreviousStage() {
		return previousStage;
	}

	public void setPreviousStage(AdminHomeView previousStage) {
		this.previousStage = previousStage;
	}
	
	public EditItemStatusView(AdminHomeView ps, Item aSelectedItem) {
		previousStage = ps;
		selectedItem = aSelectedItem;
		
		GridPane grid = new GridPane();
    	VBox vb = new VBox();
    	
        VBox box = new VBox();
        Scene scene = new Scene(box, 1000, 500);
        setScene(scene);

        setTitle("Edit Item Status");

    	ScrollPane sp = new ScrollPane();
    	box.getChildren().addAll(sp); // Important 1
        VBox.setVgrow(sp, Priority.ALWAYS);
    	
        
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.setGridLinesVisible(false) ;

        Label itemName = new Label("Name:");
        grid.add(itemName, 0, 0);

        itemNameInput = new TextField();
        itemNameInput.setDisable(true);
        itemNameInput.setText(selectedItem.getTitle());    
        HBox hbBtn = new HBox();
        hbBtn.setAlignment(Pos.CENTER_LEFT);
        hbBtn.getChildren().add(itemNameInput);
        grid.add(hbBtn, 0, 1);

        Label itemDesc = new Label("Description:");
        grid.add(itemDesc, 0, 2);

        itemDescInput = new TextArea();
        itemDescInput.setPrefRowCount(5);
        itemDescInput.setPrefColumnCount(100);
        itemDescInput.setWrapText(true);
        itemDescInput.setPrefWidth(350);
        
        itemDescInput.setDisable(true);
        itemDescInput.setText(selectedItem.getDescription());  
        
        GridPane.setHalignment(itemDescInput, HPos.CENTER);
        grid.add(itemDescInput, 0, 3, 2, 1);

        Label itemPrice = new Label("Price:");
        grid.add(itemPrice, 0, 4);

        itemPriceInput = new TextField();
        itemPriceInput.setDisable(true);
        itemPriceInput.setText(selectedItem.getPrice()+"");    
        
        
        HBox hboxPrice = new HBox();
        hboxPrice.setAlignment(Pos.CENTER_LEFT);
        hboxPrice.getChildren().add(itemPriceInput);
        grid.add(hboxPrice, 0, 5);
        
        Label itemCategory = new Label("Category:");
        grid.add(itemCategory, 0, 6);
        
        List<String> categories = new ArrayList<>();
        categories.add(selectedItem.getCat().getName());
        /*categories.add("Electronics");
        categories.add("Vehicles");
        categories.add("House Hold");*/
        ComboBox<String> categoriesCombo = 
    			new ComboBox<>(FXCollections.observableList(categories));
  		GridPane.setHalignment(categoriesCombo, HPos.LEFT);
  		
  		categoriesCombo.setValue(selectedItem.getCat().getName());
  		categoriesCombo.setDisable(true);
//  		categoriesCombo.setStyle("-fx-text-inner-color: blue;-fx-font-weight: bold;");
        
  		grid.add(categoriesCombo, 0, 7);

  		
  		Image imageTmp = null;
  		try {
    		String imagePath = selectedItem.getImg().getPath();
			imageTmp = new Image(new FileInputStream(imagePath));
		} catch (FileNotFoundException e) {
			imageTmp = IMAGE_DEFAULT;
		}

  		ImageView myImageView = new ImageView();  
  		
        myImageView.setImage(imageTmp);
        
        myImageView.setFitWidth(900);
        myImageView.setPreserveRatio(true);
        myImageView.setSmooth(true);
        myImageView.setCache(true);
        
  		sp.setVmax(440);
        sp.setPrefSize(115, 150);
//        sp.setFitToWidth(true);
        sp.setContent(vb); // Important 1

        vb.getChildren().add(grid);
        vb.getChildren().add(myImageView);
        
        Button addBtn = new Button("Approve");
        addBtn.setOnAction(new EditItemStatusController(this,"APPROVE"));
        
        Button cancelBtn = new Button("Reject");
        cancelBtn.setOnAction(new EditItemStatusController(this,"REJECT"));
        
        HBox buttonsBarBox = new HBox(10);
        buttonsBarBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsBarBox.setPadding(new Insets(5,5,5,5));
        
        buttonsBarBox.getChildren().add(addBtn);
        buttonsBarBox.getChildren().add(cancelBtn);
        
        box.getChildren().add(vb);
        box.setFillWidth(true);        
        box.prefHeightProperty().bind(scene.heightProperty());
        box.prefWidthProperty().bind(scene.widthProperty());
        box.getChildren().add(buttonsBarBox);
        
        
        show();
	}

	public int getItemId() {
		if(selectedItem != null) {
			return selectedItem.getId();
		}
		return 0;
	}
	
	public void updatePendingItemsList() {
        previousStage.updatePendingItemsList();
	}


}
