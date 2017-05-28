package pathfindingalgorithms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CavFileGenerator 
{
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Please enter the absolute filepath where you would like to store the .cav file:");
		Scanner sc = new Scanner(System.in);
		String filepath = sc.next();
		int noOfCavs = noOfCavs();
		int[][] coordinates = coordinates();
		boolean[][] connectivity = connectivity();
		int[] pathFound = pathTest(noOfCavs, coordinates, connectivity);
		
		while (pathFound[0] == 0 || pathFound[1] < 6)
		{
			noOfCavs = noOfCavs();
			coordinates = coordinates();
			connectivity = connectivity();
			pathFound = pathTest(noOfCavs, coordinates, connectivity);
		}
		
		PrintWriter writer = new PrintWriter(filepath + "\\input.cav", "UTF-8");
		String fileContents = Integer.toString(noOfCavs) + "," + Arrays.deepToString(coordinates) + "," + Arrays.deepToString(connectivity);
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
		boolean[][] connectivity = new boolean[noOfCavs][noOfCavs];
		int noOfConnections;
		
		for (int i = 0; i < noOfCavs-1; i++) //for each cavern
		{
			if (i == 0 || i == 19) //if cavern is root or goal
			{
				noOfConnections = ThreadLocalRandom.current().nextInt(1, 3);
			}
			else
			{
				if (i % 2 == 0) //if cavern no is divisible by 2 and not equal to 2, 10 or 14
				{
					noOfConnections = ThreadLocalRandom.current().nextInt(1, ThreadLocalRandom.current().nextInt(3,5));
				}
				else if (i % 5 == 0) //if cavern no is divisible by 5
				{
					noOfConnections = ThreadLocalRandom.current().nextInt(2, 5);
				}
				else
				{
					noOfConnections = ThreadLocalRandom.current().nextInt(ThreadLocalRandom.current().nextInt(1,3), 4);
				}
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
	
	public static int[] pathTest(int noOfCavs, int[][] xyArray, boolean[][] connMatrix)
	{
		int[] pathFound = new int[2];
		pathFound[0] = 0;
		
		Cavern root = new Cavern(xyArray[0][0], xyArray[0][1], 0);
		Cavern goal = new Cavern(xyArray[noOfCavs - 1][0], xyArray[noOfCavs - 1][1], noOfCavs-1);
		List<Cavern> openList = new ArrayList<Cavern>();
		List<Cavern> closedList = new ArrayList<Cavern>();
		List<Cavern> solutionList = new ArrayList<Cavern>();
		List<Cavern> finalPath = new ArrayList<Cavern>();
		
		Cavern current = root;
		root.value = 0;
		openList.add(root);
		solutionList.add(root);
		
		while (openList.size() > 0) 
		{
			if (current.no != goal.no) 
			{
				// Neighbours
				for (int i = 0; i < noOfCavs; i++) 
				{
					if (connMatrix[i][current.no]) 
					{
						Cavern neighbour = new Cavern(xyArray[i][0], xyArray[i][1], i);
						if (closedList.size() == 0) 
						{
							current.neighbours.add(neighbour);
							break;
						} 
						else 
						{
							for (Cavern c : closedList) 
							{
								if (presenceCheck(closedList, i) == false) 
								{
									current.neighbours.add(neighbour);
									break;
								}
							}
						}
					}
				}

				openList.remove(current);
				closedList.add(current);

				for (Cavern neighbour : current.neighbours) 
				{
					neighbour.value = distance(current, neighbour);
					double potentialValue = neighbour.value + current.value;

					if (presenceCheck(openList, neighbour.no) == false && presenceCheck(closedList, neighbour.no) == false) 
					{
						neighbour.value = potentialValue;
						neighbour.parent = current;
						openList.add(neighbour);
					}
				}
				
				ArrayList<Double> cavernValues = new ArrayList<Double>();
				
				for (Cavern c : openList) 
				{
					cavernValues.add(c.value);
					double minValue = Collections.min(cavernValues);
					
					if (c.value == minValue) 
					{
						current = c;
						solutionList.add(current);
					}
				}
			}
			else
			{
				break;
			}
		}
		
		if (current.no == goal.no)
		{
			goal.value = current.value;
			pathFound[0] = 1;
		}
		
		while (solutionList.contains(current))
		{
			finalPath.add(current);
			current = current.parent;
		}
		
		pathFound[1] = finalPath.size();
		
		return pathFound;
	}
	
	private static double distance(Cavern c1, Cavern c2) 
	{
		double distance = Math.sqrt(Math.pow((c2.y - c1.y), 2) + Math.pow((c2.x - c1.x), 2));
		return distance;
	}

	private static boolean presenceCheck(List<Cavern> caverns, int cavNo) 
	{
		for (Cavern c : caverns) 
		{
			if (c.no == cavNo) 
			{
				return true;
			}
		}

		return false;
	}
}
