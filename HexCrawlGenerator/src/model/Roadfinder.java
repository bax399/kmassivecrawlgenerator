package model;

import model.stats.StatsModifierBiome;

//Template model, overrides submethods to be road-specific shape.
public class Roadfinder extends Pathfinder {

	@Override
	public FilledHex earlyGreedyTermination(ConnectedHexMap chm, FilledHex start, FilledHex current)
	{
		return earlyDijkstraTermination(chm,start,current);
	}
	
	@Override
	public FilledHex earlyDijkstraTermination(ConnectedHexMap chm, FilledHex start, FilledHex current)
	{
		FilledHex fh = null;
		//Terminate if you hit a town
	    if(current.getRoadNode() == null)
	    {
	    	if(current.getLargestTown() !=null)
	    	{
	    		fh=current;
	    	}
	    }
	    else
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
		if (next.getHabitat().getAllBiomes().contains(StatsModifierBiome.road))
		{
			prioritiseRoad+=100;
		}
		return goal.distance(next) + next.getHabitat().getTravelCost()*10 - prioritiseRoad;
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{
		return next.getHabitat().getTravelCost();//chm.adjTravelCost(current, next);
	}

}
