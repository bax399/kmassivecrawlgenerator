package zdeprececated;
import model.FilledHex;
import model.WorldDescriptor;
import model.merowech.ConcaveHull.Point;
public class HexMonster extends WorldDescriptor{

	public final Monster stats; //what this monster references whenever it is ask for anything.
	private final FilledHex origin; //originating hex
	private final Point pos; //final point.
	
	public HexMonster(Monster type, FilledHex ohex, Point rpoint)
	{
		super(type.getName(), type.tags, type.getDescription(), (int)type.getEncounterChance()*100);
		stats = type;
		origin=ohex;
		pos = rpoint;
		
	}
	
	

	//get visibility from a different hex
}
