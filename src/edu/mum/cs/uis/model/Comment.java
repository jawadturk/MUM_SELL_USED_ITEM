package edu.mum.cs.uis.model;
import java.time.LocalDate;
public class Comment {
	
	private int id;
	private String commentContent;
	private int itemId;
	private User user;
	private LocalDate date;
	public Comment(int id, String commentContent, int itemId, User user,LocalDate date) {
		super();
		this.id = id;
		this.commentContent = commentContent;
		this.itemId = itemId;
		this.user = user;
		this.date=date;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public User getUser() {
		return user;
	}
	

}
