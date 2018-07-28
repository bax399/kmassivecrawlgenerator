package model;
import java.util.*;
public class Riverfinder extends Pathfinder {

	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next)
	{
		return goal.distance(current)+getCost(chm,current,next);
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) {
		
		return chm.adjTravelCost(current,next);
	}

}
