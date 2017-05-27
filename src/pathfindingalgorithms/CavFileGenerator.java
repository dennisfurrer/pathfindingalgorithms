package pathfindingalgorithms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CavFileGenerator 
{
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Please enter the absolute filepath where you would like to store the .cav file:");
		Scanner sc = new Scanner(System.in);
		String filepath = sc.next();
		PrintWriter writer = new PrintWriter(filepath + "\\input.cav", "UTF-8");
		String fileContents = Integer.toString(noOfCavs()) + "," + Arrays.deepToString(coordinates()) + "," + Arrays.deepToString(connectivity());
		String output = fileContents.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").replaceAll("false", "0").replaceAll("true", "1");
		writer.println(output);
		writer.close();
		System.out.println("File successfully generated!");
	}
	
	public static int noOfCavs()
	{
		int noOfCavs = 20;
		return noOfCavs;
	}
	
	public static int[][] coordinates()
	{
		int noOfCavs = noOfCavs();
		int[][] coordinates = new int[noOfCavs][2];
		
		for (int i = 0; i < noOfCavs; i++)
		{
			coordinates[i][0] = ThreadLocalRandom.current().nextInt(0, 21);
			coordinates[i][1] = ThreadLocalRandom.current().nextInt(0, 21);
		}
		
		return coordinates;
	}
	
	public static boolean[][] connectivity()
	{
		int noOfCavs = noOfCavs();
		int[][] coordinates = coordinates();
		boolean[][] connectivity = new boolean[noOfCavs][noOfCavs];
		int noOfConnections;
		
		for (int i = 0; i < noOfCavs-1; i++)
		{
			if (i == 0 || i == 19)
			{
				noOfConnections = ThreadLocalRandom.current().nextInt(1, 2);
			}
			else
			{
				noOfConnections = ThreadLocalRandom.current().nextInt(1, 5);
			}
			
			int[] connectionsIndice = new int[noOfConnections];
			
			for (int j = 0; j < noOfConnections; j++)
			{
				connectionsIndice[j] = ThreadLocalRandom.current().nextInt(0, 20);
			}
			
			for (int conn : connectionsIndice)
			{
				connectivity[i][conn] = true;
				connectivity[conn][i] = true;
			}
			
			connectivity[0][19] = false;
			connectivity[19][0] = false;
		}
		
		return connectivity;
	}
}
