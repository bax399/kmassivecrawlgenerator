package controller;
import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Path;
public class PropertyTypeReader {

	
	//** DEFAULTS **
	Map<String,Properties> propsdefault = new HashMap<>();
	
	Properties defaultbiome;
	Properties defaultbweight;
	
	Properties defaultworldobject;
	//WorldObjects (Below)
	Properties defaultmonster;
	Properties defaultlocation; //Abstract
	
	
	//Locations
	Properties defaultnomad;
	Properties defaultsite;
	Properties defaultstructure; //Abstract
	//Structures
	Properties defaultdungeon;
	Properties defaultlair;
	Properties defaultlandmark;
	Properties defaulttown;
	
	//Type,Storage
	Map<String, ArrayList<Properties>> storage;
	
	
	public PropertyTypeReader() 
	{
		storage = new HashMap<String, ArrayList<Properties>>();
		FileReader fr=null;
		//Add all defaults to a map for accessing
		//DEFAULTS		
		defaultbiome = new Properties();
		propsdefault.put("biome",defaultbiome);		
		loadDefault(fr,"biome");
		
		defaultbweight = new Properties();
		propsdefault.put("bweight", defaultbweight);		
		loadDefault(fr,"bweight");
		

		defaultworldobject = new Properties();
		propsdefault.put("worldobject", defaultworldobject);		
		loadDefault(fr, "worldobject");
		
		//Set up default iterations
		defaultmonster = new Properties(defaultworldobject);
		propsdefault.put("monster",defaultmonster);		
		loadDefault(fr, "monster");		
		defaultlocation = new Properties(defaultworldobject);
		propsdefault.put("location", defaultlocation);		
		loadDefault(fr, "location");
		
		defaultnomad = new Properties(defaultlocation);		
		propsdefault.put("nomad",defaultnomad);		
		loadDefault(fr, "nomad");
		defaultsite =  new Properties(defaultlocation);
		propsdefault.put("site", defaultsite);		
		loadDefault(fr, "site");		
		defaultstructure = new Properties(defaultlocation);
		propsdefault.put("structure", defaultstructure);		
		loadDefault(fr,"structure");
		
		defaultdungeon = new Properties(defaultstructure);
		propsdefault.put("dungeon",defaultdungeon);		
		loadDefault(fr, "dungeon");
		defaultlair =  new Properties(defaultstructure);
		propsdefault.put("lair",defaultlair);
		loadDefault(fr, "lair");
		defaultlandmark =  new Properties(defaultstructure);
		propsdefault.put("landmark",defaultlandmark);
		loadDefault(fr, "landmark");
		defaulttown = new Properties(defaultstructure);
		propsdefault.put("town", defaulttown);
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
		Scanner sc = new Scanner(br).useDelimiter("\n\r");
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
			defaulttype = getProperty(typeh);
			
			//The rest of sc.next() is property stuff, process that.		
			sr = new StringReader(line);
			
			f = new Properties(defaulttype);
			try
			{
				//Loads all the property information contained into f
				f.load(sr);
				storage.get(typeh).add(f);
				System.out.println("Added WorldObject: " + f);
			}
			catch(IOException e)
			{
				System.out.println("Failed to create WorldObject: "+typeh);
			}
		}
		sc.close();
		sr.close();
	}
	
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
	public Properties getProperty(String type)
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

			Properties prop = getProperty(name);
			prop.load(in);
			in.close();
		}
		catch(IOException e)
		{
			System.out.println("Failed to read default property: "+name);
		}
	}
		
}
