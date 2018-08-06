package model.worldobjects;
import java.util.*;
import model.RoadNetwork;
import model.redblob.Point;

public class RoadNode 
{
	private Point pos;
	private RoadNetwork owner=null;
	
	public RoadNode(RoadNetwork rn, Point p)
	{
		owner = rn;
		pos = p;
	}

	public void setNetwork(RoadNetwork rn)
	{
		owner = rn;
	}
	
	public RoadNetwork getNetwork()	
	{
		return owner;
	}
	
	public Point getPosition()
	{
		return pos;
	}
}
