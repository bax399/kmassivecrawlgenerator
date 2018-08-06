package model;
import java.util.*;
public class Townfinder extends Pathfinder {

	@Override
	public FilledHex earlyTermination(ConnectedHexMap chm, FilledHex start, FilledHex current)
	{
		FilledHex fh = null;
		//Terminate if you hit a town not in your set
    	if(current.getLargestTown() !=null)
    	{
    		fh=current;
    	}
	    
	    return fh;
	}
	
	
	
	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next)
	{
		return goal.distance(current)+getCost(chm,current,next);
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{
		return chm.adjTravelCost(current, next);
	}

}
