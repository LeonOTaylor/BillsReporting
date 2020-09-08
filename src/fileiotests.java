import java.io.*;
import java.util.*;

public class fileiotests {
	
	public static void main(String[] args) throws Exception
	{
		String line;
		int xCoord, yCoord;
		String[] classInfo, temp;
		FileReader fr = new FileReader("C:\\Users\\leont\\eclipse-workspace\\Bills_Reporting\\ClassroomLayout.csv");
		BufferedReader br = new BufferedReader(fr);
		List<String> classDeets = new ArrayList<>();
		
		while((line = br.readLine()) != null)
		{
			classInfo = line.split(":,");
			temp = line.split(",");
			
			if(classInfo.length > 1)
			 classDeets.add(classInfo[1]);
						
			if(temp.length > 3)
			{
				System.out.println("COLORINFO: " + temp[3]);//colorInfo = temp;
				xCoord = Integer.valueOf(temp[0]);
				yCoord = Integer.valueOf(temp[1]);
				System.out.println("AT X: " + xCoord);
				System.out.println("AT Y: " + yCoord);
			}
			else if(temp.length == 3)
			{
				System.out.println("STUDENTINFO: " + temp[2]); //studentInfo = temp;
				xCoord = Integer.valueOf(temp[0]);
				yCoord = Integer.valueOf(temp[1]);
				System.out.println("AT X: " + xCoord);
				System.out.println("AT Y: " + yCoord);
			}
			//System.out.println(line);
		}
		
		// There probably is a much more efficent way of doing this but I can't b fuked
		String[] array = classDeets.toArray(new String[classDeets.size()]);
		
		for(int i=0; i<array.length; i++)
		{
			System.out.println("i: " + i + " " + array[i]);
		}
	}
}
