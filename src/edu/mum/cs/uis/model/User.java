package edu.mum.cs.uis.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private List<Item> itemsList;
	public User(int id, String firstName, String lastName, String userName, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
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
	
	public void setItemsList(List<Item> itemsList) {
		this.itemsList = itemsList;
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

}
