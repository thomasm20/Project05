import java.awt.Point;

/**
 * A <code>SetCalculator</code> class that calculates the 
 * Mandelbrot and Julia display to be input in the frame.
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
	 * Constructor method for SetCalculator class
	 * @param xMin - minimum x coordinate
	 * @param xMax - maximum x coordinate
	 * @param yMin - minimum y coordinate
	 * @param yMax - maximum y coordinate
	 */
	public SetCalculator(double xMin, double xMax, double yMin, double yMax)
	{
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	/**
	 * Method that calculates the default display for the Mandelbrot
	 *
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param limit - limit
	 * @param h - height
	 * @param w - width
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
	 * Method that calcualtes the Julia display
	 *
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param limit - limit
	 * @param h - height
	 * @param w - width
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
	 * Method that calcualtes any updates to the display
	 *
	 * @param xMi - minimum x coordinate
	 * @param xMa - maximum x coordinate
	 * @param yMi - minimum y coordinate
	 * @param yMa - maximum y coordinate
	 * @param width - with
	 * @param height - height
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
	 * @return minimum x coordinate
	 */
	public double getXMIN() {
		return xMin - (xMin % 0.01);
	}
	
	/**
	 * Returns xMax for display on the canvas
	 * @return maximum x coordinate
	 */
	public double getXMAX() {
		return xMax - (xMax % 0.01);
	}
	
	/**
	 * Returns yMin for display on the canvas
	 * @return minimum y coordinate
	 */
	public double getYMIN() {
		return yMin - (yMin % 0.01);
	}
	
	/**
	 * Returns yMax for display on the canvas
	 * @return maximum y coordinate
	 */	
	public double getYMAX() {
		return yMax - (yMax % 0.01);
	}
}
	


