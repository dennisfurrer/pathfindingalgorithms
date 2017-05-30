# pathfindingalgorithms
Various informed/uninformed path finding algorithms as console applications as well as an input file generator. The various algorithms take ".cav" extension files as input. These consist solely of comma-separated integers and is in the following format. The first value represents how many nodes there are, this can be called N. The next Nx2 values represent each node's coordinates. Finally, the values from Nx2+1 to the end of the file represent a connectivity matrix which details which node can be accessed from where.

How to use:
1) Run the CavFileGenerator class and follow the steps to create your own ".cav" input file.
2) Take a note of the absolute filepath where this ".cav" is.
3) Run any algorithm and enter the absolute filepath of the ".cav" input file.