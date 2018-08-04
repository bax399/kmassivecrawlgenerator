package controller;
import java.util.*;
import model.*;
public class PropertiesFactory
{
	
//	public ArrayList<V> processPropertyList(ArrayList<Properties> )
	
	
	public ArrayList<Biome> processBiomes(ArrayList<Properties> biomeprops)
	{
		ArrayList<Biome> biomelist = new ArrayList<>();
		Biome b = null;
		Iterator<Properties> iter = biomeprops.iterator();
		while(iter.hasNext())
		{
			Properties p = iter.next();
			b = new BiomeConcrete(p);
			biomelist.add(b);
		}
		
		return biomelist;
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
