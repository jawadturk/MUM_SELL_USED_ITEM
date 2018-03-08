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
		this.status = status;
		this.img = img;
		this.cat=cat;
		this.userId=userId;
	}
	
	public Item( String title, String description, double price, LocalDate creationDate, Status status,Image img,Category cat, int userId) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
		this.creationDate = creationDate;
		this.status = status;
		this.img = img;
		this.cat=cat;
		this.userId=userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "id: " + id + ", title: " + title + ", description: " + description + ", img.path: " + 
					img.getPath() + ", cat.name: " + cat.getName() + ", status: " + status.toString() +
					", userId: " + userId;
	}
	
	

}
