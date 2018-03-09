package edu.mum.cs.uis.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import edu.mum.cs.uis.factorymethods.OperationsFactory;
import edu.mum.cs.uis.model.Category;
import edu.mum.cs.uis.model.Item;
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

public class AdminHomeView  extends Stage{

	Stage previousStage;

	private final Image IMAGE_DEFAULT = new Image(getClass().getResource("default_item_image.png").toExternalForm());
	
	ListView<Item> allPendingItemListView;
	ListView<Category> itemCategoriesListView;
	
	List<Item> allPendingItems;
	List<Category> allCategoriesList;
	
	int userId;
	
    /*private Image[] listOfImages = {IMAGE_RUBY, IMAGE_APPLE, IMAGE_VISTA, IMAGE_TWITTER};*/

    public Stage getPreviousStage() {
		return previousStage;
	}

	public void setPreviousStage(Stage previousStage) {
		this.previousStage = previousStage;
	}
	
	
	public AdminHomeView(Stage ps) {
		previousStage = ps;
		
        setTitle("Admin Home Screen");
        Group root = new Group();
        Scene scene = new Scene(root, 650, 400, Color.WHITE);

        TabPane tabPane = new TabPane();
        

        BorderPane borderPane = new BorderPane();

            Tab categoriesTab = new Tab();
            categoriesTab.setText("Item Categories");
            categoriesTab.setClosable(false);
            VBox itemCategoriesTabBoxLayout = new VBox();
            
            userId = 0;
            
            try {
				userId = LoggedinSession.getInstance().getLoggedinUser().getId();
				System.out.println("Loggedin User ID: " + userId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            
            allCategoriesList = OperationsFactory.getAllCategories();
            
            itemCategoriesListView = new ListView<Category>();
            
            ObservableList<Category> itemCategoriesListViewList = FXCollections.observableArrayList(allCategoriesList);
            
            itemCategoriesListView.setItems(itemCategoriesListViewList);
            itemCategoriesListView.setCellFactory(param -> new ListCell<Category>() {
                @Override
                public void updateItem(Category catVal, boolean empty) {
                    super.updateItem(catVal, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                    	setText(catVal.getName());
                    }
                }
            });
            
            
            itemCategoriesListView.prefHeightProperty().bind(scene.heightProperty());
            itemCategoriesListView.prefWidthProperty().bind(scene.widthProperty());
            
            VBox box = new VBox(itemCategoriesListView);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setFillWidth(true);


            Button addCategoryBtn = new Button("Add Item Category");
            addCategoryBtn.setOnAction(new AddItemCategoryController(this));
            HBox addCategoryBox = new HBox();
            addCategoryBox.setAlignment(Pos.CENTER_RIGHT);
            addCategoryBox.setPadding(new Insets(10,10,10,10));
            addCategoryBox.getChildren().add(addCategoryBtn);
            
            Label categoryListLbl = new Label("Categories List");
            categoryListLbl.setStyle("-fx-text-inner-color: black;-fx-font-weight: bold;");
            HBox categoryListLblBox = new HBox();
            categoryListLblBox.setAlignment(Pos.CENTER_LEFT);
            categoryListLblBox.setPadding(new Insets(0,0,10,10));
            categoryListLblBox.getChildren().add(categoryListLbl);
            
            itemCategoriesTabBoxLayout.getChildren().add(addCategoryBox);
            itemCategoriesTabBoxLayout.getChildren().add(categoryListLblBox);
            itemCategoriesTabBoxLayout.getChildren().add(box);
            
            
            itemCategoriesTabBoxLayout.setAlignment(Pos.CENTER_LEFT);
            itemCategoriesTabBoxLayout.setFillWidth(true);
            categoriesTab.setContent(itemCategoriesTabBoxLayout);
            tabPane.getTabs().add(categoriesTab);
            
            /***** Start Second Tab for All Items ******/
            
            Tab allPendingItemsTab = new Tab();
            allPendingItemsTab.setText("Pending Created Items");
            allPendingItemsTab.setClosable(false);
            
            VBox allPendingItemsTabBoxLayout = new VBox();
            
            allPendingItems = OperationsFactory.getAllAdminItems();
            
            allPendingItemListView = new ListView<Item>();
            
            ObservableList<Item> allPendingItemListViewList = FXCollections.observableArrayList(allPendingItems);
            
            allPendingItemListView.setItems(allPendingItemListViewList);
            allPendingItemListView.setCellFactory(param -> new ListCell<Item>() {
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
            

            allPendingItemListView.setOnMousePressed(new ListViewEventHandler(allPendingItemListView,"ITEM_EDIT"));
            allPendingItemListView.prefHeightProperty().bind(scene.heightProperty());
            allPendingItemListView.prefWidthProperty().bind(scene.widthProperty());
            
            VBox boxTab2 = new VBox(allPendingItemListView);
            boxTab2.setAlignment(Pos.CENTER_LEFT);
            boxTab2.setFillWidth(true);
            
            allPendingItemsTabBoxLayout.getChildren().add(boxTab2);
            
            
            allPendingItemsTabBoxLayout.setAlignment(Pos.CENTER_LEFT);
            allPendingItemsTabBoxLayout.setFillWidth(true);
            
            allPendingItemsTab.setContent(allPendingItemsTabBoxLayout);
            tabPane.getTabs().add(allPendingItemsTab);
            
            
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
	
	public void updatePendingItemsList() {
        allPendingItems = OperationsFactory.getAllAdminItems();
        ObservableList<Item> items = allPendingItemListView.getItems();
        allPendingItemListView.setItems(null);
        items.setAll(allPendingItems);
        allPendingItemListView.setItems(items);
        allPendingItemListView.refresh();
	}

	public void updateItemCategoriesList() {
        allCategoriesList = OperationsFactory.getAllCategories();
        ObservableList<Category> categories = itemCategoriesListView.getItems();
        itemCategoriesListView.setItems(null);
        categories.setAll(allCategoriesList);
        itemCategoriesListView.setItems(categories);
        itemCategoriesListView.refresh();
	}
	
}
