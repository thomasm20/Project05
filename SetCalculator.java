import java.awt.Point;

public class SetCalculator {
	
	private static double xMin;
	private static double xMax;
	private static double yMin;
	private static double yMax;
	
	public SetCalculator(double xMin, double xMax, double yMin, double yMax)
	{
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	//Not even close to done, just doing random stuff
	//x is  % of w, x/w = p, p * (max-min) + min to figure out location on # line
	public static int defaultDisplay(int x, int y, int limit, int h, int w)
	{
		int counter = 0;
		Complex z = new Complex(((x/((double)w)) * (xMax-xMin))+(xMin), ((y/((double)h)) * (yMax-yMin))+(yMin));
		Complex zOrg = new Complex(((x/((double)w)) * (xMax-xMin))+(xMin), ((y/((double)h)) * (yMax-yMin))+(yMin));
		while(counter < limit)
		{
			if(z.abs() > 2.0)
				return counter;
			z = ((z.times(z)).plus(zOrg));
			counter++;
		}
			
			
			return limit;
	}
	
	public static int juliaDisplay(int x, int y, int limit, int h, int w)
	{
		int counter = 0;
		Complex z = new Complex(((x/((double)w)) * (xMax-xMin))+(xMin), ((y/((double)h)) * (yMax-yMin))+(yMin));
		Complex c = new Complex(-0.1, 0.65);
		while(counter < limit)
		{
			if(z.abs() > 2.0)
				return counter;
			z = ((z.times(z)).plus(c));
			counter++;
		}
			
			
			return limit;
	}
	public void updateDisplay(double xMi, double xMa, double yMi, double yMa, double width, double height) {
		
		Complex topLeft = new Complex(((xMi/((double)width)) * (xMax-(xMin)))+(xMin), ((yMi/((double)height)) * (yMax-(yMin)))+(yMin));
		Complex bottomRight = new Complex(((xMa/((double)width)) * (xMax-(xMin)))+(xMin), ((yMa/((double)height)) * (yMax-(yMin)))+(yMin));
		xMin = topLeft.re();
		yMax = bottomRight.im();
		xMax = bottomRight.re();
		yMin = topLeft.im();
		
		
	}
	public double getXMIN()
	{
		return xMin - (xMin % 0.01);
	}
	public double getXMAX()
	{
		return xMax - (xMax % 0.01);
	}
	public double getYMIN()
	{
		return yMin - (yMin % 0.01);
	}
	public double getYMAX()
	{
		return yMax - (yMax % 0.01);
	}
}
	


