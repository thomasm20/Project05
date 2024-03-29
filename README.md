# Project05
Project with the purpose of displaying the Mandelbrot Set in an interactive GUI, allowing zooming and color changing by the user.

Project 5
CS 209 Fall 2019
By Mitchell Thomas, Joe Salerno, and Brendan Olski

===Canvas===
Canvas.java contains a Canvas class that draws the pixels stored in the BufferedImage variable image in chunks and yields thread control for visual updates between chunks.

--Variables--
doneRendering: private boolean variable that tells the program whether or not the image is complete
renderX: private int variable stores the largest x coordinate that has been rendered
renderY: private int variable stores the largest y coordinate that has been rendered
width: private int variable sets the width of the image frame
height: privat int variable sets the height of the image frame
posStart: private Point establishes the initial coordinate point to start the new image frame
posEnd: private Point establishes the final coordinate point to end the new image frame
drawRect: private Rectangle tells the program whether or not the drag rectangle is being used
image: private BufferedImage stores the picture to be drawn
gImg: private Graphics2D draws the image
scale: private double variable determines how much to scale up the canvas from the default size of 350x200
gradient: private RainbowGradient used to update the gradient to a new limit or switch the set of seed colors

switched: public boolean stores whether or not the set has been switched between Mandelbrot and Julia
limit: public int variable stores the number of color indices to be included in the color gradient
calc: public SetCalculator stores the display coordinates for the desired set
currentGradient: public String variable stores the current arraylist chosen to represent the seed colors
currentSet: public String variable stores which set has been selected, either Mandelbrot or Julia

colorSelect: final private Color sets the color of the outline of the drag rectangle
chunkSize: final private int variable sets the size of each chunk for rendering

--Methods--
Canvas() - Default constructor for the canvas. Sets the scale to 1.
Canvas(double scale) - Scaled constructor for the canvas. Sets the scale to the parameter passed in
setup() - Method to set up certain variables. Kept separate from the constructor so that setupCanvas and resetRender can be used elsewhere
setupCanvas() - Method to create the images for the canvas
paintComponent(Graphics g) - Overridden paintComponent to draw the BufferedImage variable to the panel
mousePressed(MouseEvent e) - Method to detect click on the canvas. Sets up a position start and end and invokes {@link #updateRectangle()} to update the drag rectangle dimensions
mouseReleased(MouseEvent e) - Method to detect the mouse button is no longer held down. Frees up the drag variables and invokes {@link #resetRender()}
mouseDragged(MouseEvent e) - Method to detect mouse movement on the canvas. Invokes {@link #updateRectangle()}
updateRectangle() - Method which updates the drag rectangle. Maintains a ratio of 3.5:2
resetRender() - Method which resets the chunk rendering. Clears out the canvas with black before invoking {@link #renderAll()}
renderAll() - Method which renders chunks of an image at a time. Yields control of the thread for visualization of each chunk
render() - Renders the next chunk
switchtoJulia() - Switches setCalculator to mins/maxes pertaining to Julia set and switches boolean to true as a check
switchBackToMandelbrot() - Switches setCalculator to mins/maxes pertaining to Mandelbrot set and switches boolean to false as a check

===RainbowGradient===
RainbowGradient is a singleton class that returns a color based on the integer given to it, which is between 0 and limit-1. It works to add color to the Mandelbrot/Julia set.

--Variables--
greyscale: private list variable for the list of seed colors for greyscale gradient
greenscale: private list variable for the list of seed colors for greenscale gradient
rainbow: private list variable for the list of seed colors for rainbow gradient
colors: private list of Color objects that defines the list of all color gradients
n: private int variable that represents the number of gradient colors
current: private list variable for the list of seed colors for current gradient selected
instance: the private static singleton instance variable of type RainbowGradient

--Methods--
getInstance() - Method that creates or gets an instance of RainbowGradient and establishes a default limit value.
RainbowGradient(int n) - Constructor method for RainbowGradient class 
stateOfLimit(int limit) - Method that updates the limit and then runs the colorGradientMaker method to update it in there too
colorGradientMaker() - Method that sets the RGB values for the color in the adjacent index of the gradient given the values of the previous index and the distance away from the next seed color.
getColors() - method to retrieve the colors
switchToGreyscale() - method that sets the value of current to grey
switchToGreenscale() - method that sets the value of current to green
switchToRainbow() - method that sets the value of current to rainbow

===Complex===
Complex.java contains a Complex class that creates mathematical function methods.

--Variables--
re: private final double variable that represents the real part
im: private final double variable that represents the imaginary part

--Methods--
Complex(double real, double imag) - Constructor method that creastes a new object with the given real and imaginary parts
toString() - returns a string representation of the invoking Complex object
abs() - returns abs/modulus/magnitude
phase() - return angle/phase/argument, normalized to be between -pi and pi
plus(Complex b) - return a new Complex object whose value is (this + b)
minus(Complex b) - return a new Complex object whose value is (this - b)
times(Complex b) - return a new Complex object whose value is (this * b)
divides(Complex b) - return a / b
scale(double alpha) - return a new object whose value is (this * alpha)
conjugate() - return a new Complex object whose value is the conjugate of this
reciprocal - return a new Complex object whose value is the reciprocal of this
re() - return the real part
im() - return the imaginary part
exp() - return a new Complex object whose value is the complex exponential of this
sin() - return a new Complex object whose value is the complex sine of this
cos() - return a new Complex object whose value is the complex cosine of this
tan() - return a new Complex object whose value is the complex tangent of this
plus(Complex a, Complex b) - a static version of plus

equals(Object x) - return true/false if equal to Object x
hashCode() - returns the integer hash code value of the object

main(String[] args) - sample client for testing

===Mandelbrot===
Mandelbrot.java contains a Mandelbrot class that extends JFrame and implements ActionListener to set up the Mandelbrot interactive GUI frame with buttons and other items that respond to actions by the user.

--Variables--
canvas: private variable of type Canvas 
increaseButton: private JButton variable for the increase limit button in the main frame
decreaseButton: private JButton variable for the decrease limit button in the main frame
resetButton: private JButton variable for the reset button in the main frame
saveImaButton: private JButton variable for the save image button in the main frame
savePosButton: private JButton variable for the save position button in the main frame
loadButton: private JButton variable for the load button in the main frame
editButton: private JButton variable for the edit button in the main frame
gradientBox: private JComboBox variable for the combo box/dropdown menu of different gradient colors

positionDisplay: public JLabel variable
combo: public JComboBox variable for the combo box/dropdown menu with Mandelbrot set and Julia set
appFrame: public static Mandelbrot variable for the main frame

--Methods--
Mandelbrot() - Constructor method for Mandelbrot class
main(String[] args) - main method to create the main frame and show the window
actionPerformed(ActionEvent e) - method to implement action commands on buttons and in the frame

===SetCalculator===
SetCalculator.java contains a SetCalculator class that calculates the Mandelbrot and Julia display to be input in the frame.

--Variables--
xMin: private static double variable for the minimum x coordinate
xMax: private static double variable for the maximum x coordinate
yMin: private static double variable for the minimum y coordinate
yMax: private static double variable for the maximum y coordinate

--Methods--
SetCalculator(double xMin, double xMax, double yMin, double yMax) - Constructor method for SetCalculator class
defaultDisplay(int x, int y, int limit, int h, int w) - Method that calculates the default display for the Mandelbrot
juliaDisplay(int x, int y, int limit, int h, int w) - Method that calcualtes the Julia display
updateDisplay(double xMi, double xMa, double yMi, double yMa, double width, double height) - Method that calcualtes any updates to the display
getXMIN() - method that returns xMin for display on the canvas 
getXMAX() - method that returns xMax for display on the canvas 
getYMIN() - method that returns yMin for display on the canvas 
getYMAX() - method that returns yMax for display on the canvas 
