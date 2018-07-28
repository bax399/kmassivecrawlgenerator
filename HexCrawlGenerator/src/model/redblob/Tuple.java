package model.redblob;
import java.util.Arrays;

import java.util.Arrays;

public class Tuple
{
    public final int[] values = new int[3];

    //TODO make this safer
    public Tuple(int[] qr)
    {
    	values[0] = qr[0];
    	values[1] = qr[1];
    	values[2] = -values[0]-values[1];
    }
    
    public Tuple(int q, int r)
    {
    	values[0] = q;
    	values[1] = r;
    	values[2] = -q-r;
    }

    @Override
    public int hashCode()
    {
		int hq = Integer.hashCode(values[0]);
		int hr = Integer.hashCode(values[1]);

		return (hq ^ (hr + 0x9e3779b9 + (hq << 6) + (hq >> 2)));
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