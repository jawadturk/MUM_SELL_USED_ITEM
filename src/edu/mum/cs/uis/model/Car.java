package edu.mum.cs.uis.model;

import java.time.LocalDate;

public class Car extends Item {
	
	private Double mileage;
	private int year;
	private String model;
	public Car(int id, String title, String description, double price, LocalDate creationDate, Status status, Image img,
			Category cat,Double mileage, int year, String model, int userId) {
		super(id, title, description, price, creationDate, status, img,cat, userId);
		this.mileage = mileage;
		this.year = year;
		this.model = model;
	}
	
	

}
