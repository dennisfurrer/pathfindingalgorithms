package pathfindingalgorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCaverns 
{

	String input;
	public void setInput(String input)
	{
		this.input = input;
	}
	
	private String[] data() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(input));
		String buffer = br.readLine();
		br.close();
		String[] data = buffer.split(",");
		return data;
	}
	
	public int noOfCavs() throws IOException
	{
		String[] data = data();
		int noOfCavs = Integer.parseInt(data[0]);
		return noOfCavs;
	}
	
	public int[][] xyArray() throws IOException
	{
		int noOfCavs = noOfCavs();
		String[] data = data();
		int[][] xyArray = new int[noOfCavs][2];
		
		for(int i = 0; i < noOfCavs; i++)
		{
			xyArray[i][0] = Integer.parseInt(data[i*2+1]);
			xyArray[i][1] = Integer.parseInt(data[i*2+2]);
		}
		
		return xyArray;
	}
	
	public boolean[][] connMatrix() throws IOException
	{
		int noOfCavs = noOfCavs();
		String[] data = data();
		boolean[][] connMatrix = new boolean[noOfCavs][noOfCavs];
		
		int row = 0;
		int col = 0;
		
		for (int i = noOfCavs*2+1; i < data.length; i++)
		{
			connMatrix[row][col] = (data[i].equals("1") ? true : false);
			col++;
			
			if (col == noOfCavs)
			{
				col = 0;
				row++;
			}
		}
		
		return connMatrix;
	}
	
}

