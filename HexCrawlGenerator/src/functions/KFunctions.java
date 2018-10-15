package functions;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Keeley
 * This contains generic functions needed by much of the program.
 */
public class KFunctions 
{
	//TODO move the outputString to a sub-debugger class.
	//private static int debug=1;
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
	  
	public static Set<String> processCSVtoSet(String csv)
	{
		Set<String> stringSet = new HashSet<>();
		csv=csv.replaceAll("\\s+", "");
		String[] split = csv.split(",");
		stringSet.addAll(Arrays.asList(split));
		
		return stringSet;
	}
	
	public static Color parseColor(String rgb)
	{
		String[] cc = rgb.split(",");
		int[] Irgb = new int[3];
		
		for(int ii=0;ii<3;ii++)
		{
			Irgb[ii]=Integer.parseInt(cc[ii]);
		}
		return new Color(Irgb[0],Irgb[1],Irgb[2]);
	}	
}
