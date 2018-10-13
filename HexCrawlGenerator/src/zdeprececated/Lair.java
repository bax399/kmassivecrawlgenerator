package zdeprececated;

public class Lair
{
	HexMonster owner;
	
	//Lairs and Nomads return and set their HexMonster's descriptor.
	public Monster getMonster()
	{
		return owner.stats;
	}
}
