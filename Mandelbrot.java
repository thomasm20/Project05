
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mandelbrot extends JFrame implements ActionListener{
    
    // Class Variables   
    private Canvas canvas;
    private JButton increaseButton;
    private JButton decreaseButton;
    private JButton resetButton;
    public JComboBox combo;
    public static Mandelbrot appFrame;
    
    private JButton saveImaButton;
    private JButton savePosButton;
    private JButton loadButton;
    private JButton editButton;
    public JLabel positionDisplay;
    
   
    public Mandelbrot(){
        
        // Use a GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints positionConst = new GridBagConstraints();
        positionConst.insets = new Insets(10, 10, 10, 10);
        
        // Set up the window
        setSize(1100,800);        
        setTitle("Mandelbrot App");        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add the canvas
        positionConst.gridx = 0;
        positionConst.gridy = 0;
        positionConst.fill = GridBagConstraints.BOTH;
        positionConst.gridwidth = GridBagConstraints.REMAINDER;
        positionConst.weightx = 1;
        positionConst.weighty = 1;
        
        canvas = new Canvas(3); // Scaled up by 3x       
        add(canvas, positionConst);
        
        positionConst.fill = GridBagConstraints.HORIZONTAL;
        positionConst.gridwidth = 1;
        positionConst.gridy = 1;
        positionConst.weighty = 0;
        
     //=====Increase=====
     	increaseButton = new JButton("Increase Limit");
     
     // Use "this" class to handle button presses
     	  increaseButton.addActionListener(this);
     	  positionConst.gridx = 1;
     	  positionConst.gridy = 1;
     	  positionConst.insets = new Insets(10, 10, 10, 10);
     	  add(increaseButton, positionConst);
     	  
     	//=====Decrease Limit=====
       	decreaseButton = new JButton("Decrease Limit");
       
       // Use "this" class to handle button presses
       	  decreaseButton.addActionListener(this);
       	  positionConst.gridx = 2;
       	  positionConst.gridy = 1;
       	  positionConst.insets = new Insets(10, 10, 10, 10);
       	  add(decreaseButton, positionConst);
       	  
       //=====Reset=====
       	resetButton = new JButton("Reset");
       
       // Use "this" class to handle button presses
       	  resetButton.addActionListener(this);
       	  positionConst.gridx = 3;
       	  positionConst.gridy = 1;
       	  positionConst.insets = new Insets(10, 10, 10, 10);
       	  add(resetButton, positionConst);
       	  
       //=====Combo Box=====
       	String[] items = {"Mandelbrot Set", "Julia Set"};
       combo = new JComboBox(items);
         // Use "this" class to handle button presses
         combo.addActionListener(this);
         positionConst.gridx = 4;
         positionConst.gridy = 1;
         positionConst.insets = new Insets(10, 10, 10, 10);
         add(combo, positionConst);
         
       //=====Save Image=====
       saveImaButton = new JButton("Save Image");
         // Use "this" class to handle button presses
          saveImaButton.addActionListener(this);
          positionConst.gridx = 1;
          positionConst.gridy = 2;
          positionConst.insets = new Insets(10, 10, 10, 10);
          add(saveImaButton, positionConst);
         	  
       //=====Save Position=====
       savePosButton = new JButton("Save Position");
           
        // Use "this" class to handle button presses
         savePosButton.addActionListener(this);
         positionConst.gridx = 2;
         positionConst.gridy = 2;
         positionConst.insets = new Insets(10, 10, 10, 10);
         add(savePosButton, positionConst);
           	  
      //=====Load Pos=====
       loadButton = new JButton("Load Position");
             
        // Use "this" class to handle button presses
         loadButton.addActionListener(this);
         positionConst.gridx = 3;
         positionConst.gridy = 2;
         positionConst.insets = new Insets(10, 10, 10, 10);
       	 add(loadButton, positionConst);
             	  
      //=====Edit Gradient=====
        editButton = new JButton("Edit Gradient");
               
        // Use "this" class to handle button presses
         editButton.addActionListener(this);
         positionConst.gridx = 4;
         positionConst.gridy = 2;
         positionConst.insets = new Insets(10, 10, 10, 10);
         add(editButton, positionConst);
     	
       //=====Position Display=====
         positionDisplay = new JLabel("Ranges:  x: [-2.5. 1.0]  y: [-1.0, 1.0]");
                
         // Use "this" class to handle button presses
          positionConst.gridx = 1;
          positionConst.gridy = 3;
          positionConst.insets = new Insets(10, 10, 10, 10);
          add(positionDisplay, positionConst);
      	
         
    }
    
    public static void main(String[] args) {
        
        // Main frame
        appFrame = new Mandelbrot();                
        
        // Show window
        appFrame.setVisible(true);
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// INCREASE LIMIT BUTTON
		if(e.getActionCommand().equals("Increase Limit")) {
			this.canvas.limit = this.canvas.limit*2;
			this.canvas.resetRender();
		}
		
		//DECREASE LIMIT BUTTON
		else if (e.getActionCommand().equals("Decrease Limit")) {
			if(this.canvas.limit > 32)
			{
			this.canvas.limit = this.canvas.limit/2;
			this.canvas.resetRender();
			}
			else
				System.out.println("Limit cannot go any lower");
		}
		
		//RESET BUTTON
		else if(e.getActionCommand().equals("Reset")) {
			this.canvas.limit = 32;
			this.canvas.currentSet = "Mandelbrot Set";
			this.canvas.calc = new SetCalculator(-2.5, 1.0, -1.0, 1.0);
		    this.canvas.switched = false;
		    
			this.canvas.resetRender();
		}
		
		//COMBO BOX CHANGED
		else if(e.getActionCommand().equals("comboBoxChanged")) {
			this.canvas.currentSet = (String)combo.getSelectedItem();
			
			//change range display
			if(((String)combo.getSelectedItem()).equals("Mandelbrot Set"))
				positionDisplay.setText("Ranges:  x: [-2.5. 1.0]  y: [-1.0, 1.0]");
			else
				positionDisplay.setText("Ranges:  x: [-1.5. 1.5]  y: [-1.5, 1.5]");
			
			this.canvas.resetRender();
		}
		
		//EDIT GRADIENT
//		else if(e.getActionCommand() == "Edit Gradient")
//		{
//			System.out.println((String)combo.getSelectedItem());
//			this.canvas.currentGradient = (String)combo.getSelectedItem();
//			this.canvas.resetRender();
//		}
//		
		//COMBO BOX RETRIEVES SET
	}
}