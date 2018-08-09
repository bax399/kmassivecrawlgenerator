package functions;
import java.util.*;
public class PFunctions 
{
	public static boolean convertToBoolean(String value) 
	{
	    boolean returnValue = false;
	    if ("1".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) || 
	        "true".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value))
	        returnValue = true;
	    return returnValue;
	}
	
	public static void outputString( String data)
	{
		System.out.println("Came from: Static Class |Message: " + data);
	}
	
	public static void outputString(Object oo, String data)
	{
		System.out.println("Came from: " + oo.getClass().getSimpleName() + ".java |Message: " + data);
	}	
}
