package model;

import model.stats.StatsModifierBiome;

public class Riverfinder extends Pathfinder {

	@Override
	public FilledHex earlyDijkstraTermination(ConnectedHexMap chm, FilledHex goal, FilledHex current)
	{
		FilledHex fh = goal;
	    //for rivers...
	    if (current.getHabitat().getRiverEnd() >= 0.8)
	    {	    	
	    	fh=current;
	    }		
	    
	    return fh;
	}
	
	@Override
	public FilledHex earlyGreedyTermination(ConnectedHexMap chm, FilledHex goal, FilledHex current)
	{
		FilledHex fh = goal;
	    //for rivers...
	    if (current.getHabitat().getRiverEnd() >= 0.2d)
	    {	    	
	    	fh=current;
	    }		
	    
	    return fh;
	}
	
	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next)
	{
		int prioritiseWater = 0;
		
		prioritiseWater=(int)(100*next.getHabitat().getRiverEnd());
		return goal.distance(next) + next.getHabitat().getHeight()*10 - prioritiseWater;
	}
		
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{
		int heightdiff;
		heightdiff = next.getHabitat().getHeight() - (current.getHabitat().getHeight());
		//heightdiff = Math.max(heightdiff, 0);
		return Math.max(heightdiff,0)*10+1;//+Math.max(0,chm.adjTravelCost(current,next)-5); //minus river travel cost...
	}

}
