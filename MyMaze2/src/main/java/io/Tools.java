package io;

public class Tools {

	public static byte[] trim(byte[] byteArray, int start, int size) {
		byte[] toReturn = new byte[size];
		for (int i = 0; i < size; ++i) {
			toReturn[i] = byteArray[start + i];
		}

		return toReturn;
	}

	public static byte[] trim(byte[] byteArray, int start) {
		return trim(byteArray, start, byteArray.length - start);
	}
	
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
