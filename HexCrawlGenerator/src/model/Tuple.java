package model;
import java.util.Arrays;

//Retrieved from https://stackoverflow.com/a/5128420
public class Tuple
{
    private int[] values;
    private int hashCode;

    public Tuple(int x, int y, int z)
    {
        values[0]=x;
        values[1]=y;
        values[z]=z;
        this.hashCode = hashCode(values);
    }

    private static <T> int hashCode(T... values)
    {
        return 31 * Arrays.hashCode(values);
    }

    public Tuple add(Integer... adds)
    {
    	for(int ii=0;ii<3;ii++)
    	{
    		adds[ii]+=values[ii];
    	}
    	
    	return new Tuple(adds[0],adds[1],adds[2]);
    }
    
    @Override
    public int hashCode()
    {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (!(obj instanceof Tuple)) return false;
        Tuple other = (Tuple) obj;
        if (!Arrays.equals(values, other.values)) return false;
        return true;
    }
}