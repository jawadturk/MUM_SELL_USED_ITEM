package edu.mum.cs.uis.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

public class GeneralItemView extends Stage{

	Stage previousStage;

    public Stage getPreviousStage() {
		return previousStage;
	}

	public void setPreviousStage(Stage previousStage) {
		this.previousStage = previousStage;
	}
	
	public GeneralItemView(Stage ps) {
		
		previousStage = ps;
    	GridPane grid = new GridPane();
    	VBox vb = new VBox();
    	
        VBox box = new VBox();
        Scene scene = new Scene(box, 1000, 500);
        setScene(scene);

        setTitle("View Item");

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

        TextField itemNameInput = new TextField();
        itemNameInput.setDisable(true);
        itemNameInput.setText("Test Item Name");        
//        itemNameInput.setStyle("-fx-text-inner-color: blue;-fx-font-weight: bold;");
        
        HBox hbBtn = new HBox();
        hbBtn.setAlignment(Pos.CENTER_LEFT);
        hbBtn.getChildren().add(itemNameInput);
        grid.add(hbBtn, 0, 1);

        Label itemDesc = new Label("Description:");
        grid.add(itemDesc, 0, 2);

        TextArea itemDescInput = new TextArea();
        itemDescInput.setPrefRowCount(5);
        itemDescInput.setPrefColumnCount(100);
        itemDescInput.setWrapText(true);
        itemDescInput.setPrefWidth(350);
        
        itemDescInput.setDisable(true);
        itemDescInput.setText("Test Item Description");        
//        itemDescInput.setStyle("-fx-text-inner-color: blue;-fx-font-weight: bold;");
        
        GridPane.setHalignment(itemDescInput, HPos.CENTER);
        grid.add(itemDescInput, 0, 3, 2, 1);

        Label itemPrice = new Label("Price:");
        grid.add(itemPrice, 0, 4);

        TextField itemPriceInput = new TextField();
        itemPriceInput.setDisable(true);
        itemPriceInput.setText("100.55");        
//        itemPriceInput.setStyle("-fx-text-inner-color: blue;-fx-font-weight: bold;");
        
        HBox hboxPrice = new HBox();
        hboxPrice.setAlignment(Pos.CENTER_LEFT);
        hboxPrice.getChildren().add(itemPriceInput);
        grid.add(hboxPrice, 0, 5);
        
        Label itemCategory = new Label("Category:");
        grid.add(itemCategory, 0, 6);
        
        List<String> categories = new ArrayList<>();
        categories.add("Self Care");
        categories.add("Electronics");
        categories.add("Vehicles");
        categories.add("House Hold");
        ComboBox<String> categoriesCombo = 
    			new ComboBox<>(FXCollections.observableList(categories));
  		GridPane.setHalignment(categoriesCombo, HPos.LEFT);
  		
  		categoriesCombo.setValue("Self Care");
  		categoriesCombo.setDisable(true);
//  		categoriesCombo.setStyle("-fx-text-inner-color: blue;-fx-font-weight: bold;");
        
  		grid.add(categoriesCombo, 0, 7);
//        
  		
  		Image testImage =
                new Image(getClass().getResourceAsStream(
                    "flower.jpg")
                );
  		
  		ImageView myImageView = new ImageView();  
  		
        myImageView.setImage(testImage);
        
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
        
        
        
        box.getChildren().add(vb);
        
        
        show();
//        previousStage.hide();
				
		
		
	}
	
}
