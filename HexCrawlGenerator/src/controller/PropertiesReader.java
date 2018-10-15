package controller;
import java.io.BufferedReader;
import static functions.KFunctions.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Keeley
 * This class is responsible for loading default properties from "default<name>.properties" 
 */
public class PropertiesReader {

	
	//** DEFAULTS **
	Map<String,Properties> defaultProperties = new HashMap<>();
	
	//Type,Storage
	Map<String, ArrayList<Properties>> allProperties;
	Set<String> uniquenames;

	
	/**
	 * Calling this constructor will automatically read default properties into all types.
	 */
	public PropertiesReader() 
	{
		allProperties = new HashMap<String, ArrayList<Properties>>();
		uniquenames = new HashSet<String>();
		
		//Add all defaults to a map for accessing
		//DEFAULTS		
		//defaultbiome = 
		defaultProperties.put("biome",new Properties());		
        loadDefault("biome");
		
		//defaulttown = 
		defaultProperties.put("town", new Properties());
		loadDefault("town");
		//END DEFAULTS

		//Add all the types with a default to the storage arraylist
		Set<String> proptypes = defaultProperties.keySet();
		Iterator<String> it = proptypes.iterator();
		while(it.hasNext())
		{
			String type = it.next();
			allProperties.put(type,new ArrayList<Properties>());
		}
		
	}
	
	//While line is not empty, read into a WorldObject class
	//this is then passed to the appropriate property readers for each WorldObject.type 
	//this is only called when there is just 1 type to process from bufferedreader.


	//Loads the default property specified by name
	public void loadDefault(String name)
	{	
		FileReader reader;
		name = cleanType(name);
		try
		{				
			reader = new FileReader("default"+name+".properties");

			Properties prop = getDefaultProperty(name);
			prop.load(reader);
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Failed to read default property: "+name);
		}
	}
	
	public String cleanType(String type)
	{
		String out = type.trim();	
		out = out.toLowerCase();
		return out;
	}
	
	//gets the name from the Map storage.
	public Properties getDefaultProperty(String type)
	{
		type = cleanType(type);
		Properties prop = defaultProperties.get(type);
		return prop;
	}
	
	public ArrayList<Properties> getTypeList(String type)
	{
		type = cleanType(type);
		return getStorage().get(type);
	}
	
	public Map<String,ArrayList<Properties>> getStorage()
	{
		return Collections.unmodifiableMap(allProperties);
	}
		
}
