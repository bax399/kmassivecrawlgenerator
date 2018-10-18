package model;

import java.util.Random;

import model.stats.StatsModifierBiome;

//Template model, overrides submethods to be road-specific shape.
public class Roadfinder extends Pathfinder {
	
	public Roadfinder(Random rand)
	{
		super(rand);
	}
	
	@Override
	public FilledHex earlyGreedyTermination(ConnectedHexMap chm, FilledHex start, FilledHex current)
	{
		return earlyDijkstraTermination(chm,start,current);
	}
	
	@Override
	public FilledHex earlyDijkstraTermination(ConnectedHexMap chm, FilledHex start, FilledHex current)
	{
		FilledHex fh = null;

    	if(current.getTowns().size() > 0 && !current.equals(start))
    	{
    		if (current.getRoadNode() ==null)
    		{
    			fh=current;    			
    		}
    	}
    	
    	if (current.getRoadNode() !=null)
    	{
    		if(!start.getRoadNode().getNetwork().equals(current.getRoadNode().getNetwork()))
    		{
    			fh=current;
    		}
    	}
		    	
		
	    return fh;	     
	}
	
	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next)
	{
		int prioritiseRoad = 0, prioritiseTown = 0, prioritiseConnection = 0;
		
//		if (next.getHabitat().getAllBiomes().contains(StatsModifierBiome.road))
//		{
//			prioritiseRoad+=1;
//		}		
		
//		if (next.getLargestTown() !=null)
//		{
//			prioritiseTown+=1;
//		}
		
		if (next.getRoadNode() !=null && current.getRoadNode() !=null)
		{		
			if (next.getRoadNode().getNetwork().areNeighbours(current.getRoadNode(), next.getRoadNode()))
			{
				prioritiseConnection+=next.getHabitat().getTravelCost();
			}
		}
		
		return next.getHabitat().getTravelCost() - prioritiseConnection - prioritiseRoad - next.distance(goal);
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{			
		//return next.getHabitat().getTravelCost();
		int prioritiseConnection=0;
		if (next.getRoadNode() !=null && current.getRoadNode() !=null)
		{
			if (next.getRoadNode().getNetwork().areNeighbours(current.getRoadNode(), next.getRoadNode()))
			{
				prioritiseConnection+=next.getHabitat().getTravelCost();
			}
		}
		return next.getHabitat().getTravelCost() - prioritiseConnection;
	}

}
