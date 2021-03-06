package edu.mum.cs.uis.factorymethods;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.mum.cs.uis.db.UsedItemsDaoImpl;
import edu.mum.cs.uis.model.Category;
import edu.mum.cs.uis.model.Comment;
import edu.mum.cs.uis.model.Image;
import edu.mum.cs.uis.model.Item;
import edu.mum.cs.uis.model.Status;
import edu.mum.cs.uis.model.User;
import edu.mum.cs.uis.ruleset.RuleException;
import edu.mum.cs.uis.ruleset.Validate;

public class OperationsFactory {

	
	
	public static User logIn(String userName,String password)  throws RuleException
	{
		
		Validate.validateLoginCredentials(userName, password);
		return UsedItemsDaoImpl.getInstance().validateLogin(userName, password);
	}
	
	public static User register(String userName,String password,String firstName, String lastName)throws RuleException
	{
		Validate.validateSignUpCredentials(userName, password, firstName, lastName);
		return UsedItemsDaoImpl.getInstance().registerNewUser(new User(0, firstName, lastName,userName, password,false));
		
	}
	
	public static void addCategory(String categoryName)throws RuleException
	{
		Validate.validateAddCategory(categoryName);
		 UsedItemsDaoImpl.getInstance().addCategory(categoryName);
	}
	
	public static boolean addItem(String title,String description,String price, Image img, Category cat,int userId)throws RuleException
	
	{
		Validate.validateItemData(title, description, price, img, cat);
		Item item= new Item(title,description,Double.parseDouble(price),LocalDate.now(),Status.CREATED,img,cat,userId);
		return UsedItemsDaoImpl.getInstance().addItem(item);
		
	}

	public static List<Item> getItemsByUserId(int id)
	{
		return UsedItemsDaoImpl.getInstance().getAllItemsByUserId(id);
		
	}
	public static List<Item> getAllItems()
	{
		return UsedItemsDaoImpl.getInstance().getAllItems();
		
	}
	public static List<Item> getAllApprovedItems()
	{
		return UsedItemsDaoImpl.getInstance().getAllItemsByStatus(Status.APPROVED);
		
	}
	
	public static List<Item> getAllAdminItems()
	{
		return UsedItemsDaoImpl.getInstance().getAllItemsByStatus(Status.CREATED);
		
	}
	
	public static List<Category> getAllCategories()
	{
		return UsedItemsDaoImpl.getInstance().getCategories();
		
	}
	public static boolean deleteItem(int id)
	{
		
		return false;
	}
	
	public static boolean approveItem(int id)
	{
		
		 return UsedItemsDaoImpl.getInstance().updateItemStatusById(id, Status.APPROVED) ;
	}
	
	public static boolean diApproveItem(int id)
	{
		return UsedItemsDaoImpl.getInstance().updateItemStatusById(id, Status.REJECTED) ;
	}
	
	public static boolean addComment(String comment, int itemId,int userId) throws RuleException
	{
		Validate.validateAddComment(comment);
	
		return UsedItemsDaoImpl.getInstance().addComment(comment, itemId, userId);
	}
	
	public static List<Comment> getCommentsByItemId(int id){
		return new ArrayList<Comment>();
	}
	
	
	
}
