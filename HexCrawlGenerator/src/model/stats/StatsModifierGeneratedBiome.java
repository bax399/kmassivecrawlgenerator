package model.stats;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import model.properties.BaseBiome;
import model.properties.BiomeModifierGeneratedProperties;
public class StatsModifierGeneratedBiome extends StatsModifierBiome implements BiomeModifierGeneratedProperties
{
	public static StatsModifierGeneratedBiome coastal = new StatsModifierGeneratedBiome("coastal", new int[] {1,2,3},-2,0,"",0d,0d,0.1d,
													"ocean,reef","all");
	public static StatsModifierGeneratedBiome raised = new StatsModifierGeneratedBiome("mountainous",new int[] {1,2,3},2,5,"",0d,0d,0.1d,
													"mountains,hills","all");
	public static StatsModifierGeneratedBiome forested = new StatsModifierGeneratedBiome("forested",new int[] {1,2,3},0,2,"",0d,0d,0.1d,
													"forest","all");
	
	//How are these defined? Functionally these can be input via files instead.
	
	private Set<BaseBiome> validbiomes = new HashSet<>();
	private Set<BaseBiome> originbiomes = new HashSet<>();
	
	private final String vbiomes; //Biomes that can be applied to
	private final String obiomes; //Biomes that can apply
	private final double originchance;

	public StatsModifierGeneratedBiome(String n, int[] c, int h, int tc, String sd, double ro, double re, double oc, String ob, String vb)
	{
		super(n,c,h,tc,sd,ro,re);
		originchance = oc;
		vbiomes=vb;
		obiomes=ob;
	}
	
	public StatsModifierGeneratedBiome(StatsModifierGeneratedBiome bm, BaseBiome nextb)
	{
		super(bm,nextb);
		originchance=bm.originchance;
		vbiomes=bm.vbiomes;
		obiomes=bm.obiomes;		
	}
		
	
	public StatsModifierGeneratedBiome(Properties pp)
	{
		super(pp);
		originchance=Double.parseDouble(pp.getProperty("originchance"));
		vbiomes=pp.getProperty("validbiomes");
		obiomes=pp.getProperty("originbiomes");
	}
	
	
	
	@Override
	public String getOBiomes() {
		return obiomes;
	}
	
	@Override
	public String getVBiomes() {
		return vbiomes;
	}	
	
	@Override
	public double getOriginChance()
	{
		return originchance;
	}

	@Override
	public Set<BaseBiome> getOriginBiomes()
	{
		return originbiomes;
	}	
	
	@Override
	public Set<BaseBiome> getValidBiomes()
	{
		return validbiomes;
	}
		
}
