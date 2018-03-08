package edu.mum.cs.uis.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserHomeView extends Stage{

	Stage previousStage;
	
    private final Image IMAGE_RUBY  = new Image(getClass().getResource("Ruby_logo_64x64.png").toExternalForm());
    private final Image IMAGE_APPLE  = new Image(getClass().getResource("apple.png").toExternalForm());
    private final Image IMAGE_VISTA  = new Image(getClass().getResource("windows_64x64.png").toExternalForm());
    private final Image IMAGE_TWITTER = new Image(getClass().getResource("twitter-bird.png").toExternalForm());

    private Image[] listOfImages = {IMAGE_RUBY, IMAGE_APPLE, IMAGE_VISTA, IMAGE_TWITTER};

    public Stage getPreviousStage() {
		return previousStage;
	}

	public void setPreviousStage(Stage previousStage) {
		this.previousStage = previousStage;
	}
	
	
	public UserHomeView(Stage ps) {
		previousStage = ps;
		
        setTitle("User Home Screen");
        Group root = new Group();
        Scene scene = new Scene(root, 650, 400, Color.WHITE);

        TabPane tabPane = new TabPane();
        

        BorderPane borderPane = new BorderPane();
        /*for (int i = 0; i < 5; i++) {*/
            Tab tab = new Tab();
            tab.setText("My Items For Sale");
            tab.setClosable(false);
            VBox vvbox = new VBox();
//            hbox.getChildren().add(new Label("Tab" + i));
            
        	
            ListView<String> listView = new ListView<String>();
            ObservableList<String> items =FXCollections.observableArrayList (
                    "RUBY", "APPLE", "VISTA", "TWITTER");
            listView.setItems(items);
            listView.setCellFactory(param -> new ListCell<String>() {
                private ImageView imageView = new ImageView();
                @Override
                public void updateItem(String name, boolean empty) {
                    super.updateItem(name, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if(name.equals("RUBY"))
                            imageView.setImage(listOfImages[0]);
                        else if(name.equals("APPLE"))
                            imageView.setImage(listOfImages[1]);
                        else if(name.equals("VISTA"))
                            imageView.setImage(listOfImages[2]);
                        else if(name.equals("TWITTER"))
                            imageView.setImage(listOfImages[3]);
                        setText(name);
                        setGraphic(imageView);
                        
                    }
                }
            });
            
            listView.setOnMousePressed(new ListViewEventHandler(listView));
            listView.prefHeightProperty().bind(scene.heightProperty());
            listView.prefWidthProperty().bind(scene.widthProperty());
            
            VBox box = new VBox(listView);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setFillWidth(true);
            /*Scene scene = new Scene(box, 200, 200);
            
            primaryStage.setWidth(615);
            primaryStage.setHeight(400);
            primaryStage.setScene(scene);
            primaryStage.show();*/

            Button addItemBtn = new Button("Add Item for Sale");
            addItemBtn.setOnAction(new AddItemController(ps));
            HBox addItemBox = new HBox();
            addItemBox.setAlignment(Pos.CENTER_RIGHT);
            addItemBox.setPadding(new Insets(10,10,10,10));
            addItemBox.getChildren().add(addItemBtn);
            
            Label itemListLbl = new Label("My Items List");
            itemListLbl.setStyle("-fx-text-inner-color: black;-fx-font-weight: bold;");
            HBox itemListLblBox = new HBox();
            itemListLblBox.setAlignment(Pos.CENTER_LEFT);
            itemListLblBox.setPadding(new Insets(0,0,10,10));
            itemListLblBox.getChildren().add(itemListLbl);
            
            vvbox.getChildren().add(addItemBox);
            vvbox.getChildren().add(itemListLblBox);
            vvbox.getChildren().add(box);
            
            
            vvbox.setAlignment(Pos.CENTER_LEFT);
            vvbox.setFillWidth(true);
            tab.setContent(vvbox);
            tabPane.getTabs().add(tab);
        /*}*/
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
        String welcomeStr = "Welcome";
        try {
        	welcomeStr = "Welcome " 
            		+ LoggedinSession.getLoggedinUser().getLastName() 
            		+ ", " + LoggedinSession.getLoggedinUser().getFirstName();
        }catch (Exception e) {
			e.printStackTrace();
		}
        
        Label welcomeLbl = new Label(welcomeStr);
        welcomeLbl.setStyle("-fx-text-inner-color: blue;-fx-font-weight: bold;");
        HBox welcomeLblBox = new HBox();
        welcomeLblBox.setAlignment(Pos.CENTER);
        welcomeLblBox.setPadding(new Insets(5,0,5,0));
        welcomeLblBox.getChildren().add(welcomeLbl);
        
        borderPane.setTop(welcomeLblBox);
        borderPane.setCenter(tabPane);
        
//        borderPane.setTop(addItemBox);
        
        
        root.getChildren().add(borderPane);
        
//        Tab tab1 = tabPane.getTabs().get(1);
//        HBox hboxTab1 = (HBox)tab1.getContent();
        
//        hboxTab1.getChildren().add(new Label("Test Label on Tab 1"));

        /***/
        
        Image image = null;
		try {
			image = new Image(new FileInputStream("C:\\Users\\Shadi\\Desktop\\shadi_facebook.jpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        ImageView iv2 = new ImageView();
        iv2.setImage(image);
        iv2.setFitWidth(250);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
        
//        hboxTab1.getChildren().add(iv2);
        
        /***/
        
        //gridpane.add(pictureRegion, 1, 1);
        
        setScene(scene);
        show();
        previousStage.hide();
        
	}
		
}
