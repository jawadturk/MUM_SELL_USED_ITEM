package edu.mum.cs.uis.db;

import java.util.List;

import edu.mum.cs.uis.model.Category;
import edu.mum.cs.uis.model.Item;
import edu.mum.cs.uis.model.Status;
import edu.mum.cs.uis.model.User;

public interface UsedItemsDao {
	
	public User registerNewUser(User user);
	public User validateLogin(String userName, String password);
	
	public boolean addCategory(String name);
	public List<Category> getCategories();
	
	public boolean addItem(Item item);
	public List<Item> getAllItemsByStatus(Status status);
	public List<Item> getAllItemsByUserId(int userId);
	public Item getItemDetailsById(int itemId);
	public boolean updateItemStatusById(int itemId, Status status);
	
	
	public boolean addComment(String comment, int itemId, int userId);
	
	

}
