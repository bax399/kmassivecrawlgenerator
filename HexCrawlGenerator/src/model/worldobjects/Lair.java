package model.worldobjects;
import java.util.*;
import model.*;
public class Lair
{
	HexMonster owner;
	
	//Lairs and Nomads return and set their HexMonster's descriptor.
	public BaseMonster getMonster()
	{
		return owner.stats;
	}
}
