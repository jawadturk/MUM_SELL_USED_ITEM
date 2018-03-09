package edu.mum.cs.uis.view.control;

import edu.mum.cs.uis.db.DBConnection;
import edu.mum.cs.uis.model.User;

public class LoggedinSession {

	private User loggedinUser;
	
	private static LoggedinSession instance;
	
	private LoggedinSession() {
		
	}
	
	public static LoggedinSession getInstance() {
		if(instance == null) {
			instance = new LoggedinSession();
		}
		return instance;	
	}
	
	public void setLoggedinUser(User user) {
		if(loggedinUser == null) {
			loggedinUser = user;					
		}
	}
	
	public User getLoggedinUser() {
		return loggedinUser;
	}
	
	
	
}
