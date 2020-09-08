/**
 * --------------------------------------------------------
 * Class: LibraryComponents
 *
 * @author Mark O'Reilly
 * Developed: 2017
 *
 * Purpose: To contain a library of utility methods that can be accessed from other Java applications
 *
 * Currently: 
 *  - LocateAJLabel - for positioning a JLabel using the layout manager: SpringLayout 
 *  - LocateAJTextField - for positioning a JTextField using SpringLayout 
 *  - LocateAJButton - for positioning a JButton using SpringLayout 
 *  - LocateAJTextArea - for positioning a JTextArea using SpringLayout 
 *
 * ----------------------------------------------------------
 */


//package Billsreporting;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.KeyListener;


public class LibraryComponents
{
    
    /** 
    * Purpose: Locate a single JLabel within the JFrame.
    * @param   myJFrame The JFrame that the JLabel will be placed on
    * @param   myJLabelLayout The layout manager where the JLabel should be placed
    * @param   JLabelCaption The string of text to be displayed in the label
    * @param   x The X coordinate the label is to be placed
    * @param   y The Y coordinate the label is to be placed
    * returns The JLabel.
    */
    public static JLabel LocateAJLabel(JFrame myJFrame, SpringLayout myJLabelLayout, String JLabelCaption, int x, int y)
    {
	// Instantiate the JLabel
        JLabel myJLabel = new JLabel(JLabelCaption);
	// Add the JLabel to the screen
        myJFrame.add(myJLabel); 
	// Set the position of the JLabel (From left hand side of the JFrame (West), and from top of JFrame (North))
        myJLabelLayout.putConstraint(SpringLayout.WEST, myJLabel, x, SpringLayout.WEST, myJFrame);
        myJLabelLayout.putConstraint(SpringLayout.NORTH, myJLabel, y, SpringLayout.NORTH, myJFrame);
	// Return the label to the calling method
        return myJLabel;
    }
   
        
    /**
    * Purpose: Locate a single JTextField within the JFrame.
    * @param myJFrame The JFrame that the textfield will be placed on
    * @param myKeyLstnr The key listener for this textfield
    * @param myJTextFieldLayout The layout manager the textfield will be placed on
    * @param width The width of the textfield
    * @param x The X coordinate where the textfield should be placed
    * @param y The Y coordinate where the textfield should be placed
    * returns The JTextField.
    */
    public static JTextField LocateAJTextField(JFrame myJFrame, KeyListener myKeyLstnr, SpringLayout myJTextFieldLayout, int width, int x, int y)
    {
        JTextField myJTextField = new JTextField(width);
        myJFrame.add(myJTextField);  
        myJTextField.addKeyListener(myKeyLstnr);
        myJTextFieldLayout.putConstraint(SpringLayout.WEST, myJTextField, x, SpringLayout.WEST, myJFrame);
        myJTextFieldLayout.putConstraint(SpringLayout.NORTH, myJTextField, y, SpringLayout.NORTH, myJFrame);
        return myJTextField;
    }
    
    /**
     * Purpose: Locate a single JTextField on an AWT Panel
     * @param myPanel The AWT panel that the textfield will be placed on
     * @param myJTextFieldLayout The layout manager the textfield will be placed on
     * @param width The width of the textfield
     * @param x The X coordinate where the textfield should be placed
     * @param y The Y coordinate where the textfield should be placed
     * returns The JTextField.
     */
    public static JTextField LocateAJTextFieldOnPanel(Panel myPanel, SpringLayout myJTextFieldLayout, int width, int x, int y)
    {
        JTextField myJTextField = new JTextField(width);
        myPanel.add(myJTextField);  
        //myJTextField.addKeyListener(myKeyLstnr);
        myJTextFieldLayout.putConstraint(SpringLayout.WEST, myJTextField, x, SpringLayout.WEST, myPanel);
        myJTextFieldLayout.putConstraint(SpringLayout.NORTH, myJTextField, y, SpringLayout.NORTH, myPanel);
        return myJTextField;
    }

        
    /** 
     * Purpose: Locate a single JLabel within the JFrame.
     * @param   myJFrame The JFrame that the JLabel will be placed on
     * @param   myActnLstnr The actionlistener to listen for this buttons events
     * @param   myJButtonLayout The layout manager where the JLabel should be placed
     * @param   JButtonCaption The string of text to be displayed in the label
     * @param   x The X coordinate the label is to be placed
     * @param   y The Y coordinate the label is to be placed
     * @param   w The width of the button
     * @param   h The height of the button
     * returns The JLabel.
     */
    public static JButton LocateAJButton(JFrame myJFrame, ActionListener myActnLstnr, SpringLayout myJButtonLayout, String  JButtonCaption, int x, int y, int w, int h)
    {    
        JButton myJButton = new JButton(JButtonCaption);
        myJFrame.add(myJButton);
        myJButton.addActionListener(myActnLstnr);
        myJButtonLayout.putConstraint(SpringLayout.WEST, myJButton, x, SpringLayout.WEST, myJFrame);
        myJButtonLayout.putConstraint(SpringLayout.NORTH, myJButton, y, SpringLayout.NORTH, myJFrame);
        myJButton.setPreferredSize(new Dimension(w,h));
        return myJButton;
    }

    
    /** 
     * Purpose: Locate a single JLabel within the JFrame.
     * @param   myJFrame The JFrame that the JLabel will be placed on
     * @param   myLayout The layout manager where the JLabel should be placed
     * @param   myJTextArea The textarea to be used
     * @param   x The X coordinate the label is to be placed
     * @param   y The Y coordinate the label is to be placed
     * @param   w The width of the textarea
     * @oaram   h The height of the textarea
     * returns The JLabel.
     */
    public static JTextArea LocateAJTextArea(JFrame myJFrame, SpringLayout myLayout, JTextArea myJTextArea, int x, int y, int w, int h)
    {    
        myJTextArea = new JTextArea(w,h);
        myJFrame.add(myJTextArea);
        myLayout.putConstraint(SpringLayout.WEST, myJTextArea, x, SpringLayout.WEST, myJFrame);
        myLayout.putConstraint(SpringLayout.NORTH, myJTextArea, y, SpringLayout.NORTH, myJFrame);
        return myJTextArea;
    }

    /**
     * Purpose: Clears the text stored in a grid of textfields - used by the clear button
     * @param JTxtFld The JTextField array to be cleared
     * @param minX The starting X coordinate where the clearing should start
     * @param minY The starting Y coordinate where the clearing should start
     * @param maxX The finishing X coordinate where the clearing should end
     * @param maxY The finishing Y coordinate where the clearing should end
     */
    public static void clearJTextFieldArray(JTextField[][] JTxtFld, int minX, int minY, int maxX, int maxY)
    {
        for (int y = minY; y < maxY; y++)
        {
            for (int x = minX; x < maxX; x++)
            {
                JTxtFld[x][y].setText("");
                JTxtFld[x][y].setBackground(Color.white); // Could be null but when program starts color is white
            }
        }
    }
 
    /**
     * Purpose: returns the index (location) of the largest value in an array
     * @param arr The array where the index of the largest value is to be found
     * @return The position in the array where the largest value resides
     */
    public static int getLargestIndex(int arr[])
    {
        int largestIndex = -1;
        int largestValue = -1;
        for (int i = 0; i<arr.length; i++)
        {
            if(arr[i] > largestValue)
            {
                largestValue = arr[i];
                largestIndex = i;
            }
        }        
        return largestIndex;       
    }

    /**
     * Purpose: Returns the largest value in an integer array
     * @param arr The array where the largest value is to be found
     * @return The largest value stored in the array
     */
    public static int getLargestValue(int arr[])
    {
        int largestValue = -1;
        for (int i = 0; i<arr.length; i++)
        {
            if(largestValue > arr[i])
            {
                largestValue = arr[i];
            }
        }        
        return largestValue;       
    }
    
	/**
	 * Purpose: Checks if a string has an integer value
	 * @param strValue The string with the integer value
	 * @return The string containg the numerical value or 0 if there wasn't a numerical value stored in the string
	 */
	public static String checkInteger(String strValue)
    {
        try 
        {
            Integer.parseInt(strValue);
            return strValue;
        }
        catch (Exception e) 
        {
            return "0";
        }
    }
    
}