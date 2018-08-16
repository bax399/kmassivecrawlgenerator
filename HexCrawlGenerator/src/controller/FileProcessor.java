package controller;
import static functions.PFunctions.outputString;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class FileProcessor {
	
	public void processFile(String filename, PropertiesReader ptr)
	{
		FileReader fr = null;
		BufferedReader br = null;
		//read until a line exists that isn't a comment or empty.
		try
		{
			outputString(this, "Reading file: " + filename);
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			ptr.processType(br);
				
					//Deprecated, not doing this method.
						//String classname = split[1].trim();	
						//classname = classname.substring(0,1).toUpperCase() + classname.substring(1).toLowerCase();
						//Class<TypeReader> type = (Class<TypeReader>) Class.forName(classname+"Reader");
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
			
		
		
		
	}
}
