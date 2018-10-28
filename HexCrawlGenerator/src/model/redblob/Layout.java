package model.redblob;
import java.awt.Polygon;

import model.Point;
public class Layout {
	   public Layout(Orientation orientation, Point size, Point origin)
	    { 
	        this.orientation = orientation;
	        this.size = size;
	        this.origin = origin;
	    }
	    public final Orientation orientation;
	    public final Point size;
	    public final Point origin;
	    static public Orientation pointy = new Orientation(Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0, Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0, 0.5);
	    static public Orientation flat = new Orientation(3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0), 2.0 / 3.0, 0.0, -1.0 / 3.0, Math.sqrt(3.0) / 3.0, 0.0);

	    public Point hexToPixel(Hex h)
	    {
	        Orientation M = orientation;
	        double x = (M.f0 * h.q + M.f1 * h.r) * size.x;
	        double y = (M.f2 * h.q + M.f3 * h.r) * size.y;
	        return new Point(x + origin.x, y + origin.y); 
	    }


	    public FractionalHex pixelToHex(Point p)
	    {
	        Orientation M = orientation;
	        Point pt = new Point((p.x - origin.x) / size.x, (p.y - origin.y) / size.y);
	        double q = M.b0 * pt.x + M.b1 * pt.y;
	        double r = M.b2 * pt.x + M.b3 * pt.y;
	        return new FractionalHex(q, r, -q - r);
	    }


	    public Point hexCornerOffset(int corner)
	    {
	        Orientation M = orientation;
	        double angle = 2.0 * Math.PI * (M.start_angle - corner) / 6;
	        return new Point(size.x * Math.cos(angle), size.y * Math.sin(angle));
	    }

	    //Changed from arrayList -> Polygon for drawing.
	    //MAY NEED TO USE FRACTIONALHEX -> hexRound() IF DRAWING IS OFF
	    public Polygon polygonCorners(Hex h)
	    {
	        Polygon corners = new Polygon();
	        Point center = hexToPixel(h);
	        for (int i = 0; i < 6; i++)
	        {

	            Point offset = hexCornerOffset(i);	        	
	            //corners.add(new Point(center.x + offset.x, center.y + offset.y));
	            corners.addPoint((int)Math.round(center.x+offset.x),  (int)Math.round(center.y+offset.y));
	        }
	        return corners;
	    }
	    
	    public Point pointCorner(Hex h, int ii)
	    {
	    	Point center = hexToPixel(h);
	    	Point offset = hexCornerOffset(ii);
	    	return new Point(center.x+offset.x, center.y+offset.y);
	    }
}
