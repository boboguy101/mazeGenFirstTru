package MazeGen;


public class MazeGen {
	
	/**
	 * Literally just returns a list with the added int
	 */
	static int[] appendInt(int[] list, int toAdd) {
		int[] newList = new int[list.length+1];
		for(int i = 0; i<list.length;i++) {
			newList[i] = list[i];
		}
		newList[list.length] = toAdd;
		return newList;
	}
	
	/**
	 * Goes to the point and gives the ID,
	 * <p>if it is off of the map it returns a 0
	 */
	static int getPosID(int x, int y, int[][] maze) {
		if (x >= 0 && y >= 0) {
			if (x <= maze.length-1 && y <= maze[0].length-1) {

				return maze[x][y];
			} 
		}
		
		return -1;
	}
	
	/**
	 * 
	 * Collects the IDs surrounding the point and returns it in 
	 * <font color="green">UP, LEFT, RIGHT, DOWN<br> 
	 * <font color="white">order
	 */
	static int[] grabNeighborsIDs(int x, int y, int[][] maze) {
		int[] IDs = {
				getPosID(x,y-1,maze), 
				getPosID(x-1,y,maze), 
				getPosID(x+1,y,maze), 
				getPosID(x,y+1,maze)
				};
		
		return IDs;
	}
	
	/**
	 * Returns the IDs that fit together
	 */
	static int[] getIDsThatFitForPos(int x, int y, int[][] maze, int[][] blocks, int[] IDs) {
		
		for (int i = 0; i < IDs.length-1; i++) {
			//System.out.println("Current ID: "+IDs[i]);
		}
		
		int[] neighborsIDs = grabNeighborsIDs(x, y, maze);
		
		int up;
		int left;
		int right;
		int down;
		
		
		if (neighborsIDs[0] == -1 || neighborsIDs[0] == -2) {
			up = neighborsIDs[0];
		}else {
			up = blocks[neighborsIDs[0]][3];
		}
		
		
		if (neighborsIDs[1] == -1 || neighborsIDs[1] == -2) {
			left = neighborsIDs[1];
		}else {
			left = blocks[neighborsIDs[1]][2];
		}
		
		
		if (neighborsIDs[2] == -1 || neighborsIDs[2] == -2) {
			right = neighborsIDs[2];
		}else {
			right = blocks[neighborsIDs[2]][1];
		}
		
		
		if (neighborsIDs[3] == -1 || neighborsIDs[3] == -2) {
			down = neighborsIDs[3];
		}else {
			down = blocks[neighborsIDs[3]][0];
		}
		//System.out.println(up+" "+left+" "+right+" "+down);
		int[] usableIDs = {};
		for(int i = 0; i<blocks.length;i++) {
			if (blocks[i][0] == up || up == -2 || (blocks[i][0] == 0 && up == -1)) {
				//System.out.println("Passed up ID: "+IDs[i]);
				if (blocks[i][3] == down || down == -2 || (blocks[i][3] == 0 && down == -1)) {
					//System.out.println("Passed down ID: "+IDs[i]);
					if (blocks[i][1] == left || left == -2 || (blocks[i][1] == 0 && left == -1)) {
						//System.out.println("Passed left ID: "+IDs[i]);
						if (blocks[i][2] == right || right == -2 || (blocks[i][2] == 0 && right == -1)) {
							//System.out.println("Passed right ID: "+IDs[i]);
							for(int IDI = 0; IDI < IDs.length; IDI++) {
								//System.out.println("2ndID: "+IDs[IDI]);
								if (IDs[IDI] == i) {
									//System.out.println("This better work ID: "+IDs[i]);
									usableIDs = appendInt(usableIDs, i);
									if (usableIDs[usableIDs.length-1] != i) {
										//System.out.println("HOW?!");
									}
								}
							}
							
						}
					}
				}
			}
		}
		
		for (int i = 0; i < usableIDs.length-1; i++) {
			//System.out.println("Passed ID: "+usableIDs[i]);
		}
		
		return usableIDs;
	}
	
	
	/**
	 * This returns the IDs that can fit in the current position
	 */
	static int[] getUsableIDsForPos(int x, int y, int[][] maze, int[][] blocks, int[] IDs) {
		
		int[] usableIDs;
		usableIDs = getIDsThatFitForPos(x, y, maze, blocks, IDs);
		for (int i = 0; i < usableIDs.length-1; i++) {
		//	System.out.println("Returning ID: "+usableIDs[i]);
		}
		return usableIDs;
	}
	
	static void printMaze(int[][] maze, int[][] blocks) {
		for(int yI = 0; yI < maze[0].length; yI++) {
			String Layer1 = "";
			String Layer2 = "";
			String Layer3 = "";
			for(int xI = 0; xI < maze[0].length; xI++) {
				Layer1 += "⬛";
				if (blocks[maze[xI][yI]][0] == 1) {
					Layer1 += "⬜";
				}else {
					Layer1 += "⬛";
				}
				Layer1 += "⬛";	
				if (blocks[maze[xI][yI]][1] == 1) {
					Layer2 += "⬜";
				}else {
					Layer2 += "⬛";
				}
				Layer2 += "⬜";	
				if (blocks[maze[xI][yI]][2] == 1) {
					Layer2 += "⬜";
				}else {
					Layer2 += "⬛";
				}
				Layer3 += "⬛";
				if (blocks[maze[xI][yI]][3] == 1) {
					Layer3 += "⬜";
				}else {
					Layer3 += "⬛";
				}
				Layer3 += "⬛";	
			}
			System.out.println(Layer1);
			System.out.println(Layer2);
			System.out.println(Layer3);
		}
	}
	
	static public int randomWeighted(int x, int y, int[] IDs, int[] weights) {
		int maxWeight = 0;
		for (int i = 0; i < IDs.length-1; i++) {
			maxWeight += weights[IDs[i]];
		}
		int randomNumber = (int)(Math.random() * maxWeight);
		int countingUpWeight = 0;
		
		for (int i = 0; i < IDs.length-1; i++) {
			//System.out.println("X: "+x+"Y: "+y+"ID: "+IDs[i]);
		}
		
		for (int i = 0; i < IDs.length-1; i++) {
			countingUpWeight += weights[IDs[i]];
			if (countingUpWeight >= randomNumber+1) {
				return IDs[i];
			}
		}
		return IDs[0];
	}
	
	static public void main(String[] args) {

		/**
		 * Blocks has 4 ints at 0 or 1
		 * 0 is closed
		 * 1 is open
		 * These values are how it knows how to connect to each other
		 */
		int[][] blocks = {
				{
					1,
				1, 		1, //0
					1
				},
				{
					1,
				1, 		0, //1
					1
				},
				{
					1,
				1, 		1, //2
					0
				},
				{
					1,
				0, 		1, //3
					1
				},
				{
					0,
				1, 		1, //4
					1
				},
				{
					0,
				1, 		0, //5
					1
				},
				{
					1,
				1, 		0, //6
					0
				},
				{
					1,
				0, 		1, //7
					0
				},
				{
					0,
				0, 		1, //8
					1
				},
				{
					0,
				1, 		1, //9
					0
				},
				{
					1,
				0, 		0, //10
					1
				},
				{
					0,
				0, 		1, //11
					0
				},
				{
					0,
				0, 		0, //12
					1
				},
				{
					0,
				1, 		0, //13
					0
				},
				{
					1,
				0, 		0, //14
					0
				},
				
		};
		
		int[][] maze = {
				{-2,-2,-2},
				{-2,-2,-2},
				{-2,-2,-2}
				};
		
		int size = 20;
		
		maze = new int[size][size];
		
		for(int i1 = 0; i1 < size; i1++) {
			
			for(int i2 = 0; i2 < size; i2++) {
				
				maze[i1][i2] = -2;
				
			}
				
		}
		
		int[] IDs = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
		int[] weights = {2,1,1,1,1,4,4,4,4,16,16,1,1,1,1};
		
		for(int xI = 0; xI < maze.length; xI++) {
			
			for(int yI = 0; yI < maze[0].length; yI++) {
				int[] list = getUsableIDsForPos(xI,yI,maze,blocks,IDs);
				
				for (int i = 0; i < list.length-1; i++) {
					//System.out.println("MAIN: X: "+xI+" Y: "+yI+"ID: "+list[i]);
				}
				
				int chosenID = randomWeighted(xI,yI,list,weights);
				maze[xI][yI] = chosenID;
			}
		}
		printMaze(maze,blocks);
	}
}
