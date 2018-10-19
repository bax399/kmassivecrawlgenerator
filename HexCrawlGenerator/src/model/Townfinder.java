package model;

import java.util.Random;

import model.stats.StatsModifierBiome;

public class Townfinder extends Pathfinder {

	public Townfinder(Random rand)
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
	    
		//BAD ITERATION: connects back and forward between similar towns.
    	if(current.getLargestTown() !=null && !current.equals(start))
    	{
    		fh=current;
    	}
    	
    	if(current.getRoadNode() !=null)
	    {
	    	if (!start.getRoadNode().equals(current.getRoadNode()))
	    	{
	    		fh=current;
	    	}
	    }
		
	    return fh;	     
	}
	
	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next)
	{
		int prioritiseRoad = 0;
		if (next.getRoadNode() !=null)
		{
			prioritiseRoad+=100;
		}
		return goal.distance(next) + next.getHabitat().getTravelCost()*10 + prioritiseRoad;
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{
		int avoidRoad=0;
		if (next.getRoadNode() != null)
		{
			avoidRoad = 1;
		}
		return (current.getHabitat().getTravelCost()+next.getHabitat().getTravelCost())/2+avoidRoad*10;//chm.adjTravelCost(current, next);
	}

}
