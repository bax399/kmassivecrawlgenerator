package controller;
import java.util.*;
import java.io.*;
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
		FileInputStream in=null;
		
		//Add all defaults to a map for accessing
		propsdefault.put("biome",defaultbiome);
		propsdefault.put("bweight", defaultbweight);
		
		propsdefault.put("worldobject", defaultworldobject);
		
		propsdefault.put("monster",defaultmonster);
		propsdefault.put("location", defaultlocation);
		
		propsdefault.put("nomad",defaultnomad);
		propsdefault.put("site", defaultsite);
		propsdefault.put("structure", defaultstructure);
		
		propsdefault.put("dungeon",defaultdungeon);
		propsdefault.put("lair",defaultlair);
		propsdefault.put("landmark",defaultlandmark);
		propsdefault.put("town", defaulttown);
		
		
		//DEFAULTS		
		defaultbiome = new Properties();
		loadDefault(in,"biome");
		
		defaultbweight = new Properties();
		loadDefault(in,"bweight");
		

		defaultworldobject = new Properties();
		loadDefault(in, "worldobject");
		
		//Set up default iterations
		defaultmonster = new Properties(defaultworldobject);
		loadDefault(in, "monster");		
		defaultlocation = new Properties(defaultworldobject);
		loadDefault(in, "location");
		
		defaultnomad = new Properties(defaultlocation);		
		loadDefault(in, "nomad");
		defaultsite =  new Properties(defaultlocation);
		loadDefault(in, "site");		
		defaultstructure = new Properties(defaultlocation);
		loadDefault(in,"structure");
		
		defaultdungeon = new Properties(defaultstructure);
		loadDefault(in, "dungeon");		
		defaultlair =  new Properties(defaultstructure);
		loadDefault(in, "lair");
		defaultlandmark =  new Properties(defaultstructure);
		loadDefault(in, "landmark");		
		defaulttown = new Properties(defaultstructure);
		loadDefault(in, "town");
		//END DEFAULTS

		//Add all the types with a default to the storage arraylist
		Set<String> proptypes = propsdefault.keySet();
		Iterator<String> it = proptypes.iterator();
		while(it.hasNext())
		{
			String type = it.next();
			storage.put(type,new ArrayList<Properties>());
		}
	}
	
	//While line is not empty, read into a WorldObject class
	//this is then passed to the appropriate property readers for each WorldObject.type 
	//this is only called when there is just 1 type to process from bufferedreader.
	public void processType(BufferedReader br) 
	{
		Scanner sc = new Scanner(br);
		String line=null;
		String[] typeh=null;
		StringReader sr=null;
		Properties defaulttype=null,f=null;
		sc.useDelimiter("[^\\r\\n]+((\\r|\\n|\\r\\n)[^\\r\\n]+)*");
		//Starts with "type"
		while(sc.hasNext())
		{
			//Read First line
			line = sc.nextLine();

			//Read until line contains TYPE=
			while( (line.trim().isEmpty()) || (line.startsWith("#")) || (!line.startsWith("TYPE=")) )
			{
				line = sc.nextLine();
			}

			//Type splitting
			typeh = line.split("=");
			typeh[1] = cleanType(typeh[1]);
			
			//get the default properties from type
			defaulttype = getProperty(typeh[1]);
			
			//The rest of sc.next() is property stuff, process that.		
			sr = new StringReader(sc.next());
			
			f = new Properties(defaulttype);
			try
			{
				f.load(sr);
				List<Properties> proplist = storage.get(typeh[1]);
				proplist.add(f);
			}
			catch(IOException e)
			{
				System.out.println("Failed to create WorldObject: "+typeh[1]);
			}
		}
		sc.close();
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
	public void loadDefault(FileInputStream in, String name)
	{
		name = cleanType(name);
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
