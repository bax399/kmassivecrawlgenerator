package model;

public class Townfinder extends Pathfinder {

	
	@Override
	public FilledHex earlyGreedyTermination(ConnectedHexMap chm, FilledHex start, FilledHex current)
	{
		return earlyDijkstraTermination(chm,start,current);
	}
	
	@Override
	public FilledHex earlyDijkstraTermination(ConnectedHexMap chm, FilledHex start, FilledHex current)
	{
		FilledHex fh = null;
    	if(current.getLargestTown() !=null && start.getRoadNode().getNetwork().equals(current.getRoadNode().getNetwork()))
    	{
    		fh=current;
    	}
    	
//    	if (current.getRoadNode() != null)
//    	{
//    		fh=current;
//    	}
	    
	    return fh;
	}
	
	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next)
	{
		int roadBetween = 0;
		if (next.getRoadNode() != null && next.getLargestTown() == null)
		{
			roadBetween = 1;
		}		
		return goal.distance(current) + next.getHabitat().getTravelCost() - 10000*roadBetween;
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{
		return next.distance(current) + next.getHabitat().getTravelCost();//chm.adjTravelCost(current, next);
	}

}
