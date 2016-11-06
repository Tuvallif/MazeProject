package io;

/**
 * this class has tools that help us do what we need to to with our program
 * it is kind of like helpers that are around the program
 * @author Tuval Lifshitz
 *
 */
public class Tools {

	/**
	 * This method trims the array to the valid data that we really need 
	 * because sometimes it could be bigger and adds the data that we need-the start and the size
	 * @param byteArray an array of bytes that represents our maze
	 * @param start int that represents the start of the maze
	 * @param size int that represents the size of the maze- height:width:depth
	 * @return a full array with all the data in an array of byte together
	 */
	public static byte[] trim(byte[] byteArray, int start, int size) {
		//creating the new byte[]
		byte[] toReturn = new byte[size];
		for (int i = 0; i < size; ++i) {
			toReturn[i] = byteArray[start + i];
		}

		return toReturn;
	}

	/**
	 * This method trims the array to the valid data that we really need 
	 * because sometimes it could be bigger and adds the data that we need-the start for generic size
	 * @param byteArray an array of bytes that represents our maze
	 * @param start int that represents the start of the maze
	 * @return a full array with all the data in an array of byte together
	 */
	public static byte[] trim(byte[] byteArray, int start) {
		return trim(byteArray, start, byteArray.length - start);
	}
	
	/**
	 * This method compresses the data in the maze in the way that can be transfered easily and faster
	 * @param toCompress
	 * @return
	 */
	public static byte[] compress(byte[] toCompress) {
		byte[] toReturn = new byte[toCompress.length];
		int value = 0;
		byte counter = 0;
		int position = 0;
		//first 9 bytes
		for(; position < 9; ++position){
			toReturn[position] = toCompress[position];
		}
		for (int i = 9; i < toCompress.length; ++i) {
			if (value == toCompress[i]) {
				if (counter == Byte.MAX_VALUE) {
					// Write 255, 0 and restart counter
					toReturn[position++] = counter;
					counter = 0;
					toReturn[position++] = counter;
				}
				++counter;
			} else {// Change value and write counter
				value = (value + 1) % 2;
				toReturn[position++] = counter;
				counter = 1;
			}
		}

		return trim(toReturn, 0, position);
	}
	/**
	 * 	This method decompresses the data in the maze after being compressed while transfered
	 * @param toDecompress the maze as a byte[]
	 * @return a decompressed byte[] that can be interpreted
	 */
	public static byte[] decompress(byte[] toDecompress) {
		//first 9 cells
		int counter = 9;
		//counting the number of cells needed
		for (int i = 0; i < toDecompress.length; ++i) {
			counter += toDecompress[i];
		}
		byte[] toReturn = new byte[counter];
		//byte to write in the array
		byte value = 0;
		//starting again at zero
		counter = 9;
		//setting the maze info- first 9
		for(int i = 0; i < 9; ++i){
			toReturn[i] = toDecompress[i];
		}
		for (int i = 9; i < toDecompress.length; ++i) {
			for(int j = 0; j<  toDecompress[i];++j){
				toReturn[counter] = value;
				counter++;
			}
			value = (byte)((value + 1) % 2);
		}
		return toReturn;

	}

}
