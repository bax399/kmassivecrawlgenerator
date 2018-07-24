package controller;
import controller.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class FileProcessor {
	
	public void processFile(String filename)
	{
		FileReader fr = null;
		BufferedReader br = null;
		String line = null;
		PropertyTypeReader tr = null;	
		//read until a line exists that isn't a comment or empty.
		
		try
		{
			fr = new FileReader(filename);
			br = new BufferedReader(fr);

			tr = new PropertyTypeReader();

				
			tr.processType(br);
				
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
