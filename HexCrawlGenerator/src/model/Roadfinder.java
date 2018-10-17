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
		//CURRENT ITERATION: Doesn't link nearby towns together, avoids spindlyness
//	    if(current.getRoadNode() == null)
//	    {
//	    	if(current.getLargestTown() !=null)
//	    	{
//	    		fh=current;
//	    	}
//	    }
//	    else if(current.getRoadNode() !=null)
//	    {
//	    	
//	    	if (!start.getRoadNode().equals(current.getRoadNode()) && !start.getRoadNode().getNetwork().equals(current.getRoadNode().getNetwork()))
//	    	{
//	    		fh=current;
//	    	}
//	    	
//	    }
//		
//	    return fh;
	    
//		//BAD ITERATION: connects back and forward between similar towns.
    	if(current.getLargestTown() !=null && !current.equals(start))
    	{
    		if (current.getRoadNode() ==null)
    		{
    			fh=current;    			
    		}
    		else if(!start.getRoadNode().getNetwork().equals(current.getRoadNode().getNetwork()))
    		{
    			fh=current;
    		}
    		
    	}
    	
    	if(current.getRoadNode() !=null)
	    {
	    	if (!start.getRoadNode().equals(current.getRoadNode()) && !start.getRoadNode().getNetwork().equals(current.getRoadNode().getNetwork()))
	    	{
	    		fh=current;
	    	}
	    }
		
	    return fh;	     
	}
	
	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next)
	{
		int prioritiseRoad = 0,prioritiseTown = 0;
		
		if (next.getHabitat().getAllBiomes().contains(StatsModifierBiome.road))
		{
			prioritiseRoad+=1;
		}		
		
		if (next.getLargestTown() !=null)
		{
			prioritiseTown+=1;
		}
		
		return next.getHabitat().getTravelCost()*10  - prioritiseRoad*100 - prioritiseTown*10;
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{		
		//return (current.getHabitat().getTravelCost() + next.getHabitat().getTravelCost())/2;//chm.adjTravelCost(current, next);
		return next.getHabitat().getTravelCost();
	}

}
