package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import functions.KFunctions;
import model.ConnectedHexMap;
import model.FilledHex;
import model.MutableInt;
import model.Network;
import model.Pathfinder;
import model.RiverNetwork;
import model.Riverfinder;
import model.graphresource.Edge;

//TODO add variance to river winding, greedy doesn't do it enough justice
public class RiverGenerator extends Generator {
	ConnectedHexMap hexmap;

	public RiverGenerator(ConnectedHexMap chm, Random rd)
	{
		super(rd);
		hexmap = chm;
	}

	public Set<Network> generateRivers()
	{
		Set<Network> networks = new HashSet<>();		

		if(hexmap.getRegions().size() > 1)
		{
			networks = generateOceanRivers(networks);
		}
		else
		{
			networks = generateRandomEndRivers(networks);
		}

		return networks;
	}
	
	public Set<Network> generateOceanRivers(Set<Network> networks)
	{
		RiverNetwork riverNetwork;
		
		List<FilledHex> riverStarts = new ArrayList<>();
		
		

		for(FilledHex eachHex : hexmap.getHexes().values())
		{
			if (rollChance(eachHex.getHabitat().getRiverOrigin())) 
			{
				riverStarts.add(eachHex);
			} 
		}
		
		Pathfinder riverFinder = new Riverfinder(getRand());
		for(FilledHex riverStarter : riverStarts )
		{
			FilledHex riverEnd = null;
			riverNetwork = new RiverNetwork();
			
			riverEnd = riverFinder.terminatingDijkstra(hexmap, riverStarter, new MutableInt(50));
			
			if(riverEnd !=null && !riverEnd.equals(riverStarter))
			{
				Set<Edge<FilledHex>> path = riverFinder.GreedyBFS(hexmap, riverEnd, riverStarter);
				riverNetwork.createNetwork(hexmap, path,networks);
				networks.add(riverNetwork);			
			}
			else
			{
				KFunctions.outputString(this,"	River failed!");
			}
		}
		
		return networks;
	}
	
	public Set<Network> generateRandomEndRivers(Set<Network> networks)
	{
		List<FilledHex> riverstarts = new ArrayList<>();

		List<FilledHex> riverends = new ArrayList<>();

		RiverNetwork riverNetwork;

		Iterator<FilledHex> it = hexmap.getHexes().values().iterator();
		while (it.hasNext()) {
			FilledHex fh = it.next();

			// Start
			if (rollChance(fh.getHabitat().getRiverOrigin())) 
			{
				riverstarts.add(fh);
			} 
			//All places that have a riverend spot is a viable end goal
			else if (Double.compare(fh.getHabitat().getCoreBiome().getRiverEnd(), 1d) >= 0) 
			{
				riverends.add(fh);
			}
		}

		Iterator<FilledHex> it2 = riverstarts.iterator();

		Pathfinder rf = new Riverfinder(getRand());
		while (it2.hasNext() && riverends.size() > 0) 
		{
			riverNetwork = new RiverNetwork();
			FilledHex fh2 = it2.next();
			int random = 0;

			// Choosing a random ending that isn't too close
			FilledHex fend = null;
			random = getRand().nextInt(riverends.size());
			fend = riverends.get(random);
			riverends.remove(random);

			Set<Edge<FilledHex>> path = rf.GreedyBFS(hexmap, fend,fh2);
			riverNetwork.createNetwork(hexmap, path,networks);
			networks.add(riverNetwork);
		}


		return networks;
	}
}
