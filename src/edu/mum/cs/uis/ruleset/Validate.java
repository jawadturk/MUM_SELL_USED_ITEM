package edu.mum.cs.uis.ruleset;

import java.time.LocalDate;

import edu.mum.cs.uis.model.Category;
import edu.mum.cs.uis.model.Image;
import edu.mum.cs.uis.model.Status;
import edu.mum.cs.uis.utils.Utils;
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
	
	public static void validateItemData( String title,
	 String description,
	 String price,
	 Image img,
	 Category cat)throws RuleException
	{
		if(title.trim().isEmpty() ||
				description.trim().isEmpty()||
				price.trim().isEmpty()
				   ) {
					throw new RuleException("All fields must be non-empty!");
				}
		
		if(img==null)
		{
			throw new RuleException("You must choose an image.");
		}
		
		if(cat==null)
		{
			throw new RuleException("You must choose Category.");
		}
		checkIfPriceIsFloatWithTwoPlaces(price);
				
		
	}
	
    private static void checkIfPriceIsFloatWithTwoPlaces(String text) throws RuleException
    {
        
    	    boolean checkValid = text.matches("^\\d+\\.\\d{2}$");
    	    
    	    if(!checkValid) {
    	    	throw new RuleException("Price must be a floating point number with two decimal places");
    	    
    	  }
    	    
    	
        /*if(Utils.doubleChecker(text))
        {
            if(Double.parseDouble(text)%1!=0)
            {
                
            
         if(text.split("\\.")[1].length()!=2)
         {
            throw new RuleException("Price must contain 2 decimal places");  
         }
         else{
             // do nothing
         }
          }else{
               throw new RuleException("Price must contain 2 decimal places");   
            }  
        }else{
            throw new RuleException("Price is not floating point number");
       }*/
    }
}
