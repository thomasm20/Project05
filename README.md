# Project05
Project with the purpose of 

Project 5
CS 209 Fall 2019
By Mitchell Thomas, Joe Salerno, and Brendan Olski

===Features===
--Selection of shapes--
Describe how you programmed the Mandelbrot set dispaly in a general sense, include descriptions of any patterns you used.
--Dragged shape preview--
etc
--Other features--
etc etc

===Canvas===
Canvas.java contains a Canvas class that draws the pixels stored in the BufferedImage variable image in chunks and yields thread control for visual updates between chunks.

--Variables--
doneRendering: private boolean variable ... doing what?
renderX: private int variable...
renderY: private int variable...
width: private int variable...
height: privat int variable...
posStart: private Point...
posEnd: private Point
drawRect: private Rectangle
image: pivate BufferedImage
gImg: private Graphics2D
scale: private double variable
gradient: private RainbowGradient

switched: public boolean
limit: public int variable
calc: public SetCalculator
currentGradient: public String variable
currentSet: public String variable

colorSelect: final private Color 
chunkSize: final private int variable that 

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
switchtoJulia() - 

===RainbowGradient===
RainbowGradient is a class that 

--Variables--
test:
greyscale:
greenscale:
rainbow:
colors: private list of Color objects that defines the list of all color gradients
n:
current:
instance: the private static singleton instance variable of type RainbowGradient

--Methods--
getInstance() - 
RainbowGradient(int n) - Constructor
stateOfCurrent(String state) - 
stateOfLimit(int limit) - 
colorGradientMaker() - 
getColors() - 

===Complex===
Complex.java contains a Complex class that

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

equals(Object x) - 
hashCode() - 

main(String[] args) - sample client for testing

===Mandelbrot===
Mandelbrot.java contains a Mandelbrot class that extends JFrame and implements ActionListener to ...

--Variables--
canvas: private Canvas 
increaseButton: private JButton 
decreaseButton: private JButton 
resetButton: private JButton  
saveImaButton: private JButton 
savePosButton: private JButton 
loadButton: private JButton 
editButton: private JButton 

positionDisplay: public JLabel 
combo: public JComboBox 
appFrame: public static Mandelbrot 

--Methods--
Mandelbrot() - Constructor method 
main(String[] args) - 
actionPerformed(ActionEvent e) - 

===SetCalculator===
SetCalculator.java contains a SetCalculator class that 

--Variables--
xMin: private static double 
xMax: private static double 
yMin: private static double 
yMax: private static double 

--Methods--
SetCalculator(double xMin, double xMax, double yMin, double yMax) - Constructor method
defaultDisplay(int x, int y, int limit, int h, int w) - 
juliaDisplay(int x, int y, int limit, int h, int w) - 
updateDisplay(double xMi, double xMa, double yMi, double yMa, double width, double height) - 
getXMIN() - 
getXMAX() - 
getYMIN() - 
getYMAX() - 
