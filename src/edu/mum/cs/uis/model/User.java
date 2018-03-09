package edu.mum.cs.uis.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private boolean isAdmin;
	private List<Item> itemsList;
	
	public User( int id,String firstName, String lastName, String userName, String password, boolean isAdmin) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.isAdmin = isAdmin;
		this.itemsList = new ArrayList<Item>();
	}
	
	public User(int id, String firstName, String lastName, String userName, String password, List<Item> itemsList) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.itemsList = itemsList;
	}
	
	public User() {	}

	public void setItemsList(List<Item> itemsList) {
		this.itemsList = itemsList;
	}
	
	

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public List<Item> getItemsList() {
		return itemsList;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id: "+ id +", firstName: " + firstName + ", lastName: " + lastName + ", userName: " + userName + ", password: " + password + ",isAdmin: " + isAdmin;
	}

}
