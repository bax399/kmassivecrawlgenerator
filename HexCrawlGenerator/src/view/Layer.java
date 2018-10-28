package view;
import java.util.*;

public enum Layer 
{
	
	NOMADICRADIUS (22),
	NOMADMONSTER (20),
	
	DUNGEONRADIUS (21),
	LAIR (20),
	
	ROAD (15),
	TOWN (15),
	
	RIVER (10),
	
	BIOME (5),
	
	HEX (2),
	
	MAP (1);

	private final Integer layer;
	
    private Layer(Integer index)
	{
		this.layer = index;
	}
}
