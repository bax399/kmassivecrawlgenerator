package model;
import java.util.*;
import model.redblob.Point;
public class HexMonster extends HasDescriptor{

	public final BaseMonster stats; //what this monster references whenever it is ask for anything.
	private final FilledHex origin; //originating hex
	
	public HexMonster(BaseMonster type, FilledHex ohex)
	{
		super(new WorldDescriptor(type.getName(), type.tags, type.getDescription(), (int)type.getEncounterChance()*100));
		stats = type;
		origin=ohex;
	}
	
	

	//get visibility from a different hex
}
