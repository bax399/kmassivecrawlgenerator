package controller;
import java.util.*;
import model.*;
import model.worldobjects.*;
public class PropertiesFactory
{
	
	public <V extends Object> ArrayList<V> processProperties(ArrayList<Properties> pps)
	{
		ArrayList<V> objectlist = new ArrayList<>();
		Iterator<Properties> iter = pps.iterator();
		while(iter.hasNext())
		{
			Properties pp = iter.next();

			objectlist.add(chooseType(pp));
		}
		
		return objectlist;
	}	
	
	public <V extends Object> V chooseType(Properties pp)
	{
		String type = pp.getProperty("TYPE");
		V created = null; 
		if (type.equals("biome"))
		{	
			created = (V) new BiomeConcrete(pp);
		}
		else if(type.equals("town"))
		{
			created = (V) new Town(pp);
		}
		return created;
	}
	
	//Make this generic ^
	/*This doesnt work.
	private Class<V> cls;
	public PropertiesFactory(String classname) throws ClassNotFoundException
	{
		cls = Class.forName(classname);
	}
	
	public ArrayList<V> processType(ArrayList<Properties> typeprops)
	{
		ArrayList<V> typelist = new ArrayList<>();
		Object clsInstance;
		Iterator<Properties> iter = typeprops.iterator();
		while(iter.hasNext())
		{
			clsInstance = (Object) cls.newInstance();
			typelist.add(b);
		}
		
		return typelist;
	}
	*/	
}
