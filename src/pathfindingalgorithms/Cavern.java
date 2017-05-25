package pathfindingalgorithms;

import java.util.ArrayList;
import java.util.List;

public class Cavern 
{
	int x, y, no;
	double g, f, h, value;
	Cavern parent;
	List<Cavern> neighbours = new ArrayList<Cavern>();
	
	public Cavern(int _x, int _y, int _no)
	{
		x = _x;
		y = _y;
		no = _no;
	}
}