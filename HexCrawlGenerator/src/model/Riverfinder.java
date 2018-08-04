package model;
import java.util.*;
public class Riverfinder extends Pathfinder {

	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next)
	{
		return /*goal.distance(current)+*/getCost(chm,current,next);
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) 
	{
		int heightdiff;
		heightdiff = next.getBiome().getHeight() - (current.getBiome().getHeight());
		//heightdiff = Math.max(heightdiff, 0);
		heightdiff *= 100;
		return Math.max(heightdiff,1);//+Math.max(0,chm.adjTravelCost(current,next)-5); //minus river travel cost...
	}

}
