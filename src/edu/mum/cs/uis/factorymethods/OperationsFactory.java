package edu.mum.cs.uis.factorymethods;

import edu.mum.cs.uis.db.UsedItemsDaoImpl;
import edu.mum.cs.uis.model.User;
import edu.mum.cs.uis.ruleset.RuleException;
import edu.mum.cs.uis.ruleset.Validate;

public class OperationsFactory {
	
	
	public static User  logIn(String userName,String password)  throws RuleException
	{
		
		Validate.validateLoginCredentials(userName, password);
		return new UsedItemsDaoImpl().validateLogin(userName, password);
	}
	
	public static User register(String userName,String password,String firstName, String lastName)throws RuleException
	{
		Validate.validateSignUpCredentials(userName, password, firstName, lastName);
		return new UsedItemsDaoImpl().registerNewUser(new User(userName, password, firstName, lastName,false));
		
	}
	
	public static void addCategory(String categoryName)throws RuleException
	{
		Validate.validateAddCategory(categoryName);
		new UsedItemsDaoImpl().addCategory(categoryName);
	}

}
