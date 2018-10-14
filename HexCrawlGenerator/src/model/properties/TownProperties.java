package model.properties;

//Anything that properties input, the object is expected to use.
public interface TownProperties	
{
	public String getVBiomes(); 
	public int getMax();
	public int getConnectivity();
	public boolean needsRiver(); //if it must spawn near water. 1=must.
	public double getChance();
}
