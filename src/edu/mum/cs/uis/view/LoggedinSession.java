package edu.mum.cs.uis.view;

import edu.mum.cs.uis.model.User;

public class LoggedinSession {

	private static User loggedinUser;
	
	public static void setLoggedinUser(User user) {
		if(loggedinUser == null) {
			loggedinUser = user;					
		}
	}
	
	public static User getLoggedinUser() {
		return loggedinUser;
	}
	
	
	
}
