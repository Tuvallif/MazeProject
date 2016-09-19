package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import algorithms.maze.Maze3d;
import algorithms.maze.MyMaze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;

public class TestCompress {

	public static void main(String[] args) throws IOException {
		// generate another 3d maze
		Maze3dGenerator mg = new MyMaze3dGenerator();
		Maze3d maze = mg.generate(/* your parameters */);
		maze.printMaze();
		byte[] mazeArray = maze.toByteArray();

		Maze3d mazeToCompare = new MyMaze3d(mazeArray);
		if (maze.equals(mazeToCompare)) {
			System.out.println("Mazes are equal");
		}

		OutputStream out = new MyCompressorOutputStream(new FileOutputStream("1.maz"));
		out.write(maze.toByteArray());
		out.flush();
		out.close();
		InputStream in = new MyDecompressorInputStream(new FileInputStream("1.maz"));
		byte b[] = new byte[maze.toByteArray().length];
		in.read(b);
		in.close();
		Maze3d loaded = new MyMaze3d(b);
		System.out.println(loaded.equals(maze));
	}

}
