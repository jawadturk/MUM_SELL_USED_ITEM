package edu.mum.cs.uis.model;

import java.time.LocalDate;

public class Item {
	
	private int id;
	private String title;
	private String description;
	private double price;
	private LocalDate creationDate;
	private Status status;
	private Image img;
	private Category cat;
	private int userId;
	
	public Item(int id, String title, String description, double price, LocalDate creationDate, Status status,Image img,Category cat, int userId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.creationDate = creationDate;
		this.status = Status.CREATED;
		this.img = img;
		this.cat=cat;
		this.userId=userId;
	}
	
	
	
	
	

}
