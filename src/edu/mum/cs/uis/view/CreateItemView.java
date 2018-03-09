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
import edu.mum.cs.uis.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
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

public class CreateItemView extends Stage{

	// Show Created User ?
	// Show Item Status ?
	
	UserHomeView previousStage;
	
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

	public void setPreviousStage(UserHomeView previousStage) {
		this.previousStage = previousStage;
	}
	
	public CreateItemView(UserHomeView ps) {
		previousStage = ps;
    	GridPane grid = new GridPane();
    	VBox vb = new VBox();
//    	Scene scene = new Scene(grid);
    	
        VBox box = new VBox();
        Scene scene = new Scene(box, 1000, 500);
        setScene(scene);
//        setTitle("Item Screen");
        
        /*previousStage.setScene(scene);
        previousStage.setWidth(615);
        previousStage.setHeight(400);
        previousStage.setTitle("Item Screen");*/
        
        /*primaryStage.setWidth(615);
        primaryStage.setHeight(400);*/
    	
        setTitle("Create Item Screen");

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
        GridPane.setHalignment(itemDescInput, HPos.CENTER);
        grid.add(itemDescInput, 0, 3, 2, 1);

        Label itemPrice = new Label("Price:");
        grid.add(itemPrice, 0, 4);

        itemPriceInput = new TextField();
        HBox hboxPrice = new HBox();
        hboxPrice.setAlignment(Pos.CENTER_LEFT);
        hboxPrice.getChildren().add(itemPriceInput);
        grid.add(hboxPrice, 0, 5);
        
        Label itemCategory = new Label("Category:");
        grid.add(itemCategory, 0, 6);
        
        allCategories =  OperationsFactory.getAllCategories();
        
        List<String> categories = new ArrayList<>();
        
        /*categories.add("Self Care");
        categories.add("Electronics");
        categories.add("Vehicles");
        categories.add("House Hold");*/
        
        for(Category cat: allCategories) {
        	categories.add(cat.getName());
        }
        
        categoriesCombo = 
    			new ComboBox<>(FXCollections.observableList(categories));
        
  		GridPane.setHalignment(categoriesCombo, HPos.LEFT);
  		grid.add(categoriesCombo, 0, 7);

  		Label imagePath = new Label("Image Path:");
  		grid.add(imagePath, 0, 8);
  		
        imagePathVal = new Label();
        grid.add(imagePathVal, 0, 9);
        
        Button btnLoad = new Button("Browse Image ...");
        btnLoad.setOnAction(btnLoadEventListener);
        grid.add(btnLoad, 1, 9);
        
        myImageView = new ImageView();        
//        grid.add(myImageView, 0, 10);
        
  		sp.setVmax(440);
        sp.setPrefSize(115, 150);
//        sp.setFitToWidth(true);
        sp.setContent(vb); // Important 1

        vb.getChildren().add(grid);
        vb.getChildren().add(myImageView);
        
        Button addBtn = new Button("Add");
        addBtn.setOnAction(new CreateItemController(this,"ADD"));
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(new CreateItemController(this,"CANCEL"));
        
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

    EventHandler<ActionEvent> btnLoadEventListener
    = new EventHandler<ActionEvent>(){

        @Override
        public void handle(ActionEvent t) {
            FileChooser fileChooser = new FileChooser();
            
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
             
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
                      
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                imagePathVal.setText(file.getPath());
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                myImageView.setImage(image);
                
                myImageView.setFitWidth(900);
                myImageView.setPreserveRatio(true);
                myImageView.setSmooth(true);
                myImageView.setCache(true);
                
            } catch (IOException ex) {
                Logger.getLogger(CreateItemView.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };
    
    public String getItemName() {
    	
    	String itemName = null;
    	if(itemNameInput != null) {
    		itemName = itemNameInput.getText();
    	}
    	return itemName;
    	
    }
    
    public String getItemDesc() {
    	
    	String itemDesc = null;
    	if(itemDescInput != null) {
    		itemDesc = itemDescInput.getText();
    	}
    	return itemDesc;
    	
    }

    public String getItemPrice() {
    	
    	String itemPrice = null;
    	if(itemPriceInput != null) {
    		itemPrice = itemPriceInput.getText();
    	}
    	return itemPrice;
    	
    }
    
    public Category getSelectedCategory() {
    	
    	if(categoriesCombo != null && allCategories != null && allCategories.size() > 0) {
    		String value = categoriesCombo.getValue();
    		
    		for(Category cat: allCategories) {
    			if(cat != null && value != null && value.equals(cat.getName())) {
    				return cat;
    			}
    		}
    		
    	}
    	
    	
    	return null;
    }
    
    public edu.mum.cs.uis.model.Image getUploadedImage(){
    	
    	if(imagePathVal != null) {
    		String imagePath = imagePathVal.getText();
    		if(imagePath != null && !"".equals(imagePath) && imagePath != "") {
    			return new edu.mum.cs.uis.model.Image(0, imagePath);
    		}
    	}
    	
    	
    	return null;
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

	public void updateUserItemsList() {
        previousStage.updateUserItemsList();
	}
    
}
