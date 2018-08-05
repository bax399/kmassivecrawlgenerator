package model;
import java.util.*;
public class BiomeModifierGeneration extends BiomeModifier implements BiomeModifierGenerationProperties
{
	public static BiomeModifierGeneration coastal = new BiomeModifierGeneration("coastal", new int[] {1,2,3},-2,0,"",0d,0d,0.1d,
													"ocean,reef","all");
	public static BiomeModifierGeneration raised = new BiomeModifierGeneration("mountainous",new int[] {1,2,3},2,5,"",0d,0d,0.1d,
													"mountains,hills","all");
	public static BiomeModifierGeneration forested = new BiomeModifierGeneration("forested",new int[] {1,2,3},0,2,"",0d,0d,0.1d,
													"forest","all");
	
	
	private Set<Biome> validbiomes = new HashSet<>();
	private Set<Biome> originbiomes = new HashSet<>();
	
	private final String vbiomes; //Biomes that can be applied to
	private final String obiomes; //Biomes that can apply
	private final double originchance;

	public BiomeModifierGeneration(String n, int[] c, int h, int tc, String sd, double ro, double re, double oc, String ob, String vb)
	{
		super(n,c,h,tc,sd,ro,re);
		originchance = oc;
		vbiomes=vb;
		obiomes=ob;
	}
	
	public BiomeModifierGeneration(BiomeModifierGeneration bm, Biome nextb)
	{
		super(bm,nextb);
		originchance=bm.originchance;
		vbiomes=bm.vbiomes;
		obiomes=bm.obiomes;		
	}
		
	
	public BiomeModifierGeneration(Properties pp)
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
	public Set<Biome> getOriginBiomes()
	{
		return originbiomes;
	}	
	
	@Override
	public Set<Biome> getValidBiomes()
	{
		return validbiomes;
	}
		
}
