package zdeprececated;
import java.util.Arrays;

//Retrieved from https://stackoverflow.com/a/5128420
public class Tuple
{
    private int[] values;
    private int hashCode;

    public Tuple(int x, int y, int z)
    {
    	values = new int[3];
        values[0]=x;
        values[1]=y;
        values[2]=z;
        this.hashCode = hashCode(values);
    }

    private static <T> int hashCode(T... values)
    {
        return 31 * Arrays.hashCode(values);
    }

    public Tuple add(Tuple other)
    {
    	for(int ii=0;ii<3;ii++)
    	{
    		other.values[ii]+=values[ii];
    	}
    	
    	return other;
    }
    
    @Override
    public String toString()
    {
    	return values[0] + ", "+values[1] +", "+ values[2];
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