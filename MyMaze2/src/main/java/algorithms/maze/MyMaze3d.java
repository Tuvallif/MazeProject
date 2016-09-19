package algorithms.maze;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import algorithms.exceptions.MyPositionIsWallException;
import ch.qos.logback.core.subst.Tokenizer;
import io.MyDecompressorInputStream;
import io.Tools;

/**
 * @author Tuval Lifshitz
 *
 */
public class MyMaze3d implements Maze3d {
	/**
	 * represents the maze
	 */
	private int[][][] myBoard;
	/**
	 * the start Position
	 */
	private Position start;
	/**
	 * the goal Position
	 */
	private Position goal;

	/**
	 * the maze Height of the maze
	 */
	private int height;
	/**
	 * the maze Width of the maze
	 */
	private int width;
	/**
	 * the maze Depth of the maze
	 */
	private int depth;

	/**
	 * This is the constructor of the class
	 * 
	 * @param start
	 *            - the starting Position
	 * @param goal
	 *            - the goal Position
	 * @param height
	 *            - the Height of the maze
	 * @param width
	 *            - the Width of the maze
	 * @param depth
	 *            - the Depth of the maze
	 * @param setAsWall
	 *            - determines if the maze will start as all walls or all doors.
	 *            if true - all walls, if false - all doors
	 */
	public MyMaze3d(Position start, Position goal, int height, int width, int depth, boolean setAsWall) {
		super();
		this.start = start;
		this.goal = goal;
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.myBoard = new int[height][width][depth];
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				for (int k = 0; k < depth; ++k) {
					this.myBoard[i][j][k] = setAsWall ? 1 : 0;
				}
			}
		}
	}

	public MyMaze3d(byte[] byteArray) {
		int startHeight = byteArray[0];
		int startWidth = byteArray[1];
		int startDepth = byteArray[2];
		this.start = new MyPosition(startHeight, startWidth, startDepth);
		int goalHeight = byteArray[3];
		int goalWidth = byteArray[4];
		int goalDepth = byteArray[5];
		this.goal = new MyPosition(goalHeight, goalWidth, goalDepth);
		this.height = byteArray[6];
		this.width = byteArray[7];
		this.depth = byteArray[8];
		byte[] uncompressedBytes = Tools.trim(byteArray, 9, byteArray.length - 9);
		myBoard = fromMazeByte(uncompressedBytes, Tools.trim(byteArray, 6, 3));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getStartPosition()
	 */
	public Position getStartPosition() {
		return this.start;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getGoalPosition()
	 */
	public Position getGoalPosition() {
		return this.goal;
	}

	/**
	 * this method checks if the cell exists in the maze, according to the given
	 * ints of height, width and depth
	 * 
	 * @param currHeight
	 *            - the height of the potential cell
	 * @param currWidth
	 *            - the Width of the potential cell
	 * @param currDepth
	 *            - the Depth of the potential cell
	 * @return true if cell exists, else false
	 */
	public boolean cellExists(int currHeight, int currWidth, int currDepth) {
		return (currHeight < this.height && currHeight >= 0 && currWidth < this.width && currWidth >= 0
				&& currDepth < this.depth && currDepth >= 0);
	}
	
	/**
	 * this method checks if the cell in the maze is not a wall, according to
	 * the given ints of height, width and depth
	 * 
	 * @param currHeight
	 *            - the height of the potential cell
	 * @param currWidth
	 *            - the Width of the potential cell
	 * @param currDepth
	 *            - the Depth of the potential cell
	 * @return true if cell is not wall, else false
	 */
	private boolean cellIsNotWall(int currHeight, int currWidth, int currDepth) {
		return (cellExists(currHeight, currWidth, currDepth) && myBoard[currHeight][currWidth][currDepth] == 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getCrossSectionByX(int)
	 */
	public int[][] getCrossSectionByX(int currHeight) {
		int[][] res;
		if(currHeight < height && currHeight >= 0 ){
			res = new int[this.width][this.depth];
			for (int i = 0; i < this.width; ++i) {
				for (int j = 0; j < this.depth; ++j) {
					res[i][j] = this.myBoard[currHeight][i][j];
				}
			}
		}else{
			res = null;
		}
		
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getCrossSectionByY(int)
	 */
	public int[][] getCrossSectionByY(int currWidth) {
		int[][] res;
		if(currWidth < width && currWidth >= 0 ){
			res = new int[this.height][this.depth];

			for (int i = 0; i < this.height; ++i) {
				for (int j = 0; j < this.depth; ++j) {
					res[i][j] = this.myBoard[i][currWidth][j];
				}
			}
		}else{
			res = null;
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getCrossSectionByZ(int)
	 */
	public int[][] getCrossSectionByZ(int currDepth) {
		int[][] res;
		if(currDepth < depth && currDepth >= 0 ){
			res = new int[this.height][this.width];

			for (int i = 0; i < this.height; ++i) {
				for (int j = 0; j < this.width; ++j) {
					res[i][j] = this.myBoard[i][j][currDepth];
				}
			}
		}else{
			res = null;
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getDepth()
	 */
	public int getDepth() {
		return depth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#setWall(algorithms.maze.Position, boolean)
	 */
	public void setWall(Position p, boolean wall) {
		if (cellExists(p.getHeight(), p.getWidth(), p.getDepth())) {
			this.myBoard[p.getHeight()][p.getWidth()][p.getDepth()] = wall ? 1 : 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getPossibleMoves(algorithms.maze.Position)
	 */
	public String[] getPossibleMoves(Position p) throws MyPositionIsWallException {
		// If position is a wall
		if (!cellExists(p.getHeight(), p.getWidth(), p.getDepth())) {
			throw new MyPositionIsWallException();
		}
		List<Position> neighbors = getNeighborPositions(p);
		List<Position> possibleMoves = new LinkedList<Position>();
		for (Position pos : neighbors) {
			if (cellIsNotWall(pos.getHeight(), pos.getWidth(), pos.getDepth())) {
				possibleMoves.add(pos);
			}
		}

		String[] result = new String[possibleMoves.size()];
		int i = 0;
		for (Position position : possibleMoves) {
			result[i++] = position.toString();
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getPossibleMovesPositions(algorithms.maze.
	 * Position)
	 */
	public List<Position> getPossibleMovesPositions(Position p) throws MyPositionIsWallException {
		// If position is a wall
		if (!cellExists(p.getHeight(), p.getWidth(), p.getDepth())) {
			throw new MyPositionIsWallException();
		}
		List<Position> neighbors = getNeighborPositions(p);
		List<Position> possibleMoves = new LinkedList<Position>();
		for (Position pos : neighbors) {
			if (cellIsNotWall(pos.getHeight(), pos.getWidth(), pos.getDepth())) {
				possibleMoves.add(pos);
			}
		}

		return possibleMoves;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * algorithms.maze.Maze3d#getNeighborPositions(algorithms.maze.Position)
	 */
	public List<Position> getNeighborPositions(Position p) throws MyPositionIsWallException {

		List<Position> possibleMoves = new LinkedList<Position>();

		// Check up/down move

		if (cellExists(p.getHeight() + 1, p.getWidth(), p.getDepth())) {
			possibleMoves.add(new MyPosition(p.getHeight() + 1, p.getWidth(), p.getDepth()));
		}
		if (cellExists(p.getHeight() - 1, p.getWidth(), p.getDepth())) {
			possibleMoves.add(new MyPosition(p.getHeight() - 1, p.getWidth(), p.getDepth()));
		}
		// Check left/right move
		if (cellExists(p.getHeight(), p.getWidth() + 1, p.getDepth())) {
			possibleMoves.add(new MyPosition(p.getHeight(), p.getWidth() + 1, p.getDepth()));
		}
		if (cellExists(p.getHeight(), p.getWidth() - 1, p.getDepth())) {
			possibleMoves.add(new MyPosition(p.getHeight(), p.getWidth() - 1, p.getDepth()));
		}
		// Check forward/backwards move
		if (cellExists(p.getHeight(), p.getWidth(), p.getDepth() + 1)) {
			possibleMoves.add(new MyPosition(p.getHeight(), p.getWidth(), p.getDepth() + 1));
		}
		if (cellExists(p.getHeight(), p.getWidth(), p.getDepth() - 1)) {
			possibleMoves.add(new MyPosition(p.getHeight(), p.getWidth(), p.getDepth() - 1));
		}

		return possibleMoves;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#getValueAtPosition(algorithms.maze.Position)
	 */
	public int getValueAtPosition(Position p) {
		int result = 1;
		if (cellExists(p.getHeight(), p.getWidth(), p.getDepth())) {
			result = this.myBoard[p.getHeight()][p.getWidth()][p.getDepth()];
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.maze.Maze3d#printMaze()
	 */
	public void printMaze() {

		System.out.println("PRINTING");
		for (int i = 0; i < this.getHeight(); ++i) {
			System.out.println(" i =" + i);
			for (int j = 0; j < this.getWidth(); ++j) {
				for (int k = 0; k < this.getDepth(); ++k) {
					// System.out.print(" i ="+ i);
					// System.out.print(" j ="+ j);
					// System.out.print(" k ="+ k + "value is ");
					System.out.print(this.getValueAtPosition(new MyPosition(i, j, k)));
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	public byte[] toByteArray() {
		return fromArrysToOne(this.getStartPosByte(), this.getGoalPosByte(), getMazeByte(),
				this.getMazeSizeByte());
	}

	private byte[] fromArrysToOne(byte[] start, byte[] goal, byte[] maze, byte[] size) {
		int place = 0;
		byte[] toReturn = new byte[this.getHeight() * this.getWidth() * this.getDepth() + 9];
		// setting the startPos
		while (place < 3) {
			toReturn[place] = start[place % 3];
			place++;
		}
		// now for goal
		while (place < 6) {
			toReturn[place] = goal[place % 3];
			place++;
		}
		// for size
		while (place < 9) {
			toReturn[place] = size[place % 3];
			place++;
		}
		for (int i = 0; i < maze.length; ++i, ++place) {
			toReturn[place] = maze[i];
		}

		return toReturn;
	}

	private byte[] getPosByte(Position pos) {
		byte[] toReturn = new byte[3];
		toReturn[0] = getByteValue(pos.getHeight());
		toReturn[1] = getByteValue(pos.getWidth());
		toReturn[2] = getByteValue(pos.getDepth());

		return toReturn;
	}

	private byte[] getGoalPosByte() {
		return getPosByte(this.getGoalPosition());
	}

	private byte[] getStartPosByte() {
		return getPosByte(this.getStartPosition());
	}

	private byte[] getMazeSizeByte() {
		byte[] toReturn = new byte[3];
		toReturn[0] = getByteValue(this.getHeight());
		toReturn[1] = getByteValue(this.getWidth());
		toReturn[2] = getByteValue(this.getDepth());

		return toReturn;
	}

	private byte[] getMazeByte() {
		byte[] toReturn = new byte[this.getHeight() * this.getWidth() * this.getDepth()];
		int counter = 0;
		int cur = 0;
		int toAssign = 0;
		for (int i = 0; i < this.getHeight(); ++i) {
			for (int j = 0; j < this.getWidth(); ++j) {
				for (int k = 0; k < this.getDepth(); ++k) {
					if((cur == this.getCrossSectionByX(i)[j][k] && counter > 255)|| cur != this.getCrossSectionByX(i)[j][k]){		
						toReturn[toAssign] = (byte)counter;
						cur = (cur+1)%2;
						toAssign++;
						counter = 0;
					}
					counter++;
					//toReturn[counter] = (byte) this.getCrossSectionByX(i)[j][k];
					//counter++;
				}
			}
		}
		for(int i = 0;i <  toReturn.length;i++){
			System.out.print(toReturn[i]);
		}
		return toReturn;
	}

	private int[][][] fromMazeByte(byte[] byteBoard, byte[] sizeOfBoard) {
		MyDecompressorInputStream in;
		int heightHelper = sizeOfBoard[0];
		int widthHelper = sizeOfBoard[1];
		int depthHelper = sizeOfBoard[2];
		int[][][] toReturn = new int[heightHelper][widthHelper][depthHelper];
		int cur = 0;
		int index = 0;
		int counter = byteBoard[index];
		for (int i = 0; i < heightHelper; ++i) {
			for (int j = 0; j < widthHelper; ++j) {
				for (int k = 0; k < depthHelper; ++k) {
					if(counter == 0){
						index++;
						counter = byteBoard[index];
						cur = (cur+1)%2;
					}
					toReturn[i][j][k] = cur;
					counter--;
				}	
			}
		}	
		//myBoard = toReturn;
		//printMaze();
		return toReturn;
	}

	private byte getByteValue(int toChange) {
		// changing to byte- we know its smaller than 255
		byte toReturn = (byte) toChange;

		return toReturn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + depth;
		result = prime * result + ((goal == null) ? 0 : goal.hashCode());
		result = prime * result + height;
		result = prime * result + Arrays.deepHashCode(myBoard);
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyMaze3d other = (MyMaze3d) obj;
		if (depth != other.depth)
			return false;
		if (goal == null) {
			if (other.goal != null)
				return false;
		} else if (!goal.equals(other.goal))
			return false;
		if (height != other.height)
			return false;
		if (!Arrays.deepEquals(myBoard, other.myBoard))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (width != other.width)
			return false;
		return true;
	}


	public int[][][] getBoard() {
		return myBoard;
	}
	

	public int getSize(){
		int sizeOfInt = Integer.SIZE;
		//for positions start and goal
		int PositionsSize = sizeOfInt*3*2;
		int sizeOfBoard = sizeOfInt*this.height*this.width*this.depth;
		int totalSize = (PositionsSize + sizeOfBoard) /4;
		return totalSize;
	}
}
