import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.lang.Thread;
import java.util.List;
import java.util.ArrayList;


/**
 * A <code>Canvas</code> class that draws the pixels stored in the BufferedImage variable image in chunks.
 * Yields thread control for visual updates between chunks.
 * Selects a 3.5:2 rectangle when clicked on, or 1:1 depending on the set
 * 
 * @author Joseph Salerno
 * @author Brendan Olski
 * @author Mitchell Thomas
 *
 * Class: Canvas.java
 * Project: 5
 */
public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
   
   
   // Variables
   private boolean doneRendering;
   private int renderX, renderY;
   private int width, height;
   private Point posStart;
   private Point posEnd;   
   private Rectangle drawRect;
   private BufferedImage image;
   private Graphics2D gImg;
   private double scale;
   
   public RainbowGradient gradient;
   public boolean switched;
   public int limit;
   public SetCalculator calc;
   public String currentGradient;
   public String currentSet;
   
   // Final variables
   final private Color colorSelect = new Color(0, 200, 200);
   final private int chunkSize = 50;
   
   /**
    * Default constructor for the canvas. Sets the scale to 1.
    */   
   public Canvas() {
      super();
      
      scale = 1;
      
     
      
      setup();
      
      setupCanvas();
      
      // Start the first render
      resetRender(); 
   }
   
   /**
    * Scaled constructor for the canvas. Sets the scale to the parameter passed in.
    * @param scale - how much to scale up the canvas from the default size of 350 by 200
    */   
   public Canvas(double scale) {
      super();
      
      this.scale = scale;
      
      
      setup();
      
      setupCanvas();
      
      // Start the first render
      resetRender();
      
      
   }
   
   /**
    * Method to set up certain variables. Kept separate from the constructor so that 
    * setupCanvas and resetRender can be used elsewhere.
    * 
    */
   private void setup() {
      
      // Initial state of variables
      doneRendering = false;
      
      renderX = 0;
      renderY = 0;
      limit = 32;
      currentGradient = "Original Gradient";
      gradient = new RainbowGradient(limit);
      currentSet = "Mandelbrot Set";
      calc = new SetCalculator(-2.5, 1.0, -1.0, 1.0);
      switched = false;
      drawRect = null;
      
      // Listen for mouse movement or input
      addMouseListener(this);      
      addMouseMotionListener(this);
      
   }
   
   /**
    * Method to create the images for the canvas
    * 
    */
   public void setupCanvas() {
      // Solid dimensions
      width = (int)(350 * scale);
      height = (int)(width * (2 / 3.5));
	 
      
      // Image which is drawn upon
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      gImg = image.createGraphics();
      
      
   }
   
   /**
    * Overridden paintComponent to draw the BufferedImage variable to the panel
    * @param g - Graphics variable linked to this panel
    * 
    */
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      // Draw the image so far
      g.drawImage(image, 0, 0, width, height, 0, 0, width, height, null);
      
      // Draw drag rectangle if it is there
      if (drawRect != null) {
         g.setColor(colorSelect);
         g.drawRect((int)drawRect.getX(), (int)drawRect.getY(),
                    (int)drawRect.getWidth(), (int)drawRect.getHeight());
      }
      
   }
   
   // Methods needed for mouse listeners but not needed to implement
   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }
   
   @Override
   public void mouseMoved(MouseEvent e) {
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      
   }
   
   
   /**
    * Method to detect click on the canvas. Sets up a position start and end and invokes {@link #updateRectangle()} 
    * to update the drag rectangle dimensions.
    * @param e - Mouse event that occured
    * 
    */
   public void mousePressed(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
         posStart = new Point(e.getX(), e.getY());
         posEnd = new Point(e.getX(), e.getY());
         updateRectangle();
      }
   }

   /**
    * Method to detect the mouse button is no longer held down. Frees up the drag variables and invokes {@link #resetRender()}.
    * @param e - Mouse event that occured
    * 
    */
   public void mouseReleased(MouseEvent e) {
      posEnd.setLocation(e.getX(), e.getY());
      updateRectangle();
      
      // Resize the viewing area here, check if rectangle is too small first
      if(drawRect.getWidth() < 0.1)
    	  System.out.println("Too small");
      else {
      calc.updateDisplay(drawRect.getX(), drawRect.getX()+drawRect.getWidth(),
    		  drawRect.getY(), drawRect.getY()+drawRect.getHeight(), width, height);
      
      //for display
      Mandelbrot.appFrame.positionDisplay.setText(("Ranges:\t     x: [" + calc.getXMIN() + "," + calc.getXMAX()  + " ]"
       		+ "    y: [" + calc.getYMIN() + ", " + calc.getYMAX() + " ]"));
      }
	   
      // Free up the draw variables
      drawRect = null;
      posStart = null;
      posEnd = null;
      
      // Start the drawing process over again if we're not already rendering.
      if (doneRendering) {
         
         resetRender();
      }
         
   }
   
   /**
    * Method to detect mouse movement on the canvas. Invokes {@link #updateRectangle()}.
    * @param e - Mouse event that occured
    * 
    */   
   public void mouseDragged(MouseEvent e) {
      if (drawRect != null) {
         posEnd.setLocation(e.getX(), e.getY());
         updateRectangle();
      }
   }

   
   /**
    * Method which updates the drag rectangle. Maintains a ratio of 3.5:2.
    * 
    */
   public void updateRectangle() {
      int distX, distY;
      
      // If we don't have a drag rectangle already, make one
      if (drawRect == null) {
         drawRect = new Rectangle(0, 0, 0, 0);
      }
      
      int width = (int)Math.abs(posEnd.getX() - posStart.getX());
      int height = (int)Math.abs(posEnd.getY() - posStart.getY());
      int left = (int)Math.min(posStart.getX(), posEnd.getX());
      int top = (int)Math.min(posStart.getY(), posEnd.getY());
      
      // Calculate Y-value based on x and ratio, check if Mandelbrot/Julia for rectangle dimensions
      if(currentSet.equals("Mandelbrot Set"))
      {
      distX = Math.abs(width);
      distY = (int)(distX * (2 / 3.5)); 
      }
      else
      {
    	  distX = Math.abs(width);
          distY = distX;   
      }
      
      // Set up rectangle to the correct four corners
      drawRect.setLocation(left, top);
      
      drawRect.setSize(distX,
                       distY);
      
      
      // Let paintComponent handle this later
      repaint();
   }
   
   /**
    * Method which resets the chunk rendering. Clears out the canvas with black before invoking {@link #renderAll()}.
    * 
    */   
   public void resetRender() {
       //check if switched to Julia and vice versa
       if(!switched && currentSet.equals("Julia Set"))
	   switchToJulia();
       if(switched && currentSet.equals("Mandelbrot Set"))
       	   switchBackToMandelbrot();
       
       //update gradient to new limit
       gradient.stateOfLimit(limit);
       
       //switch gradient to new gradient type
       if(currentGradient.equals("Rainbow Gradient"))
    	   gradient.switchToRainbow();
       else if(currentGradient.equals("Greenscale Gradient"))
    	   gradient.switchToGreenscale();
       else if(currentGradient.equals("Greyscale Gradient"))
    	   gradient.switchToGreyscale();
       
      // Start the rendering over again, set current x,y render chunk to 0      
      renderX = 0;
      renderY = 0;
      doneRendering = false;
            
      // Clear out whatever is on the image
      gImg.setPaint(Color.BLACK);
      gImg.fillRect(0, 0, width , height );
      
      // Start up the render
      renderAll();
      
      // Re-draw the panel
      repaint();

   }
   
   /**
    * Method which renders chunks of an image at a time. Yields control of the thread for visualization of each chunk.
    * 
    */
   public void renderAll() {
      
      // Continue until the entire image is done
      while (!doneRendering) {
         try { 
             // relinquish control 
             Thread.yield();
             
         } 
         // Catch anything that goes wrong
         catch (Exception e) { 
             System.out.println(e); 
         }
         
         // Render next chunk
         render();
      
      }
    
   }
   
   /**
    * Method that renders the next chunk.
    * 
    */ 
   private void render() {
      
      // Variables
      Color color;
      List<Color> g = gradient.getColors();
      int val;
      // If we're not done with the entire image...
      if (!doneRendering) {
         // Iterate over each pixel in the render chunk
         for(int x = renderX; x < renderX + chunkSize; x++) {
             for(int y = renderY; y < renderY + chunkSize; y++) {
            	 
            	 if(currentSet.equals("Mandelbrot Set"))
            		  val = calc.defaultDisplay(x, y, limit, height, width);
            	 else 
            		  val = calc.juliaDisplay(x, y, limit, height, width);
            	 
            	 
               // Set the pixel in the image to the appropriate color
            	if(currentGradient.equals("Original Gradient"))
            	{
            		if(val == limit)
            			image.setRGB(x, y, Color.black.getRGB());
            		else
            			image.setRGB(x,  y, Color.white.getRGB());
            	}
            	else
            	{
            		color = g.get(val);
            		image.setRGB(x, y, color.getRGB());
            	}
            	
             
             }
         }
         
         
         // Move to next chunk
         renderX += chunkSize;
         if (renderX >= width ) {
            renderX = 0;
            renderY += chunkSize;
         }
         
         // If our next y-coordinate is off the end of the image then the entire image is rendered
         if (renderY >= height ) {
            doneRendering = true;
            renderX = 0;
            renderY = 0;
         }
      }
      
         
      
      // Paint NOW to force the chunk visualization
      paintImmediately(0, 0, width, height);
      
   }

   
   /**
    * Method that switches setCalculator to mins/maxes pertaining to Julia Set, and switches boolean to true as a check
    *
    */
   public void switchToJulia()
   {
	   switched = true;
	   calc = new SetCalculator(-1.5, 1.5, -1.5, 1.5);
   }
 
   /**
    * Method that switches setCalculator to mins/maxes pertaining to Mandelbrot Set, and switches boolean to false as a check
    *
    */
   public void switchBackToMandelbrot()
   {
	   switched = false;
	   calc = new SetCalculator(-2.5, 1.0, -1.0, 1.0);
   }
   
}

/**
 * A <code>RainbowGradient</code> class that establishes the options for the list of seed colors, and given n, the limit
 * of total colors in the gradient, blends the colors in between each seed color to create a gradient.
 * 
 * @author Joseph Salerno
 * @author Brendan Olski
 * @author Mitchell Thomas
 *
 * Class: RainbowGradient.java
 * Project: 5
 */
class RainbowGradient {
	
	// Defines the list of seed colors
	private Color[] test = new Color[] {Color.RED, Color.GREEN, Color.BLUE};
	
	private Color[] greyscale = new Color[] {Color.BLACK, Color.WHITE};
	
	private Color[] greenscale = new Color[] {Color.BLACK, Color.GREEN};
	
	private Color[] rainbow = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
	
	// Defines the list of all gradients 
	private List<Color> colors = new ArrayList<Color>();
	
	private int n;
	
	private Color[] current = null;
	
	
	
	// The singleton instance variable
	private static RainbowGradient instance;
	
       /**
        * Method that creates or gets an instance of RainbowGradient and establishes a default limit value.
        * @return an instance of RainbowGradient
        */
	public static RainbowGradient getInstance() {
	   if (instance == null) {
	      instance = new RainbowGradient(32);
	   }
	      
	   return instance;
	}


       /**
    	* Constructor method for RainbowGradient class
    	* @param n - int variable for the number of gradient colors
   	*/  
	public RainbowGradient(int n) {
		this.n = n;
		
		this.current = rainbow;
		
		colorGradientMaker();
	}
	
       /**
    	* Method that sets the value of current to greyscale.
    	*
   	*/
	public void switchToGreyscale()
	{
		current = greyscale;
		colorGradientMaker();
	}
	
       /**
    	* Method that sets the value of current to greenscale.
    	*
   	*/
	public void switchToGreenscale()
	{
		current = greenscale;
		colorGradientMaker();
	}
	
	
       /**
    	* Method that sets the value of current to rainbow.
    	*
   	*/
	public void switchToRainbow()
	{
		current = rainbow;
		colorGradientMaker();
	}
	
       /**
        * Method that updates the limit and then runs the colorGradientMaker method to update it there as well.
        * @param limit - int variable that 
	*
   	*/
	public void stateOfLimit(int limit) {
		this.n = limit;
		colorGradientMaker();
	  }
	
       /**
        * Method that sets the RGB values for the color in the adjacent index of the gradient given the values of the 
	* previous index and the distance away from the next seed color. 
        *
   	*/
	public void colorGradientMaker() {
		double numColors = (n-1);
		double shadesPerColor = (int)(numColors/(current.length-1));
		int max = 255;
		int min = 0;
		colors.clear();
		// keep appending items for each seed color
		// n is the number of gradient colors, test is the number of seed colors
		
		for (int i = 0; i < n; i++) {

			int shade = (int)(i/shadesPerColor);	
			int iModded = (i % ((int) shadesPerColor));
			double dist = (iModded/shadesPerColor);
			double red1 = current[shade].getRed();
			double green1 = current[shade].getGreen();
			double blue1 = current[shade].getBlue();
			double red2 = current[(Math.min(shade + 1,current.length-1))].getRed();
			double green2 = current[(Math.min(shade + 1,current.length-1))].getGreen();
			double blue2 = current[(Math.min(shade + 1,current.length-1))].getBlue();
			double redDiff = red2 - red1;
			double greenDiff = green2 - green1;
			double blueDiff = blue2 - blue1;

			
			red1 = Math.min(Math.max(red1 + (redDiff * dist),  min), max);
		

			green1 = Math.min(Math.max(green1 + (greenDiff * dist), min), max);



			blue1 = Math.min(Math.max(blue1 + (blueDiff * dist), min), max);
					
			colors.add(new Color((int) red1, (int) green1, (int) blue1));
			//System.out.print(current[shade]);
		}
		colors.add(new Color(0,0,0));

	}
	
       /**
    	* Method that retrieves the full gradient of colors.
    	* @return the color gradient
   	*/
	public List<Color> getColors()
	{
		return colors;
	}
	
	
}
	



