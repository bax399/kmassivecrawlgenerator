package controller;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import functions.PFunctions;
import model.BiomeConcrete;
import model.worldobjects.Town;
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
			created = (V) createBiomeConcrete(pp);
		}
		else if(type.equals("town"))
		{
			created = (V) createTown(pp);
		}
		return created;
	}
	
	
	public BiomeConcrete createBiomeConcrete(Properties pp)
	{
		BiomeConcrete created = new BiomeConcrete(	
									pp.getProperty("name"),
									PFunctions.parseColor(pp.getProperty("color")),
									Integer.parseInt(pp.getProperty("height")),
									Integer.parseInt(pp.getProperty("travelcost")),
									pp.getProperty("spotdistance"),
									Double.parseDouble(pp.getProperty("riverorigin")),
									Double.parseDouble(pp.getProperty("riverend")),
									PFunctions.convertToBoolean(pp.getProperty("validstart")),		
									pp.getProperty("weight").toLowerCase(),
									PFunctions.processCSVtoSet(pp.getProperty("validregion"))
								);
		
		return created;
	}
	
	public Town createTown(Properties pp)
	{
		Town created = new Town(
							pp.getProperty("name"),Integer.parseInt(pp.getProperty("visibility")),Integer.parseInt(pp.getProperty("max")),
							Integer.parseInt(pp.getProperty("connectivity")),
							pp.getProperty("validbiomes").toLowerCase(),
							PFunctions.convertToBoolean(pp.getProperty("needriver")),
							Double.parseDouble(pp.getProperty("spawnchance"))
						);
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
