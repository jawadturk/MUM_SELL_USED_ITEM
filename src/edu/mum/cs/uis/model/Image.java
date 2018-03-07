package edu.mum.cs.uis.model;

public class Image {
	
	private int id;
	private String path;

	
	public Image(int id, String path) {
		super();
		this.id = id;
		this.path = path;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	

}
