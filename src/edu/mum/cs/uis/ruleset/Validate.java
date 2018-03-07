package edu.mum.cs.uis.ruleset;

public class Validate {
	
	public static void validateLoginCredentials(String email,String password) throws RuleException
	{
		
		if(email.trim().isEmpty() ||
				password.trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
		
	}
	
	public static void validateSignUpCredentials(String email,String password,String firstName, String lastName) throws RuleException
	{
		
		if(email.trim().isEmpty() ||
		   password.trim().isEmpty()||
		   firstName.trim().isEmpty()||
		   lastName.trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
		
		if(!new EmailValidator().validate(email))
		{
			throw new RuleException("Email is not in the correct format.");
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
	

}
