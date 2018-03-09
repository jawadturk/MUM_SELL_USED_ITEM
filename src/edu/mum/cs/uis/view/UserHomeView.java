package edu.mum.cs.uis.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import edu.mum.cs.uis.factorymethods.OperationsFactory;
import edu.mum.cs.uis.model.User;
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
import edu.mum.cs.uis.model.Item;

public class UserHomeView extends Stage{

	Stage previousStage;
	
    /*private final Image IMAGE_RUBY  = new Image(getClass().getResource("Ruby_logo_64x64.png").toExternalForm());
    private final Image IMAGE_APPLE  = new Image(getClass().getResource("apple.png").toExternalForm());
    private final Image IMAGE_VISTA  = new Image(getClass().getResource("windows_64x64.png").toExternalForm());
    private final Image IMAGE_TWITTER = new Image(getClass().getResource("twitter-bird.png").toExternalForm());*/

	private final Image IMAGE_DEFAULT = new Image(getClass().getResource("default_item_image.png").toExternalForm());
	
	ListView<Item> userItemListView, allApprovedItemListView;
	List<Item> allUserItems, allApprovedItems;
	int userId;
	
    /*private Image[] listOfImages = {IMAGE_RUBY, IMAGE_APPLE, IMAGE_VISTA, IMAGE_TWITTER};*/

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

            Tab userItemsTab = new Tab();
            userItemsTab.setText("My Items For Sale");
            userItemsTab.setClosable(false);
            VBox userItemsTabBoxLayout = new VBox();
            
            userId = 0;
            
            try {
				userId = LoggedinSession.getInstance().getLoggedinUser().getId();
				System.out.println("Loggedin User ID: " + userId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            
            allUserItems = OperationsFactory.getItemsByUserId(userId);
            
            userItemListView = new ListView<Item>();
            
            ObservableList<Item> userItemListViewList = FXCollections.observableArrayList(allUserItems);
            
            userItemListView.setItems(userItemListViewList);
            userItemListView.setCellFactory(param -> new ListCell<Item>() {
                private ImageView imageView = new ImageView();
                @Override
                public void updateItem(Item itemVal, boolean empty) {
                    super.updateItem(itemVal, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {

                    	
                    	Image imageTmp = null; 
                    	
                    	try {
                    		String imagePath = itemVal.getImg().getPath();
							imageTmp = new Image(new FileInputStream(imagePath));
						} catch (FileNotFoundException e) {
							imageTmp = IMAGE_DEFAULT;
						}
                    	imageView.setImage(imageTmp);
                    	imageView.setFitWidth(64);
                    	imageView.setFitHeight(64);
//                    	imageView.setPreserveRatio(true);
                    	imageView.setSmooth(true);
                    	imageView.setCache(true);
                    	
                    	setText(itemVal.getTitle());
                        setGraphic(imageView);
                    }
                }
            });
            
            
            
            
            userItemListView.setOnMousePressed(new ListViewEventHandler(userItemListView, "ITEM_VIEW"));
            userItemListView.prefHeightProperty().bind(scene.heightProperty());
            userItemListView.prefWidthProperty().bind(scene.widthProperty());
            
            VBox box = new VBox(userItemListView);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setFillWidth(true);


            Button addItemBtn = new Button("Add Item for Sale");
            addItemBtn.setOnAction(new AddItemController(this));
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
            
            userItemsTabBoxLayout.getChildren().add(addItemBox);
            userItemsTabBoxLayout.getChildren().add(itemListLblBox);
            userItemsTabBoxLayout.getChildren().add(box);
            
            
            userItemsTabBoxLayout.setAlignment(Pos.CENTER_LEFT);
            userItemsTabBoxLayout.setFillWidth(true);
            userItemsTab.setContent(userItemsTabBoxLayout);
            tabPane.getTabs().add(userItemsTab);
            
            /***** Start Second Tab for All Items ******/
            
            Tab allItemsTab = new Tab();
            allItemsTab.setText("All Items For Sale");
            allItemsTab.setClosable(false);
            
            VBox allItemsTabBoxLayout = new VBox();
            
            allApprovedItems = OperationsFactory.getAllApprovedItems();
            
            allApprovedItemListView = new ListView<Item>();
            
            ObservableList<Item> allApprovedItemListViewList = FXCollections.observableArrayList(allApprovedItems);
            
            allApprovedItemListView.setItems(allApprovedItemListViewList);
            allApprovedItemListView.setCellFactory(param -> new ListCell<Item>() {
                private ImageView imageView = new ImageView();
                @Override
                public void updateItem(Item itemVal, boolean empty) {
                    super.updateItem(itemVal, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {                    	
                    	Image imageTmp = null; 
                    	
                    	try {
                    		String imagePath = itemVal.getImg().getPath();
							imageTmp = new Image(new FileInputStream(imagePath));
						} catch (FileNotFoundException e) {
							imageTmp = IMAGE_DEFAULT;
						}
                    	imageView.setImage(imageTmp);
                    	imageView.setFitWidth(64);
                    	imageView.setFitHeight(64);
//                    	imageView.setPreserveRatio(true);
                    	imageView.setSmooth(true);
                    	imageView.setCache(true);
                    	
                    	setText(itemVal.getTitle());
                        setGraphic(imageView);
                    }
                }
            });
            

            allApprovedItemListView.setOnMousePressed(new ListViewEventHandler(allApprovedItemListView,"ITEM_VIEW"));
            allApprovedItemListView.prefHeightProperty().bind(scene.heightProperty());
            allApprovedItemListView.prefWidthProperty().bind(scene.widthProperty());
            
            VBox boxTab2 = new VBox(allApprovedItemListView);
            boxTab2.setAlignment(Pos.CENTER_LEFT);
            boxTab2.setFillWidth(true);

            /*Label allApprovedtemListLbl = new Label("All Items List");
            allApprovedtemListLbl.setStyle("-fx-text-inner-color: black;-fx-font-weight: bold;");
            HBox allApprovedItemListLblBox = new HBox();
            allApprovedItemListLblBox.setAlignment(Pos.CENTER_LEFT);
            allApprovedItemListLblBox.setPadding(new Insets(0,0,10,10));
            allApprovedItemListLblBox.getChildren().add(allApprovedtemListLbl);
            allItemsTabBoxLayout.getChildren().add(allApprovedItemListLblBox);*/
            
            allItemsTabBoxLayout.getChildren().add(boxTab2);
            
            
            allItemsTabBoxLayout.setAlignment(Pos.CENTER_LEFT);
            allItemsTabBoxLayout.setFillWidth(true);
            
            allItemsTab.setContent(allItemsTabBoxLayout);
            tabPane.getTabs().add(allItemsTab);
            
            
            /***** End Second Tab for All Items ******/
            
        /*}*/
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
        String welcomeStr = "Welcome";
        try {
        	User user = LoggedinSession.getInstance().getLoggedinUser();
        	if(user == null) {
        		System.out.println("Logged in User is Null !!!!!");
        	}else {
            	welcomeStr = "Welcome " 
                		+ user.getLastName() 
                		+ ", " + user.getFirstName();
        	}

        }catch (Exception e) {
			System.out.println(e.getMessage());
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
        
        /*Image image = null;
		try {
			image = new Image(new FileInputStream("C:\\Users\\Shadi\\Desktop\\shadi_facebook.jpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
        /*ImageView iv2 = new ImageView();
        iv2.setImage(image);
        iv2.setFitWidth(250);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);*/
        
//        hboxTab1.getChildren().add(iv2);
        
        /***/
        
        //gridpane.add(pictureRegion, 1, 1);
        
        setScene(scene);
        show();
        previousStage.hide();
        
	}
	
	public void updateUserItemsList() {
        allUserItems = OperationsFactory.getItemsByUserId(userId);
//        userItemListView = new ListView<Item>();
        
        ObservableList<Item> items = userItemListView.getItems();
        userItemListView.setItems(null);
        
//        ObservableList<Item> userItemListViewList = FXCollections.observableArrayList(allUserItems);
        items.setAll(allUserItems);
        userItemListView.setItems(items);
        
        userItemListView.refresh();
	}
	
}
