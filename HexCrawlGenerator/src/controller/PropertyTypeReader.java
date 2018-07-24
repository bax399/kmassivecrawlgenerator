package controller;
import java.util.*;
import java.io.*;
public class PropertyTypeReader {

	
	//** DEFAULTS **
	Map<String,Properties> propsdefault = new HashMap<>();
	
	Properties defaultworldobject;
	//Reads default Properties from file, then each type 
	Properties defaultbiome;
	Properties defaultbweight;
	
	Properties defaultmonster;
	
	//Abstract, nothing can be just a location. Use 'Site' for misc stuff.
	Properties defaultlocation;
	//Locations
	Properties defaultdungeon;
	Properties defaultlair;
	Properties defaultlandmark;
	Properties defaultnomad;
	Properties defaultsite;
	
	//Type,Storage
	Map<String, ArrayList<Properties>> storage;
	
	
	public PropertyTypeReader() 
	{
		//Add all defaults to a map for accessing
		propsdefault.put("worldobject", defaultworldobject);
		propsdefault.put("location", defaultlocation);
		propsdefault.put("biome",defaultbiome);
		propsdefault.put("bweight", defaultbweight);
		propsdefault.put("monster",defaultmonster);
		propsdefault.put("dungeon",defaultdungeon);
		propsdefault.put("lair",defaultlair);
		propsdefault.put("landmark",defaultlandmark);
		propsdefault.put("nomad",defaultnomad);
		propsdefault.put("site", defaultsite);
		
		//Read Defaults.

		FileInputStream in=null;
		defaultworldobject = new Properties();
		
		loadDefault(in, "worldobject");
		
		//Set up default iterations
		defaultbiome = new Properties(defaultworldobject); 
		defaultbweight = new Properties(defaultworldobject);
		defaultmonster = new Properties(defaultworldobject);
		defaultlocation = new Properties(defaultworldobject);
		
		
		defaultdungeon = new Properties(defaultlocation);
		defaultlair = new Properties(defaultlocation);
		defaultlandmark = new Properties(defaultlocation);
		defaultnomad = new Properties(defaultlocation);
		defaultsite = new Properties(defaultlocation);
		
		//Load defaults from files
		loadDefault(in, "location");
		loadDefault(in, "biome");
		loadDefault(in, "bweight");
		loadDefault(in, "monster");
		loadDefault(in, "dungeon");
		loadDefault(in, "lair");
		loadDefault(in, "landmark");
		loadDefault(in, "nomad");
		loadDefault(in, "site");

	}
	
	//While line is not empty, read into a WorldObject class
	//this is then passed to the appropriate property readers for each WorldObject.type 
	//this is only called when there is just 1 type to process from bufferedreader.
	public void processType(BufferedReader br) 
	{
		Scanner sc = new Scanner(br).useDelimiter("[^\\r\\n]+((\\r|\\n|\\r\\n)[^\\r\\n]+)*");
		//Starts with "type"
		String[] typeh = sc.nextLine().split("=");
		Properties defaulttype = getProperty(typeh[1]);
		//Class<TypeReader> type = (Class<TypeReader>) Class.forName(classname+"Reader");		
		StringReader sr = new StringReader(sc.next());
		
		Properties f = new Properties(defaulttype);
		try
		{
			f.load(sr);
		}
		catch(IOException e)
		{
			System.out.println("Failed to create WorldObject: "+typeh[1]);
		}
		
		sc.close();
	}
	
	public Map<String,ArrayList<Properties>> getFileObjects()
	{
		return storage;
	}
	
	//gets the name from the Map storage.
	public Properties getProperty(String type)
	{
		type = type.trim();	
		type = type.toLowerCase();
		Properties prop = propsdefault.get(type);
		return prop;
	}
	
	//Loads the default property specified by name
	public void loadDefault(FileInputStream in, String name)
	{
		name = name.trim();	
		name = name.toLowerCase();		
		try
		{
			in = new FileInputStream("default"+name);
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
