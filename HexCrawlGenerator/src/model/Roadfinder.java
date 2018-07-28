package model;
import java.util.*;
public class Roadfinder extends Pathfinder {

	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex current, FilledHex next)
	{
		return getCost(chm,current,next);
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) {
		
		return chm.adjTravelCost(current,next);
	}

}
