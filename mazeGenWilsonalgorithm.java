public class MazeGen {
	
	static int[] appendInt(int[] list, int toAdd) {
		int[] newList = new int[list.length+1];
		for(int i = 0; i<list.length;i++) {
			newList[i] = list[i];
		}
		newList[list.length] = toAdd;
		return newList;
	}
	
	
	
	static int[][] appendListInt(int[][] list, int[] toAdd) {
		int[][] newList = new int[list.length+1][];
		for(int i = 0; i<list.length;i++) {
			newList[i] = list[i];
		}
		newList[list.length] = toAdd;
		return newList;
	}
	
	/**
	 * Creates a beginning at an empty location and makes its way to a visited location and connects 
	 * <font color="red"> THERE MUST ALREADY BE A VISITED LOCATION
	 * <font color="white">
	 */
	
	static int[][] createAHall(int[][][] maze, int[][] unvisitedLocation, int[][] visitedLocation) {
		
		int[][] hallway = {};
		int randInt = (int)Math.random()*unvisitedLocation.length;
		hallway = appendListInt(hallway,new int[] {unvisitedLocation[randInt][0],unvisitedLocation[randInt][1]});
		
		boolean connected = false;
		
		while (connected == false) {
			randInt = (int)(Math.random()*4);
			int[] pickedPos = {};
			
			if (randInt == 0) {
				pickedPos = new int[] {hallway[hallway.length-1][0]+1,hallway[hallway.length-1][1]};
			}
			
			if (randInt == 1) {
				pickedPos = new int[] {hallway[hallway.length-1][0]-1,hallway[hallway.length-1][1]};
			}
			
			if (randInt == 2) {
				pickedPos = new int[] {hallway[hallway.length-1][0],hallway[hallway.length-1][1]+1};
			}
			
			if (randInt == 3) {
				pickedPos = new int[] {hallway[hallway.length-1][0],hallway[hallway.length-1][1]-1};
			}
			
			
			
			if (pickedPos[0] < 0 || pickedPos[0] >= maze.length || pickedPos[1] < 0 || pickedPos[1] >= maze[0].length) {
				continue;
			}
			
			
			for (int i = 0; i < visitedLocation.length; i++) {
				if (visitedLocation[i][0] == pickedPos[0] && visitedLocation[i][1] == pickedPos[1]) {
					hallway = appendListInt(hallway,new int[] {pickedPos[0],pickedPos[1]});
					return hallway;
				}
			}
			
			
			boolean undoHallway = false;
			
			for (int i = 0; i < hallway.length; i++) {
				
				if (hallway[i][0] == pickedPos[0] && hallway[i][1] == pickedPos[1]) {
					undoHallway = true;
					int[][] newHallway;
					newHallway = new int[i+1][];
					for (int i2 = 0; i2 <= i; i2++) {
						
						newHallway[i2] = hallway[i2];
						
					}
					
					hallway = newHallway;
					
				}
				
			}
			
			
			if (undoHallway == false) {
				
				hallway = appendListInt(hallway,new int[] {pickedPos[0],pickedPos[1]});
				
			}
		}
		return hallway;
	}
	
	/**
	 * Changes the open and close values of the maze use along with the create hall function
	 */
	
	static int[][][] putHallIntoMaze(int[][][] maze, int[][] hallway) {
		
		for (int i = 0; i < hallway.length-1; i++) {
			
			if (hallway[i][0] == hallway[i+1][0]+1) {
				
				maze[hallway[i][0]][hallway[i][1]][1] = 1;
				maze[hallway[i+1][0]][hallway[i+1][1]][2] = 1;
				
			}
			
			if (hallway[i][0] == hallway[i+1][0]-1) {
				
				maze[hallway[i][0]][hallway[i][1]][2] = 1;
				maze[hallway[i+1][0]][hallway[i+1][1]][1] = 1;
				
			}

			if (hallway[i][1] == hallway[i+1][1]+1) {
				
				maze[hallway[i][0]][hallway[i][1]][0] = 1;
				maze[hallway[i+1][0]][hallway[i+1][1]][3] = 1;
				
			}
			
			if (hallway[i][1] == hallway[i+1][1]-1) {
				
				maze[hallway[i][0]][hallway[i][1]][3] = 1;
				maze[hallway[i+1][0]][hallway[i+1][1]][0] = 1;
				
			}
			
		}
		
		return maze;
	}
	
	/**
	 * removes any unvisited locations found in the hallway
	 */
	static int[][] removeUnvisitedLocationWithHallway(int[][] unvisitedLocation, int[][] hallway) {
		
		int[][] newUnvisitedLocation = {};
		
		for (int i = 0; i < unvisitedLocation.length;i++) {
			
			boolean add = true;
			
			for (int i2 = 0; i2 < hallway.length;i2++) {
				
				if (hallway[i2][0] == unvisitedLocation[i][0] && hallway[i2][1] == unvisitedLocation[i][1]) {
					
					add = false;
					
				}
				
			}
			if (add == true) {
				
			newUnvisitedLocation = appendListInt(newUnvisitedLocation, unvisitedLocation[i]);
			
			}
		}
		
		return newUnvisitedLocation;
	}
	
	/**
	 * adds every location found in hallway except for the last one (last value in hallway is all ways already visited)
	 */
	static int[][] addVisitedLocationWithHallway(int[][] visitedLocation, int[][] hallway) {
		
		int[][] newVisitedLocation = visitedLocation;
		
		for (int i = 0; i < hallway.length-1;i++) {
			
			newVisitedLocation = appendListInt(newVisitedLocation, hallway[i]);
			
		}
		
		return newVisitedLocation;
	}
	
	
	/**
	 * returns a made maze ready for printing
	 */
	static int[][][] createMaze(int size, int[][][] maze) {
		
		maze = new int[size][size][6];
		
		int[][] unvisitedLocation = {};
		int[][] visitedLocation = {};
		
		for(int i1 = 0; i1 < size; i1++) {
			
			for(int i2 = 0; i2 < size; i2++) {
				
				maze[i1][i2] = new int[]{0,0,0,0};
				
			}
		}
		
		
		visitedLocation = new int[][] {{(int)(Math.random()*size),(int)(Math.random()*size)}};
		
		for(int i1 = 0; i1 < size; i1++) {
			
			for(int i2 = 0; i2 < size; i2++) {
					
				unvisitedLocation = appendListInt(unvisitedLocation, new int[] {i1,i2});
				
			}
		}
		
		removeUnvisitedLocationWithHallway(unvisitedLocation, visitedLocation);
		while (unvisitedLocation.length > 0) {
			int[][] hallway = createAHall(maze, unvisitedLocation, visitedLocation);
			maze = putHallIntoMaze(maze, hallway);	
			unvisitedLocation = removeUnvisitedLocationWithHallway(unvisitedLocation, hallway);
			visitedLocation = addVisitedLocationWithHallway(visitedLocation, hallway);
			printMaze(maze);
			System.out.println();
			System.out.println();
			System.out.println();
		}
		
		return maze;
	}
	
	static void printMaze(int[][][] maze) {
		
		
		for (int y = 0; y < maze.length; y++) {
			
			String Layer1 = "";
			String Layer2 = "";
			String Layer3 = "";
			
			for (int x = 0; x < maze[0].length; x++) {
				
				Layer1 += "⬛";
				
				if (maze[x][y][0] == 1) {
					Layer1 += "⬜";
				} else {
					Layer1 += "⬛";
				}
				
				Layer1 += "⬛";
				
				
				if (maze[x][y][1] == 1) {
					Layer2 += "⬜";
				} else {
					Layer2 += "⬛";
				}
				
				Layer2 += "⬜";
				
				if (maze[x][y][2] == 1) {
					Layer2 += "⬜";
				} else {
					Layer2 += "⬛";
				}
				
				
				Layer3 += "⬛";
				
				if (maze[x][y][3] == 1) {
					Layer3 += "⬜";
				} else {
					Layer3 += "⬛";
				}
				
				Layer3 += "⬛";
				
			}
			
			System.out.println(Layer1);
			System.out.println(Layer2);
			System.out.println(Layer3);
			
		}
		
	}
	 
	static public void main(String[] args) {
		
		int[][][] maze = {};
		
		int size = 10;
		
		maze = createMaze(size, maze);
		printMaze(maze);
		
	}
	
}
