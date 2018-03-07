package edu.mum.cs.uis.ruleset;

public class Validate {
	
	public static void validateLoginCredentials(String userName,String password) throws RuleException
	{
		
		if(userName.trim().isEmpty() ||
				password.trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
		
	}
	
	public static void validateSignUpCredentials(String userName,String password,String firstName, String lastName) throws RuleException
	{
		
		if(userName.trim().isEmpty() ||
		   password.trim().isEmpty()||
		   firstName.trim().isEmpty()||
		   lastName.trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
		
		if(password.trim().length()<8)
		{
			throw new RuleException("Password must be greater than or equal 8 characters.");
		}
	}
	
	
	
	public static void validateAddComment(String comment)throws RuleException
	{
		if(comment.trim().isEmpty()
				  ) {
					throw new RuleException("Comment can't be empty.");
				}
	}
	

	public static void validateAddCategory(String category)throws RuleException
	{
		if(category.trim().isEmpty()
				  ) {
					throw new RuleException("category can't be empty.");
				}
	}
}
