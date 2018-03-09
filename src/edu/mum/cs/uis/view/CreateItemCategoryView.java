package edu.mum.cs.uis.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import edu.mum.cs.uis.factorymethods.OperationsFactory;
import edu.mum.cs.uis.model.Category;
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

public class CreateItemCategoryView  extends Stage{

	// Show Created User ?
	// Show Item Status ?
	
	AdminHomeView previousStage;
	
	List<Category> allCategories;
	ComboBox<String> categoriesCombo;
	TextField itemCategoryNameInput;
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
	
	public CreateItemCategoryView(AdminHomeView ps) {
		previousStage = ps;
    	GridPane grid = new GridPane();
    	VBox vb = new VBox();
//    	Scene scene = new Scene(grid);
    	
        VBox box = new VBox();
        Scene scene = new Scene(box, 350, 150);
        setScene(scene);
    	
        setTitle("Create Item Category");

    	ScrollPane sp = new ScrollPane();
    	box.getChildren().addAll(sp); // Important 1
        VBox.setVgrow(sp, Priority.ALWAYS);
    	
        
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.setGridLinesVisible(false) ;

        Label itemCategoryName = new Label("Name:");
        grid.add(itemCategoryName, 0, 0);

        itemCategoryNameInput = new TextField();
        HBox hbBtn = new HBox();
        hbBtn.setAlignment(Pos.CENTER_LEFT);
        hbBtn.getChildren().add(itemCategoryNameInput);
        grid.add(hbBtn, 0, 1);

 
        
  		sp.setVmax(440);
        sp.setPrefSize(115, 150);
        sp.setContent(vb); // Important 1

        vb.getChildren().add(grid);
        
        Button addBtn = new Button("Add");
        addBtn.setOnAction(new CreateItemCategoryController(this,"ADD"));
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(new CreateItemCategoryController(this,"CANCEL"));
        
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
//        previousStage.hide();
		
	}

    
    public String getItemCategoryName() {
    	
    	String itemCategoryName = null;
    	if(itemCategoryNameInput != null) {
    		itemCategoryName = itemCategoryNameInput.getText();
    	}
    	return itemCategoryName;
    	
    }
    
    public int getUserId() {
    	
    	int id = 0;
		try {
			id = LoggedinSession.getInstance().getLoggedinUser().getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return id;
    	
    }

	public void updateItemCategoriesList() {
        previousStage.updateItemCategoriesList();
	}
    
}
