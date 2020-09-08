import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class BillsReporting extends JFrame implements ActionListener, KeyListener
{
    private int totalX = 10; // WAS 12
    private int totalY = 19; // WAS 23
    int xPos = 0;
    int yPos = 0;
    int oldX=0, oldY=0;
    int studentListCntr = 0;
    private JTextField[][] fields;
    private JLabel[] xNums = new JLabel[10];
    private JLabel[] yNums = new JLabel[19];
    //private JTextField[][] fields = new JTextField[totalX][totalY]; 
    ArrayList<String> sortStudents = new ArrayList<String>();
    String[] sortArray;
    
    private JButton btnClear, btnSave, btnExit, btnOpen, btnSort, btnRAF, btnFind;
    JFileChooser fileChoose = new JFileChooser(); 
    private String dataFileName = "C:\\Users\\leont\\Dropbox\\Code\\Billsreporting\\BillsReportingTable.csv";
    private String tableFileName = "C:\\Users\\leont\\Dropbox\\Code\\Billsreporting\\BillsReportingSystem_NEW.csv";
    private String rafFileName = "C:\\Users\\leont\\Dropbox\\Code\\Billsreporting\\BillsReportingRAF.csv";

    private JTextField txtFind, txtClass, txtRoom, txtDate, txtTeacher;
    // Do the declarations below belong here?
    private JLabel lblClass = new JLabel();
    private JLabel lblRoom = new JLabel();
    private JLabel lblDate = new JLabel();
    private JLabel lblTeacher = new JLabel();
    
    SpringLayout springLayout;
    
    
    public static void main(String[] args)
    {
        BillsReporting billsReportingSystem = new BillsReporting();
        billsReportingSystem.run();
    }
    
    /**
     * Purpose: calls methods upon startup to create GUI and fill it with data
     */
    private void run()
    {
        getScreenDimensions(dataFileName);
        fields = new JTextField[totalX][totalY];
        sortArray = new String[(totalY-3)];
        
        setBounds(10, 10, xPos, yPos);
        setTitle("CLASSROOM R0B0TZ");
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        displayGUI();
        System.out.println("File being read: " + dataFileName);

        setResizable(true);
        setVisible(true);
    }

    /**
     * Purpose: Parses the data file to set suitable coordinates for the GUI components
     * @param fileName The data file to be used by the program
     */
    private void getScreenDimensions(String fileName)
    {
        try
        {
            int count = 0;
            String line;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null)
            {
                String temp[] = line.split(",");                    
                count++;
                totalX = temp.length; // WAS temp.length + 1;
            }

            totalX = 10; // Hard set to stop the GUI from being deformed
            xPos = totalX * 65 + 50;
            if(xPos < 825) { xPos = 825; }
            yPos = totalY * 22 +120;
            //System.out.println("XTOTA: " + totalX + " YTOTAL: " + totalY);
            br.close();
        }
        catch (Exception e)
        {
            System.err.println("Error, gere maybe?: " + e.getMessage());            
        }
    }
    
    /**
     * Purpose: Creates and sets up the GUI and fills it with data
     */
    private void displayGUI()
    {
        springLayout = new SpringLayout();
        setLayout(springLayout);
        
        displayTextFields(springLayout);
        displayButtons(springLayout);
        displayXNumsLabel(springLayout);
        displayYNumsLabel(springLayout);
        setupTable(springLayout);
        readDataFile(dataFileName);
    }
	
    /**
     * Purpose: Places the textfields in the GUI
     * @param layout The SpringLayout to be targeted
     */
    private void displayTextFields(SpringLayout layout)
    {
        for (int y = 0; y < totalY; y++)
        {
            for (int x = 0; x < totalX; x++) // WAS JUST x < totalX     
            {
                xPos = x * 65 + 20;
                yPos = y * 22 + 20;           
                //System.out.println("xPos: " + xPos + " yPos: " + yPos);
                fields[x][y] = LibraryComponents.LocateAJTextField(this, this, layout, 5, xPos, yPos);
            }
        }
    }
    
    
    /**
     * Purpose: Displays the grid numbers across the X Axis of the GUI
     * @param layout The SpringLayout to be targeted
     */
    private void displayXNumsLabel(SpringLayout layout)
    {
    	for(int i=0; i<10; i++)
    	{
    		xNums[i] = LibraryComponents.LocateAJLabel(this, layout, Integer.toString(i), i*70+25, 5);
    	}
    }
    
    /**
     * Purpose: Displays the grid numbers down the Y axis 
     * @param layout The SpringLayout to be targeted
     */
    private void displayYNumsLabel(SpringLayout layout)
    {
    	for(int i=0; i<19; i++)
    	{
    		//continue;
    		yNums[i] = LibraryComponents.LocateAJLabel(this, layout, Integer.toString(i), 3, i*22+19);
    	}
    }

    /**
     * Purpose: Creates and places various buttons, lables and textfeilds on the GUI
     * @param layout The SpringLayout to be targeted
     */
    private void displayButtons(SpringLayout layout)
    {
        int yPos = totalY * 22 + 40;
        int xTension = 0;
        if (totalX > 12) { xTension = ((totalX - 12) * 65); }
        
      
        lblClass = LibraryComponents.LocateAJLabel(this, layout, "Class:", 670, 15);
        lblDate = LibraryComponents.LocateAJLabel(this, layout, "Date:", 670, 45);
        lblTeacher = LibraryComponents.LocateAJLabel(this, layout, "Teacher:", 670, 75);
        lblRoom = LibraryComponents.LocateAJLabel(this, layout, "Room:", 670, 105);
        //lblClass.setBounds(20, 400, 100, 30);
        btnClear = LibraryComponents.LocateAJButton(this, this, layout, "Clear", 20, yPos, 80, 25);
        btnOpen = LibraryComponents.LocateAJButton(this, this, layout, "Open File", 100, yPos, 100, 25);
        btnSave = LibraryComponents.LocateAJButton(this, this, layout, "Save", 220, yPos, 80, 25);
        btnSort = LibraryComponents.LocateAJButton(this, this, layout, "Sort", 300, yPos, 80, 25);
        btnFind = LibraryComponents.LocateAJButton(this, this, layout, "Find", 555 + xTension, yPos, 80, 25);
        btnRAF = LibraryComponents.LocateAJButton(this, this, layout, "RAF", 635 + xTension, yPos, 80, 25);
        btnExit = LibraryComponents.LocateAJButton(this, this, layout, "Exit", 715 + xTension, yPos, 80, 25);
        txtFind = LibraryComponents.LocateAJTextField(this, this, layout, 13, 405 + xTension, yPos+4);
        txtClass = LibraryComponents.LocateAJTextField(this, this, layout, 7, 725, 15);
        txtDate = LibraryComponents.LocateAJTextField(this, this, layout, 7, 725, 45);
        txtTeacher = LibraryComponents.LocateAJTextField(this, this, layout, 7, 725, 75);
        txtRoom = LibraryComponents.LocateAJTextField(this, this, layout, 7, 725, 105);
        txtFind.addActionListener(this);  
    }  
  
    /**
     * Purpose: Sets properties for the textfields in the GUI
     * @param layout The SpringLayout to be targeted
     */
    private void setupTable(SpringLayout layout)
    {
        for (int y = 0; y < totalY; y++)
        {
            for (int x = 0; x < totalX; x++)
            {
                setFieldProperties(x, y, true, 255, 255, 255);
            }
        }
    } 

    /**
     * Purpose: Allows properies of a textfield to be set, such as the color and if it can be changed
     * @param x The X coordinate of the specified cell
     * @param y The Y coordinate of the specified cell
     * @param editable Whether the TextField will be able to accept data input
     * @param r The red value of the cell color
     * @param g The green value of the cell color
     * @param b The blue value of the cell color
     */
    public void setFieldProperties(int x, int y, boolean editable, int r, int g, int b)
    {
        fields[x][y].setEditable(editable);
        fields[x][y].setBackground(new Color(r, g, b));
    }
     
    /**
     * Purpose: Performs actions (calls methods) based on various GUI events such as buttons being clicked
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //setupTable(springLayout);

        if (e.getSource() == btnClear)
        {
            LibraryComponents.clearJTextFieldArray(fields,0,0,totalX,totalY);
        }
        if (e.getSource() == btnOpen)
        {
        	JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
        	  
            // invoke the showsOpenDialog function to show the save dialog 
            int r = j.showOpenDialog(null); 
            
            if (r == JFileChooser.APPROVE_OPTION) 
            { 
                // set the label to the path of the selected file 
            	
            	dataFileName = j.getSelectedFile().getAbsolutePath(); 
            	System.out.println("FILE: " + dataFileName);
            	sortStudents.clear();
            	readDataFile(dataFileName);
            	//setupTable(springLayout);
            } 
            // if the user cancelled the operation  
            else
                System.out.print("WAS CANCELLED");
        }
        if (e.getSource() == btnSave)
        {
            writeDataFile(dataFileName);
            sortStudents.clear();
            readDataFile(dataFileName);
        }
        if (e.getSource() == btnSort)
        {
        	SortDialog(false);
        	System.out.println("STUDENT CNTR: " + studentListCntr);
        	//sortStudentRecords();
        }
        if (e.getSource() == btnFind  || e.getSource() == txtFind)
        {
            findStudentRecord();
            if(findStudentRecord() == true)
            	SortDialog(true);
        }
        if (e.getSource() == btnRAF)
        {
            writeRandomAccessFile(rafFileName);
            //int requiredEntry = Integer.parseInt(LibraryComponents.checkInteger(txtFind.getText()));
            //System.out.println("REQUIRED ENTRY: " + requiredEntry);
            readRandomAccessFile(rafFileName,txtFind.getText());
        }        
        if (e.getSource() == btnExit)
        {
        	writeDataFile(dataFileName); //- if clear is clicked then exit data will be lost!
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)  {  }
    @Override
    public void keyPressed(KeyEvent e)  {  }
    @Override
    public void keyReleased(KeyEvent e) {  }
    
    
    /**
     * Purpose: Searches the GUI grid for the value entered into the search bar
     * @return Boolean true (if found) or false (not found)
     */
    public boolean findStudentRecord()
    {
        boolean found = false;
        //int y = 0;
        String strFind = txtFind.getText();
        int xx=0, yy=0;
                
        fields[oldX][oldY].setBackground(Color.white);
        
        while(yy < totalY && found == false)
        {
        	xx = 0;
        	while(xx < totalX && found == false)
        	{
        		if(fields[xx][yy].getText().equalsIgnoreCase(strFind))
                {
                    found = true;
                    //System.out.println("XX: " + xx + " YY: " + yy);
                }
        		xx++; // maybe ++xx?
        	}
           yy++;
        }
        if (found)
        {
            for (int x = 0; x < totalX; x++)
            {
                fields[xx-1][yy-1].setBackground(new Color(255,217,200)); // This is the code that highlights the search result
                oldX = xx-1;
                oldY = yy-1;
            }
            //txtFind.setText(txtFind.getText() + " ...Found.");
        }
        else
        {
            txtFind.setText(txtFind.getText() + " ...Not Found.");
        }
        
        return found;
    }
    
    /**
     * Returns the matching AWT color from a string - string "red" will return AWT.Color.Red
     * @param col The string containing the color
     * @return An AWT Color
     */
    public Color setColor(String col)
    {
    	if(col == "black")
    		return Color.black;
    	else if(col == "blue")
    		return Color.blue;
    	else if(col == "cyan")
    		return Color.cyan;
    	else if(col == "gray")
    		return Color.gray;
    	else if(col == "green")
    		return Color.green;
    	else if(col == "orange")
    		return Color.orange;
    	else if(col == "magenta")
    		return Color.magenta;
    	else if(col == "pink")
    		return Color.pink;
    	else if(col == "red")
    		return Color.red;
    	else if(col == "yellow")
    		return Color.yellow;
    	else
    		return null;
    }
    
    /**
     * Purpose: Returns the string value of an AWT color - AWT.Color.Red will return "red"
     * @param col The AWT Color
     * @return A string with the name of the Color passed to the method
     */
    public String getColor(Color col)
    {
    	if(col.equals(Color.black))
    		return "black";
    	else if(col.equals(Color.blue))
    		return "blue";
    	else if(col.equals(Color.cyan))
    		return "cyan";
    	else if(col.equals(Color.gray))
    		return "gray";
    	else if(col.equals(Color.green))
    		return "green";
    	else if(col.equals(Color.orange))
    		return "orange";
    	else if(col.equals(Color.magenta))
    		return "magenta";
    	else if(col.equals(Color.pink))
    		return "pink";
    	else if(col.equals(Color.red))
    		return "red";
    	else if(col.equals(Color.yellow))
    		return "yellow";
    	else
    		return "null";
    }
    //<editor-fold defaultstate="collapsed" desc="File Management">    

 	/**
 	 * Creates and opens a GUI containing a sorted list of students and their corresponding postions 
 	 * - also highlights the location of a student that has been searched for
 	 * @param isSearch Boolean value - True if a value is being searched for and false if only a sorted list is desired
 	 */
    public void SortDialog(boolean isSearch)
    {   
    	  SpringLayout myLayout = new SpringLayout();
          JFrame newFrame = new JFrame();
          int xPos1 = -125, yPos1 = 0;
          
          
          newFrame.setSize(265,500);
          newFrame.setLocation(400, 200);
          newFrame.setResizable(true);
          newFrame.setVisible(true); 
          newFrame.setTitle("Popup Data Frame");

          //Setting close function Dispose of frame and Keep main open.
          newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

          //Defining and setting up new Panel and adding it to the frame.
          Panel panel = new Panel();
          newFrame.add(panel);
          panel.setLayout(myLayout);
          panel.setBackground(new Color(220,220,255));

          JTextField[][] cells = new JTextField[studentListCntr][3];       
          
          for(int i=0; i<studentListCntr; i++)
          {
          	for(int j=0; j<3; j++)
          	{
          		xPos1 = j * 65 + 20;
                  yPos1 = i * 22 + 20;           
                  //System.out.println("xPos: " + xPos + " yPos: " + yPos);
                  cells[i][j] = LibraryComponents.LocateAJTextFieldOnPanel(panel, myLayout, 5, xPos1, yPos1);
                  cells[i][j].setEditable(true); 
          	}
          }
         cells[0][0].setText("Name");
         cells[0][0].setEditable(false);
         cells[0][1].setText("Across");
         cells[0][1].setEditable(false);
         cells[0][2].setText("Down");
         cells[0][2].setEditable(false);
          Collections.sort(sortStudents);
          
          String[] students = sortStudents.toArray(new String[sortStudents.size()]);
          String[] studentDeets;
          for(int i=1; i<students.length; i++)
          {
        	  studentDeets = students[i].split(",");
        	  for(int j=0; j<studentDeets.length; j++)
        	  {
        		  System.out.print(studentDeets[j] + " ");
        		  cells[i][j].setText(studentDeets[j]);
        	  }
        	  System.out.println("HEY: " + i);
          }
          
          if(isSearch)
          {
        	  for(int i=1; i<students.length; i++)
        	  {
        		  if(cells[i][0].getText().equalsIgnoreCase(txtFind.getText()))
        		  {
        			  System.out.println("FOUND" + i);
        			  cells[i][0].setBackground(new Color(255,217,200));
        			  cells[i][1].setBackground(new Color(255,217,200));
        			  cells[i][2].setBackground(new Color(255,217,200));
        		  }
        	  }
          }
        	  
    }    
    
    /**
     * Purpose: Reads a CSV file containing classroom data
     * @param fileName The name of the file to be read
     */
    private void readDataFile(String fileName)
    {
    	String line;
		int xCoord, yCoord;
		studentListCntr = 0;
		String[] classInfo, temp;
		List<String> classDeets = new ArrayList<>();	
		boolean seedFlag = false;
		Color culla;
        try
        {
        	FileReader fr = new FileReader(fileName);
    		BufferedReader br = new BufferedReader(fr);
    		while((line = br.readLine()) != null)
    		{
    			classInfo = line.split(":,");
    			temp = line.split(",");
    			
    			if(classInfo.length > 1)
    			 classDeets.add(classInfo[1]);
    						
    			if(temp.length > 3)
    			{
    				//System.out.println("COLORINFO: " + temp[3]);//colorInfo = temp;
    				xCoord = Integer.valueOf(temp[0]);
    				yCoord = Integer.valueOf(temp[1]);
    				//culla = setColor(temp[3]);
    				// TODO document this & learn about what it does   				
    				try {
    					Field field = Class.forName("java.awt.Color").getField(temp[3]);
    					culla = (Color)field.get(null);
    				} catch (Exception e) {
    					culla = Color.white;
    				}

    				setFieldProperties(xCoord, yCoord, true, culla.getRed(), culla.getGreen(), culla.getBlue());
    				//fields[xCoord][yCoord].setEditable(true); 				
    			}
    			else if(temp.length == 3)
    			{
    				studentListCntr++;
    				StringBuilder sb = new StringBuilder();
    				xCoord = Integer.valueOf(temp[0]);
    				yCoord = Integer.valueOf(temp[1]);
    				fields[xCoord][yCoord].setText(temp[2]);
    				
    				//ORDER FOR STRING BILDA : TEMP2, TEMP0, TEMP1
    				if(!temp[2].equalsIgnoreCase("Front Desk") && seedFlag == false)
    				{
    					sb.append(temp[2] + ",");
        				sb.append(temp[0] + ",");
        				sb.append(temp[1]);
        				
        				//This list gets created twice!
        				//sortStudents.
        				
        				//System.out.println("SB: " + sb);
    				}
    				//System.out.println();
    				sortStudents.add(sb.toString());
    			}
    			//System.out.println(line);
    		}
    		String[] classArray = classDeets.toArray(new String[classDeets.size()]);
    		txtTeacher.setText(classArray[0]);
    		txtClass.setText(classArray[1]);
    		txtRoom.setText(classArray[2]);
    		txtDate.setText(classArray[3]);
    	
            br.close();
        }
        catch (Exception e)
        {
            System.err.println("Error in read IN THIS ONE: " + e.getMessage());            
        }
        
        seedFlag = true;
    }

    /**
     * Purpose: Writes data from the program into a program-readable CSV file
     * @param fileName The name of the file to be written to
     */
    public void writeDataFile(String fileName)
    {
    	Color writeCulla;
        try
        {
            BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
            //BufferedWriter outFile = new BufferedWriter(new FileWriter("C:\\Users\\leont\\Dropbox\\Code\\Billsreporting\\BillsReportingSystem_NEW.csv"));
            outFile.write("Teacher:," + txtTeacher.getText() + "\n");
            outFile.write("Class:," + txtClass.getText() + "\n");
            outFile.write("Room:," + txtRoom.getText() + "\n");
            outFile.write("Date:," + txtDate.getText() + "\n");
            
            for (int y = 0; y < totalY; y++)
            {
                for (int x = 0; x < totalX; x++)
                {
                    //outFile.write(fields[x][y].getText() + ",");
                	
                	writeCulla = fields[x][y].getBackground();
                	
                	if(!writeCulla.equals(Color.white))
                   		outFile.write(Integer.toString(x) + "," + Integer.toString(y) + ",BKGRND FILL," + getColor(writeCulla) + "\n");
                		//System.out.println("COLOR: " + " X: " + x + " Y: " + y + " " + getColor(writeCulla));
                	
                	if(!fields[x][y].getText().isEmpty())
                		outFile.write(Integer.toString(x) + "," + Integer.toString(y) + "," + fields[x][y].getText() + "\n");
                		//System.out.println("TEXTERE" + " X: " + x + " Y: " + y + " " + fields[x][y].getText());
                }
                //outFile.write(fields[totalX - 2][y].getText());
                //outFile.newLine();
            }
            outFile.close();
            System.out.println("Bills Reporting System data has been saved.");
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        
        //System.out.println("CMON PSSS " + Color.getColor("Color.BLUE"));
    }

    /**
     * Purpose: Writes data from the program into a random access file
     * @param rafFileName - The name of the file that data is to be written too
     */

    public void writeRandomAccessFile(String rafFileName)
    {
    	Color writeCulla;
    	try
        {
            RandomAccessFile rafFile = new RandomAccessFile(rafFileName, "rw");
            rafFile.writeUTF("Teacher:," + txtTeacher.getText());
            rafFile.writeUTF("Class:," + txtClass.getText());
            rafFile.writeUTF("Room:," + txtRoom.getText());
            rafFile.writeUTF("Date:," + txtDate.getText());
            
            for (int y = 0; y < totalY; y++)
            {
                for (int x = 0; x < totalX; x++)
                {
                	writeCulla = fields[x][y].getBackground();
                	
                	if(!writeCulla.equals(Color.white))
                   		rafFile.writeUTF(Integer.toString(x) + "," + Integer.toString(y) + ",BKGRND FILL," + getColor(writeCulla));
                		
                	
                	if(!fields[x][y].getText().isEmpty())
                		rafFile.writeUTF(Integer.toString(x) + "," + Integer.toString(y) + "," + fields[x][y].getText());
                		
                }
            }

            rafFile.close();
            System.out.println("Class Room Plan data has been saved.");
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
 
    
    /**
     * Purpose: Reads the random access file and searches for a value in the file
     * @param fileName The name of the random access file to be read from
     * @param content The value to be searched for in the random access file
     */
    private void readRandomAccessFile(String fileName, String content)
    {
        try
        {
            String str = "";
            
            int cntr = 0;
            RandomAccessFile rafFile = new RandomAccessFile(fileName, "r");
            while((str = rafFile.readUTF()) != null)
            {
            	++cntr;
            	if(str.toLowerCase().contains(content.toLowerCase()))
            		System.out.println(content + " found in RAF entry number " + cntr);
            	
            }
            
            rafFile.close();
        }
        catch (Exception e)
        {
        	
            System.err.println("Error: " + e.getMessage());     
        }
    }
    
    
    //</editor-fold>       
    
}

