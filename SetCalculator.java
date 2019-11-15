import java.awt.Point;

public class SetCalculator {
	
	
	//Not even close to done, just doing random stuff
	//x is  % of w, x/w = p, p * (max-min) + min to figure out location on # line
	public int defaultDisplay(Point z, Point z0, int limit, int counter, int h, int w)
	{
		
		if(counter > limit)
		{
			return counter;
		}
		z = z^2 + z0;
		return defaultDisplay(z, z0, limit, counter++, h, w);
	}
	

}
