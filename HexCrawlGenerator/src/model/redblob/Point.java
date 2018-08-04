package model.redblob;

//Refactor to use awt.Point, as it has all functionality + base implementation.
public class Point{
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public final double x;
    public final double y;

    public Point scalarMultiple(double ratio)
    {
    	return new Point(this.x*ratio,this.y*ratio);
    }
    
}
