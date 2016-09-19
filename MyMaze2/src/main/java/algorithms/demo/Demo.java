package algorithms.demo;

import java.util.Comparator;
import java.util.List;
import algorithms.maze.Maze3d;
import algorithms.maze.MyMaze3d;
import algorithms.maze.Position;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.PrimMaze3dGenerator;
import algorithms.search.BFS;
import algorithms.search.BestFirstSearch;
import algorithms.search.DFS;
import algorithms.search.Search;

public class Demo {

	/**
	 * This method runs a creation of a maze, and than finds a path using BFS, DFS, and bestFS
	 */
	public void Run() {

		Maze3dGenerator myGernerator = new PrimMaze3dGenerator(10,10,10);
		Maze3d myMaze = myGernerator.generate();
		myMaze.printMaze();
		Searchable Srchble = new mySearchable(myMaze);
		Search srcDFS = new BestFirstSearch(new DFS(Srchble));
		Search srcBFS = new BestFirstSearch(new BFS(Srchble, c));
		Search srcBestFS = new BestFirstSearch(new BFS(Srchble, best));

		List<Position> DFSPath = srcDFS.FindPath();
		List<Position> BFSPath = srcBFS.FindPath();
		List<Position> BestFSPath = srcBestFS.FindPath();

		System.out.println("dfs");
		int i = printPath(DFSPath);
		System.out.println("bfs");
		int j = printPath(BFSPath);
		System.out.println("bestfs");
		int k = printPath(BestFSPath);
		
		byte[] myByte = myMaze.toByteArray();
		Maze3d myMazeFromByte = new MyMaze3d(myByte);
		System.out.println(myMaze);
		System.out.println(myMaze.equals(myMazeFromByte));

	}

	/**
	 * This method prints the path from startPoint to goalPoint, according to toPrint
	 * if the list is null - returns null
	 * @param toPrint a list of Positions to print on the screen
	 * @return the number of positions that were in the list
	 */
	private int printPath(List<Position> toPrint) {
		int result = 0;
		//checking that not null
		if (toPrint == null) {
			System.out.println("EMPTY LIST");
		} else {
			// PRINTING THE FIRST POSITION
			Position curPos = toPrint.remove(toPrint.size() - 1);
			System.out.println(curPos.printPos());
			// PRINTING THE OTHERS
			while (toPrint.isEmpty() == false) {
				result++;
				printArrow();
				curPos = toPrint.remove(toPrint.size() - 1);
				System.out.println(curPos.printPos());

			}
		}

		return result;
	}

	/**
	 * simple comparator
	 */
	Comparator<Position> c = new Comparator<Position>() {
		public int compare(Position p1, Position p2) {
			return 0;
		}
	};

	/**
	 * best comparator
	 */
	Comparator<Position> best = new Comparator<Position>() {
		public int compare(Position p1, Position p2) {
			return ((p1.getHeight() - p2.getHeight()) + (p1.getWidth() - p2.getWidth())
					+ (p1.getDepth() - p2.getDepth()));
		}
	};

	
	/**
	 * This method prints and arrow
	 *  ||       
     *  ||       
     * \||/       
     *  \/       
	 */
	void printArrow() {
		for (int i = 0; i < 2; ++i) {
			System.out.println("        " + "||" + "       ");
		}
		System.out.println("       \\" + "||" + "/       ");
		System.out.println("        \\/       ");
	}

}
