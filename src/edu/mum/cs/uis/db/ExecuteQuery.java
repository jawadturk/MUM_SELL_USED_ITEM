package edu.mum.cs.uis.db;

import java.util.ArrayList;
import java.util.List;

import edu.mum.cs.uis.model.Category;
import edu.mum.cs.uis.model.User;

public class ExecuteQuery {

	public static void main(String[] args) {

//		User user = new User("firstName", "lastName", "userName", "password", false);
		UsedItemsDao dao = UsedItemsDaoImpl.getInstance();

		// dao.registerNewUser(user);

		// System.out.println(dao.validateLogin("userName", "password"));
		// System.out.println(dao.validateLogin("userName1", "password"));
		// dao.addCategory("VEHICLE");
		// dao.addCategory("ELECTRONICS");
		// dao.addCategory("LAPTOP1");

		List<Category> cats = new ArrayList<>();
		cats = dao.getCategories();
		for (Category c : cats) {
			System.out.println(c);
		}

		// Image img = new Image(0, "/x/y/z");
		// Category category = new Category(1, "VEHICLE");
		//
		// Item item = new Item("title", "description", 25.5, LocalDate.now(),
		// Status.CREATED, img , category, 1);
		//
		// dao.addItem(item);

	}

}
