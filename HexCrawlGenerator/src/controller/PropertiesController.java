package controller;

import java.util.List;

import model.stats.*;

/**
 * @author Keeley
 *	This class controls the PropertyFactory, PropertyReader and FileProcessor to produce the instantialized statsObjects
 */
public class PropertiesController
{
	private List<StatsTown> townList;
	private List<StatsCoreBiome> biomeList;

	public PropertiesController()
	{
		UserPropertyReader userReader = new UserPropertyReader();;
		PropertiesFactory pFactory = new PropertiesFactory();
		userReader.processFile("inputTest.txt");
		
		
		townList = pFactory.processProperties(userReader.getTypeList("town"));
		biomeList = pFactory.processProperties(userReader.getTypeList("biome"));

	}

	public List<StatsTown> getTownList() 
	{
		return townList;
	}

	public List<StatsCoreBiome> getBiomeList() 
	{
		return biomeList;
	}
}
