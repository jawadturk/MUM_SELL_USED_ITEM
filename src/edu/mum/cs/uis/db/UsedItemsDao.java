package edu.mum.cs.uis.db;

import edu.mum.cs.uis.model.User;

public interface UsedItemsDao {
	
	public User registerNewUser(User user);
	public User validateLogin(String userName, String password);
	public void addCategory(String name);

}
