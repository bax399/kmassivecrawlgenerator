package model;

import java.util.Random;

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
		Random rand = new Random();

		prioritiseWater=(int)(next.getHabitat().getRiverEnd());
		//dist up = towards goal
		return goal.distance(next)*20 + Math.max((next.getHabitat().getHeight()-current.getHabitat().getHeight())*1,0) - prioritiseWater + rand.nextInt(5)*5;
	}
		
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{
		int heightdiff;
		heightdiff = next.getHabitat().getHeight() - (current.getHabitat().getHeight());
		//heightdiff = Math.max(heightdiff, 0);
		return Math.max(heightdiff,-1)*10+1;//+Math.max(0,chm.adjTravelCost(current,next)-5); //minus river travel cost...
	}

}
