package edu.mum.cs.uis.utils;

public class Utils {

	 public static boolean doubleChecker(String text) {
         try {
         Double.parseDouble(text.trim());
         return true;
         }
         catch (NumberFormatException e ){
             return false;
         }
     }
}
