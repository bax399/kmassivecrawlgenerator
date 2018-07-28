package model;
import java.util.*;
public class Riverfinder extends Pathfinder {

	@Override
	public int heuristic(ConnectedHexMap chm, FilledHex current, FilledHex next)
	{
		return next.getHeight()-current.getHeight();
	}
	
	@Override
	public int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next) {
		
		return chm.adjTravelCost(current,next);
	}

}
