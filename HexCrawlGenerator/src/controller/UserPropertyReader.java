package controller;
import static functions.KFunctions.outputString;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.Scanner;


/**
 * @author Keeley
 *	This Class is responsible for reading in non-default property files
 *  Additionally, it has the methods responsible for reading ALL property files, to create Property Storage Map.
 */

public class UserPropertyReader extends PropertiesReader
{

	
	/**
	 * This will eventually read multiple user-specified files.
	 * TODO make this read multiple user-specified files.
	 * @param filename
	 */
	public void processFile(String filename)
	{
		FileReader fr = null;
		BufferedReader br = null;
		//read until a line exists that isn't a comment or empty.
		try
		{
			outputString(this, "Reading file: " + filename);
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			processType(br);
				
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Read a property cluster from buffered reader. Input Buffered Reader
	 * @param br
	 */
	public void processType(BufferedReader br) 
	{
		Scanner sc = new Scanner(br);
		sc.useDelimiter("(\n\r)+"); 
		String line, propertyTypeName, checkName;

		Properties defaultProperties,singleStatsProperties;
		//Starts with "type"
		while(sc.hasNext())
		{
			StringReader stringReader;
			//Read entire paragraph
			line = sc.next();
			propertyTypeName = line.trim();
			propertyTypeName = propertyTypeName.split("=|\n")[1].trim();
			
			//get the default properties from type
			defaultProperties = getDefaultProperty(propertyTypeName);
			//The rest of sc.next() is property stuff, process that.		
			stringReader = new StringReader(line);
			
			singleStatsProperties = new Properties(defaultProperties);
			try
			{
				//Loads all the property information contained into f
				singleStatsProperties.load(stringReader);
				
				//checks that f's name is unique
				checkName = singleStatsProperties.getProperty("name");
				if(!uniquenames.add(checkName))
				{
					outputString(this, "Failed to create Worldobject (non-unique name): " + singleStatsProperties.getProperty("name"));
				}
				else //Adds f to the correct property list.
				{
					allProperties.get(propertyTypeName).add(singleStatsProperties);
				}
				
			}
			catch(IOException e)
			{
				outputString(this,"Failed to create WorldObject: "+propertyTypeName);
			}
			stringReader.close();			
		}
		sc.close();

	}	
}
