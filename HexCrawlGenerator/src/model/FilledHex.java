package model;

import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.redblob.Hex;
import model.worldplaces.Habitat;
import model.worldplaces.HexTown;
import model.worldplaces.NetworkNode;
import model.worldplaces.WorldPlace;

public class FilledHex extends Hex 
{
	private Habitat habitat;
	public Point center;
	
	public int priority=0; //Editable field for pathfinding priority	
	
	private Polygon shape;	
	//Points are stored to place worldobjects in exact locations
	//Need a better way to store Points.
	//These are ONLY ITEMS THAT EXIST WITHIN THE HEX

	private Set<HexTown> towns = new HashSet<>();
	private NetworkNode riverNode=null, roadNode=null;
	private HexRegion region;
	
	//TODO addWorldObject methods to FilledHexes, randomize point then add it to the map.
	public FilledHex(Habitat hab, int q, int r, int s)
	{
		super(q,r);
		habitat = hab;
		region = null;
	}

	public FilledHex(Habitat hab, int q, int r)
	{
		super(q,r);
		habitat = hab;
		region = null;
	}
	
	//Can create FilledHexes using default biome values.
	public FilledHex(int q, int r)
	{
		super(q,r);
		habitat = new Habitat();
		region = null;
	}

	public void add(HexTown t)
	{
		towns.add(t);
	}
	
	public Set<HexTown> getTowns()
	{
		return Collections.unmodifiableSet(towns);
	}
	
	public void setRiverNode(NetworkNode node)
	{
		riverNode=node;
	}
	
	public void setRoadNode(NetworkNode node)
	{
		roadNode=node;
	}
	
	public NetworkNode getRiverNode()
	{
		return riverNode;
	}
	
	public NetworkNode getRoadNode()
	{
		return roadNode;
	}
	
	public HexTown getLargestTown()
	{
		int c=0;
		HexTown largest=null;
		for(HexTown t: towns)
		{
			if (t.getConnectivity() >= c)
			{
				c = t.getConnectivity();
				largest=t;
			}	
		}
		
		return largest;
	}
	

	public HexRegion getRegion()
	{
		return region;
	}
	
	public void setRegion(HexRegion region)
	{
		this.region = region;
	}

	public Habitat getHabitat()
	{
		return habitat;
	}
	
	public List<FilledHex> getNeighbours(ConnectedHexMap chm)
	{
		List<FilledHex> neighbours = new ArrayList<>();
		for(int ii = 0; ii < 6; ii++)
		{
			if (getNeighbour(chm,ii) != null) 
			{
				neighbours.add(getNeighbour(chm,ii)); 
			}
		}
		return neighbours; 
	}
	
	public FilledHex getNeighbour(ConnectedHexMap chm, int index)
	{
		return chm.getHex(this.neighbor(index)); 
	}	
	
	public void setShape(Polygon shape)
	{
		this.shape = shape;
	}
	
	public Polygon getShape()
	{
		return this.shape;
	}
}
