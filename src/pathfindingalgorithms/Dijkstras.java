package pathfindingalgorithms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Dijkstras 
{
	static ReadCaverns rc = new ReadCaverns();
	
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Please enter absolute filepath to .cav file:");
		Scanner sc = new Scanner(System.in);
		String in = sc.nextLine();
		rc.setInput(in);
		algorithm();
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

	private static void algorithm() throws IOException 
	{
		int noOfCavs = rc.noOfCavs();
		int[][] xyArray = rc.xyArray();
		boolean[][] connMatrix = rc.connMatrix();
		Cavern root = new Cavern(xyArray[0][0], xyArray[0][1], 0);
		Cavern goal = new Cavern(xyArray[noOfCavs - 1][0], xyArray[noOfCavs - 1][1], noOfCavs-1);
		List<Cavern> openList = new ArrayList<Cavern>();
		List<Cavern> closedList = new ArrayList<Cavern>();
		List<Cavern> solutionList = new ArrayList<Cavern>();
		List<Cavern> finalPath = new ArrayList<Cavern>();

		System.out.print("There are " + noOfCavs + " caverns.\n");
		
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
		
		if (openList.size() == 0 && goal.no != current.no)
		{
			System.out.println("No path found!");
		}
		
		if (current.no == goal.no)
		{
			goal.value = current.value;
		}
		
		while (solutionList.contains(current))
		{
			finalPath.add(current);
			current = current.parent;
		}
		
		Collections.reverse(finalPath);
		
		System.out.print("Final path: ");
		
		for (Cavern c : finalPath)
		{
			if (c.no != goal.no)
			{
				System.out.print(c.no+1 + " -> ");
			}
			else
			{
				System.out.print(c.no+1 + "\n");
			}
		}
		
		System.out.print("Path cost: " + goal.value);
	}
}
