import java.awt.Point;

public class SetCalculator {
	
	
	//Not even close to done, just doing random stuff
	//x is  % of w, x/w = p, p * (max-min) + min to figure out location on # line
	public static int defaultDisplay(int x, int y, int limit, int h, int w)
	{
		int counter = 0;
		Complex z = new Complex((((x/w)) * ((1.0-(-2.5))+(-2.5))), (((y/h)) * (1.0-(-1.0))+(-1.0)));
		Complex zOrg = z;
		while(counter <= limit)
		{
			if(z.abs() > 2.0)
				return counter;
			z = (z.exp()).plus(zOrg);
			counter++;
			
		}
		return limit;
	}
	

}
