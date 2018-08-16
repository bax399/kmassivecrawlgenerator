package functions;

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
		System.out.println(rightPadding("Static Class",25) +"| "+data);
	}
	
	public static void outputString(Object oo, String data)
	{
		System.out.println(rightPadding(oo.getClass().getSimpleName() + ".java",25) + "| "+data);
	}
	
	  public static String rightPadding(String str, int num) 
	  {
		    return String.format("%1$-" + num + "s", str);
	  }	
}
