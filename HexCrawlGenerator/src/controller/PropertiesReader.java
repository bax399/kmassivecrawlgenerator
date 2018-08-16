package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
public class PropertiesReader {

	
	//** DEFAULTS **
	Map<String,Properties> propsdefault = new HashMap<>();
	
	//Type,Storage
	Map<String, ArrayList<Properties>> storage;
	
	//TODO change seperate defaultPropertie variables to just be accessed by the map.
	public PropertiesReader() 
	{
		storage = new HashMap<String, ArrayList<Properties>>();
		FileReader fr=null;
		//Add all defaults to a map for accessing
		//DEFAULTS		
		//defaultbiome = 
		propsdefault.put("biome",new Properties());		
		loadDefault(fr,"biome");
		
		//defaultworldobject = 
		propsdefault.put("worldobject", new Properties());		
		loadDefault(fr, "worldobject");
		
		//Set up default iterations
		//defaultmonster = 
		propsdefault.put("monster",new Properties(propsdefault.get("worldobject")));		
		loadDefault(fr, "monster");		
		//defaultlocation = 
		propsdefault.put("location", new Properties(propsdefault.get("worldobject")));		
		loadDefault(fr, "location");
		
		//defaultnomad = 		
		propsdefault.put("nomad",new Properties(propsdefault.get("location")));		
		loadDefault(fr, "nomad");
		//defaultsite =  
		propsdefault.put("site", new Properties(propsdefault.get("location")));		
		loadDefault(fr, "site");		
		//defaultstructure = 
		propsdefault.put("structure", new Properties(propsdefault.get("location")));		
		loadDefault(fr,"structure");
		
		//defaultdungeon = 
		propsdefault.put("dungeon",new Properties(propsdefault.get("structure")));		
		loadDefault(fr, "dungeon");
		//defaultlair =
		propsdefault.put("lair",new Properties(propsdefault.get("structure")));
		loadDefault(fr, "lair");
		//defaultlandmark =  
		propsdefault.put("landmark",new Properties(propsdefault.get("structure")));
		loadDefault(fr, "landmark");
		//defaulttown = 
		propsdefault.put("town", new Properties(propsdefault.get("structure")));
		loadDefault(fr, "town");
		//END DEFAULTS

		//Add all the types with a default to the storage arraylist
		Set<String> proptypes = propsdefault.keySet();
		Iterator<String> it = proptypes.iterator();
		while(it.hasNext())
		{
			String type = it.next();
			storage.put(type,new ArrayList<Properties>());
		}
		
		//System.out.println(propsdefault.get("biome").getProperty("name"));
	}
	
	//While line is not empty, read into a WorldObject class
	//this is then passed to the appropriate property readers for each WorldObject.type 
	//this is only called when there is just 1 type to process from bufferedreader.
	public void processType(BufferedReader br) 
	{
		Scanner sc = new Scanner(br).useDelimiter("(\n\r)+");
		String line=null;
		String typeh=null;
		StringReader sr=null;
		Properties defaulttype=null,f=null;
		//BAD REGEX, MUST HAVE A BLANK/COMMENTED LINE, CALLED EVERY LINE 

		//Starts with "type"
		while(sc.hasNext())
		{
			
			//Read entire paragraph
			line = sc.next();
			typeh = line.trim();
			typeh = typeh.split("=|\n")[1].trim();
			
			//get the default properties from type
			defaulttype = getDefaultProperty(typeh);
			
			//The rest of sc.next() is property stuff, process that.		
			sr = new StringReader(line);
			
			f = new Properties(defaulttype);
			try
			{
				//Loads all the property information contained into f
				f.load(sr);
				storage.get(typeh).add(f);
				//System.out.println("Added "+typeh+": " + f.getProperty("name"));
				//System.out.println(f.stringPropertyNames());
			}
			catch(IOException e)
			{
				System.out.println("Failed to create WorldObject: "+typeh);
			}
		}
		sc.close();
		sr.close();
	}
	
	/*TODO FUTURE FUNCTIONALITY needs parent inheritance for properties.
	public Properties useParent(Properties in, ArrayList<Properties> type)
	{
		Properties child;
		String pname;
		if(in.getProperty("parent") != null)
		{
			pname = in.getProperty("parent");
			for(int ii = 0; ii < type.size();ii++)
			{
				if (type.get(ii).getProperty("name").equals(pname))
				{
					child = new Properties(type.get(ii));
					for(String field: child.propertyNames())
					{
						
					}
					
				}
			}
		}
		
		return in;
	}
	*/
	
	public Map<String,ArrayList<Properties>> getFileObjects()
	{
		return storage;
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
		Properties prop = propsdefault.get(type);
		return prop;
	}
	
	//Loads the default property specified by name
	public void loadDefault(FileReader in, String name)
	{	
		name = cleanType(name);
		try
		{				
			in = new FileReader("default"+name+".properties");

			Properties prop = getDefaultProperty(name);
			prop.load(in);
			in.close();
		}
		catch(IOException e)
		{
			System.out.println("Failed to read default property: "+name);
		}
	}

	public ArrayList<Properties> getTypeList(String type)
	{
		type = cleanType(type);
		return getStorage().get(type);
	}
	
	public Map<String,ArrayList<Properties>> getStorage()
	{
		return Collections.unmodifiableMap(storage);
	}
	
	public Map<String, Properties> getDefaults()
	{
		return Collections.unmodifiableMap(propsdefault);
	}
}
