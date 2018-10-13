package model;

//Template model, overrides submethods to be road-specific shape.
public class Roadfinder extends Pathfinder {

	@Override
	public FilledHex earlyTermination(ConnectedHexMap chm, FilledHex start, FilledHex current)
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
	    else //end if the existing road not is NOT in set.
	    {
	    	if (!start.getRoadNode().getNetwork().equals(current.getRoadNode().getNetwork()))
	    	{
	    		fh=current;
	    	}
	    }
	    
	    return fh;
	}
	
	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next)
	{
		return goal.distance(current) + next.getBiome().getTravelCost();
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{
		return next.getBiome().getTravelCost();//chm.adjTravelCost(current, next);
	}

}
