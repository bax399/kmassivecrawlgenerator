package model;
import java.util.*;
public class Monster extends HasDescriptor implements MonsterProperties {
	public static final String[] setvalues = {"monster"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));
	
	public Monster(WorldDescriptor wd) {
		super(wd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processProperties(Properties props) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getVisibility() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNomadChance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRoamRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpawnChance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRoamType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEncounterChance() {
		// TODO Auto-generated method stub
		return 0;
	}

}
