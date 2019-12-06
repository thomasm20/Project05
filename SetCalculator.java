import java.awt.Point;

/**
 * A <code>SetCalculator</code> class that creates
 * 
 *
 * @author JosephSalerno
 * @author BrendanOlski
 * @author MitchellThomas
 *
 * Class: SetCalculator.java
 * Project: 5
 */

public class SetCalculator {
	
	private static double xMin;
	private static double xMax;
	private static double yMin;
	private static double yMax;
	
	/**
	 * Constructor method
	 * @param xMin - 
	 * @param xMax - 
	 * @param yMin - 
	 * @param yMax - 
	 */
	public SetCalculator(double xMin, double xMax, double yMin, double yMax)
	{
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	/**
	 * 
	 *
	 * @param x - 
	 * @param y - 
	 * @param limit - 
	 * @param h - 
	 * @param w - 
	 * @return limit
	 */
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
	
	/**
	 * 
	 *
	 * @param x - 
	 * @param y - 
	 * @param limit - 
	 * @param h - 
	 * @param w - 
	 * @return limit
	 */
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
	
	/**
	 * 
	 *
	 * @param xMi - 
	 * @param xMa - 
	 * @param yMi - 
	 * @param yMa - 
	 * @param width - 
	 * @param height - 
	 * @return
	 */
	public void updateDisplay(double xMi, double xMa, double yMi, double yMa, double width, double height) {
		
		Complex topLeft = new Complex(((xMi/((double)width)) * (xMax-(xMin)))+(xMin), ((yMi/((double)height)) * (yMax-(yMin)))+(yMin));
		Complex bottomRight = new Complex(((xMa/((double)width)) * (xMax-(xMin)))+(xMin), ((yMa/((double)height)) * (yMax-(yMin)))+(yMin));
		xMin = topLeft.re();
		yMax = bottomRight.im();
		xMax = bottomRight.re();
		yMin = topLeft.im();	
	}
	
	/**
	 * Returns xMin for display of the canvas
	 * @return xMin
	 */
	public double getXMIN() {
		return xMin - (xMin % 0.01);
	}
	
	/**
	 * Returns xMax for display on the canvas
	 * @return xMax
	 */
	public double getXMAX() {
		return xMax - (xMax % 0.01);
	}
	
	/**
	 * Returns yMin for display on the canvas
	 * @return yMin
	 */
	public double getYMIN() {
		return yMin - (yMin % 0.01);
	}
	
	/**
	 * Returns yMax for display on the canvas
	 * @return yMax
	 */	
	public double getYMAX() {
		return yMax - (yMax % 0.01);
	}
}
	


